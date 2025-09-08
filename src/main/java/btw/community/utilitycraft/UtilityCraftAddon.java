package btw.community.utilitycraft;

import btw.BTWAddon;
import btw.AddonHandler;
import com.inf1nlty.utilitycraft.block.UCBlocks;
import com.inf1nlty.utilitycraft.init.UCRecipes;
import com.inf1nlty.utilitycraft.item.UtilityCraftItems;
import com.inf1nlty.utilitycraft.network.ColoredCementCtrlNet;
import com.inf1nlty.utilitycraft.network.SteelLockerNet;

public class UtilityCraftAddon extends BTWAddon {
    @Override
    public void initialize() {
        AddonHandler.logMessage(getName() + " v" + getVersionString() + " Initializing...");
        UCBlocks.initMBBlocks();
        UtilityCraftItems.initMBItems();
        UCRecipes.initMBRecipes();

        SteelLockerNet.register(this);
        ColoredCementCtrlNet.register(this);
    }
}