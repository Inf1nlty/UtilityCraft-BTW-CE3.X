package btw.community.utilitycraft;


import api.AddonHandler;
import api.BTWAddon;
import api.util.AddonSoundRegistryEntry;
import com.inf1nlty.utilitycraft.UCEnchantments;
import com.inf1nlty.utilitycraft.block.UCBlocks;
import com.inf1nlty.utilitycraft.init.UCRecipes;
import com.inf1nlty.utilitycraft.item.UCItems;
import com.inf1nlty.utilitycraft.network.ColoredCementCtrlNet;
import com.inf1nlty.utilitycraft.network.SteelLockerNet;

public class UtilityCraftAddon extends BTWAddon {

    public static final AddonSoundRegistryEntry[] SWEEP_ATTACK_SOUNDS = new AddonSoundRegistryEntry[7];
    static {
        for (int i = 0; i < 7; i++) {
            SWEEP_ATTACK_SOUNDS[i] = new AddonSoundRegistryEntry("utilitycraft:weapon.sweep" + (i + 1));
        }
    }

    public static final AddonSoundRegistryEntry[] RAPIER_ATTACK_SOUNDS = new AddonSoundRegistryEntry[4];
    static {
        for (int i = 0; i < 4; i++) {
            RAPIER_ATTACK_SOUNDS[i] = new AddonSoundRegistryEntry("utilitycraft:weapon.rapier" + (i + 1));
        }
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(getName() + " v" + getVersionString() + " Initializing...");
        UCBlocks.registerBlocks();
        UCItems.registerItems();
        UCRecipes.registerRecipes();
        UCEnchantments.registerEnchantments();

        SteelLockerNet.register(this);
        ColoredCementCtrlNet.register(this);
    }
}