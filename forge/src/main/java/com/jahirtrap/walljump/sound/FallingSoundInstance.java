package com.jahirtrap.walljump.sound;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FallingSoundInstance extends AbstractTickableSoundInstance {
    private final LocalPlayer player;

    public FallingSoundInstance(LocalPlayer player) {
        super(SoundEvents.ELYTRA_FLYING, SoundSource.PLAYERS);
        this.player = player;
        this.looping = true;
        this.delay = 0;
        this.volume = 0;
    }

    @Override
    public void tick() {
        var length = (float) player.getDeltaMovement().lengthSqr();
        if (player.isAlive() && length >= 0.7) {
            this.volume = Mth.clamp((length - 0.7f) / 4, 0, 1);
            this.pitch = (volume > 0.8) ? (1 + (volume - 0.8f)) : 1;
        } else this.stop();
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }
}
