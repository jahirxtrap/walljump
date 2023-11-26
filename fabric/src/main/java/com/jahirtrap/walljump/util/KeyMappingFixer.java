package com.jahirtrap.walljump.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.jahirtrap.walljump.logic.mixin.KeyMappingAccessor;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class KeyMappingFixer {
    private static final Multimap<InputConstants.Key, KeyMapping> KEY_FIX_MAP = ArrayListMultimap.create();

    public static void putKey(InputConstants.Key key, KeyMapping keyMapping) {
        KEY_FIX_MAP.put(key, keyMapping);
    }

    public static void clearMap() {
        KEY_FIX_MAP.clear();
    }

    public static void onKeyDown(InputConstants.Key key) {
        KEY_FIX_MAP.get(key).forEach(it -> ((KeyMappingAccessor) it).setClickCount(((KeyMappingAccessor) it).getClickCount() + 1));
    }

    public static void setKeyDown(InputConstants.Key key, boolean bl) {
        KEY_FIX_MAP.get(key).forEach(it -> it.setDown(bl));
    }
}
