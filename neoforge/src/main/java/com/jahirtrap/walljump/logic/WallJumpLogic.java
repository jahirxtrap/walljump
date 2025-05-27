package com.jahirtrap.walljump.logic;

import com.jahirtrap.walljump.WallJumpClient;
import com.jahirtrap.walljump.init.ModConfig;
import com.jahirtrap.walljump.init.ModConfig.BlockListMode;
import com.jahirtrap.walljump.init.ModEnchantments;
import com.jahirtrap.walljump.init.ServerConfig;
import com.jahirtrap.walljump.network.message.MessageFallDistance;
import com.jahirtrap.walljump.network.message.MessageWallJump;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class WallJumpLogic {

    public static int ticksWallClinged;
    public static int ticksWallSlid;
    public static boolean stopSlid = false;
    public static int wallJumpCount;
    private static int ticksKeyDown;
    private static double clingX, clingZ;
    private static double lastJumpY = Double.MAX_VALUE;
    private static Set<Direction> walls = new HashSet<>();
    private static Set<Direction> staleWalls = new HashSet<>();

    private static boolean collidesWithBlock(Level level, AABB box) {
        return !level.noCollision(box);
    }

    public static void doWallJump(LocalPlayer pl) {
        if (!canWallJump(pl))
            return;

        if (pl.onGround() || pl.getAbilities().flying || !pl.level().getFluidState(pl.blockPosition()).isEmpty() || pl.isHandsBusy()) {
            ticksWallClinged = 0;
            ticksWallSlid = 0;
            stopSlid = false;
            clingX = Double.NaN;
            clingZ = Double.NaN;
            lastJumpY = Double.MAX_VALUE;
            staleWalls.clear();
            wallJumpCount = 0;

            return;
        }

        if (stopSlid) return;

        updateWalls(pl);
        ticksKeyDown = WallJumpClient.KEY_WALL_JUMP.isDown() ? ticksKeyDown + 1 : 0;

        if (ticksWallClinged < 1) {
            if (ticksKeyDown > 0 && ticksKeyDown < 4 && !walls.isEmpty() && canWallCling(pl)) {
                if (ModConfig.autoRotation) {
                    pl.setYRot(getClingDirection().getOpposite().toYRot());
                    pl.yRotO = pl.getYRot();
                }

                ticksWallClinged = 1;
                clingX = pl.getX();
                clingZ = pl.getZ();

                playHitSound(pl, getWallPos(pl));
                spawnWallParticle(pl, getWallPos(pl));
            }

            return;
        }

        if (!WallJumpClient.KEY_WALL_JUMP.isDown() || pl.onGround() || !pl.level().getFluidState(pl.blockPosition()).isEmpty() || walls.isEmpty() || pl.getFoodData().getFoodLevel() < 1) {
            ticksWallClinged = 0;

            if ((pl.input.forwardImpulse != 0 || pl.input.leftImpulse != 0) && !pl.onGround() && !walls.isEmpty()) {
                if (wallJumpCount >= ServerConfig.maxWallJumps) return;
                pl.resetFallDistance();
                PacketDistributor.sendToServer(new MessageWallJump(true));

                wallJump(pl, (float) ServerConfig.wallJumpHeight);
                staleWalls = new HashSet<>(walls);
            }

            return;
        }

        pl.setPos(clingX, pl.getY(), clingZ);

        double motionY = pl.getDeltaMovement().y;
        if (motionY > 0.0) {
            motionY = 0.0;
        } else if (motionY < -0.6) {
            motionY = motionY + 0.2;
            spawnWallParticle(pl, getWallPos(pl));
        } else if (ticksWallClinged++ > ServerConfig.wallSlideDelay) {
            if (ticksWallSlid++ > ServerConfig.stopWallSlideDelay) stopSlid = true;
            motionY = -0.1;
            spawnWallParticle(pl, getWallPos(pl));
        } else {
            motionY = 0.0;
        }

        pl.resetFallDistance();
        PacketDistributor.sendToServer(new MessageFallDistance((float) (motionY * motionY * 8)));

        pl.setDeltaMovement(0.0, motionY, 0.0);
    }

    private static boolean canWallJump(LocalPlayer pl) {
        if (ServerConfig.useWallJump) return true;
        if (!ServerConfig.enableEnchantments || !ServerConfig.enableWallJump)
            return false;
        ItemStack stack = pl.getItemBySlot(EquipmentSlot.FEET);
        if (!stack.isEmpty()) {
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            try {
                Holder<Enchantment> wjHolder = pl.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.WALL_JUMP);
                return enchantments.getLevel(wjHolder) > 0;
            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    private static boolean canWallCling(LocalPlayer pl) {
        if (pl.onClimbable() || pl.getDeltaMovement().y > 0.1 || pl.getFoodData().getFoodLevel() < 1) return false;
        if (collidesWithBlock(pl.level(), pl.getBoundingBox().move(0, -0.8, 0))) return false;
        if (!ServerConfig.onFallWallCling && pl.getDeltaMovement().y < -0.8) return false;
        if (ServerConfig.allowReClinging || pl.getY() < lastJumpY - 1) return true;

        return !staleWalls.containsAll(walls);
    }

    private static void updateWalls(LocalPlayer pl) {
        Vec3 pos = pl.position();
        AABB box = new AABB(pos.x - 0.001, pos.y, pos.z - 0.001, pos.x + 0.001, pos.y + pl.getEyeHeight(), pos.z + 0.001);

        double dist = (pl.getBbWidth() / 2) + (ticksWallClinged > 0 ? 0.1 : 0.06);
        AABB[] axes = {box.expandTowards(0, 0, dist), box.expandTowards(-dist, 0, 0), box.expandTowards(0, 0, -dist), box.expandTowards(dist, 0, 0)};

        int i = 0;
        Direction direction;
        walls = new HashSet<>();
        for (AABB axis : axes) {
            direction = Direction.from2DDataValue(i++);

            if (collidesWithBlock(pl.level(), axis)) {
                if (ServerConfig.blockListMode == BlockListMode.DISABLED || ServerConfig.blockList.isEmpty() || areBlocksAllowed(getBlockId(pl, pl.blockPosition().relative(direction)), getBlockId(pl, pl.blockPosition().above().relative(direction)))) {
                    walls.add(direction);
                    pl.horizontalCollision = true;
                }
            }
        }
    }

    private static String getBlockId(LocalPlayer pl, BlockPos pos) {
        BlockState state = pl.level().getBlockState(pos);
        return (state.isSolid()) ? BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString() : null;
    }

    private static boolean areBlocksAllowed(String... blocks) {
        for (String block : blocks)
            if ((block != null) && switch (ServerConfig.blockListMode) {
                case BLACKLIST -> !ServerConfig.blockList.contains(block);
                case WHITELIST -> ServerConfig.blockList.contains(block);
                default -> true;
            }) return true;
        return false;
    }

    private static Direction getClingDirection() {
        return walls.isEmpty() ? Direction.UP : walls.iterator().next();
    }

    private static BlockPos getWallPos(LocalPlayer player) {
        BlockPos blockPos = player.getOnPos().relative(getClingDirection());
        return player.level().getBlockState(blockPos).isSolid() ? blockPos : blockPos.relative(Direction.UP);
    }

    private static void wallJump(LocalPlayer pl, float up) {
        float strafe = Math.signum(pl.input.leftImpulse) * up * up;
        float forward = Math.signum(pl.input.forwardImpulse) * up * up;

        float f = (float) (1.0F / Math.sqrt(strafe * strafe + up * up + forward * forward));
        strafe = strafe * f;
        forward = forward * f;

        float f1 = (float) (Math.sin(pl.getYRot() * 0.017453292F) * 0.45F);
        float f2 = (float) (Math.cos(pl.getYRot() * 0.017453292F) * 0.45F);

        int jumpBoostLevel = 0;
        MobEffectInstance jumpBoostEffect = pl.getEffect(MobEffects.JUMP);
        if (jumpBoostEffect != null) jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;

        Vec3 motion = pl.getDeltaMovement();
        pl.setDeltaMovement(motion.x + (strafe * f2 - forward * f1), up + (jumpBoostLevel * 0.125), motion.z + (forward * f2 + strafe * f1));

        lastJumpY = pl.getY();
        playBreakSound(pl, getWallPos(pl));
        spawnWallParticle(pl, getWallPos(pl));
        wallJumpCount++;
    }

    private static void playHitSound(Entity entity, BlockPos blockPos) {
        BlockState state = entity.level().getBlockState(blockPos);
        SoundType soundtype = state.getBlock().getSoundType(state, entity.level(), blockPos, entity);
        entity.playSound(soundtype.getHitSound(), soundtype.getVolume() * 0.25F, soundtype.getPitch());
    }

    private static void playBreakSound(Entity entity, BlockPos blockPos) {
        BlockState state = entity.level().getBlockState(blockPos);
        SoundType soundtype = state.getBlock().getSoundType(state, entity.level(), blockPos, entity);
        entity.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch());
    }

    private static void spawnWallParticle(Entity entity, BlockPos blockPos) {
        BlockState state = entity.level().getBlockState(blockPos);
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            Vec3 pos = entity.position();
            Vec3i motion = getClingDirection().getUnitVec3i();

            entity.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.x, pos.y, pos.z,
                    motion.getX() * -1.0D, -1.0D, motion.getZ() * -1.0D);
        }
    }
}
