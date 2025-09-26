package com.inf1nlty.utilitycraft.util;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class UCCompatMixinPlugin implements IMixinConfigPlugin {

    private static final String NIGHTMARE_MOD_ID = "nightmare_mode";

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        boolean nightmareLoaded = isNightmareLoaded();

        if (mixinClassName.endsWith("EntityPlayerMixin") && nightmareLoaded) {
            return false;
        }
        return true;
    }

    private boolean isNightmareLoaded() {
        try {
            return FabricLoader.getInstance().isModLoaded(NIGHTMARE_MOD_ID);
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}