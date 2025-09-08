package com.inf1nlty.utilitycraft.material;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;

/**
 * Custom material used for colored cement "fluid-like" blocks.
 */
public class ColoredCementMaterial extends Material {

    public ColoredCementMaterial(MapColor mapColor) {
        super(mapColor);
        setReplaceable();
        setNoPushMobility();
    }
}