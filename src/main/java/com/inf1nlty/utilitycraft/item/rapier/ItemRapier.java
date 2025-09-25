package com.inf1nlty.utilitycraft.item.rapier;

import btw.item.items.SwordItem;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

import java.util.List;

public abstract class ItemRapier extends SwordItem implements IRapier {

    public ItemRapier(int id, EnumToolMaterial material) {
        super(id, material);
    }

    public String getModId() {
        return "utilitycraft";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.desc"));
        } else {
            info.add(StatCollector.translateToLocal("item.utilitycraft.rapier.tip"));
        }
    }
}