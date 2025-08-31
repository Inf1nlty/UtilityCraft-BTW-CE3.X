package btw.community.extendedcraft;

import btw.BTWAddon;
import btw.AddonHandler;
import com.inf1nlty.extendedcraft.block.ECBlocks;
import com.inf1nlty.extendedcraft.init.ECRecipes;
import com.inf1nlty.extendedcraft.item.ExtendedCraftItems;
import com.inf1nlty.extendedcraft.network.ColoredCementCtrlNet;
import com.inf1nlty.extendedcraft.network.SteelLockerNet;

public class ExtendedCraftAddon extends BTWAddon {
    @Override
    public void initialize() {
        AddonHandler.logMessage(getName() + " v" + getVersionString() + " Initializing...");
        ECBlocks.initMBBlocks();
        ExtendedCraftItems.initMBItems();
        ECRecipes.initMBRecipes();

        SteelLockerNet.register(this);
        ColoredCementCtrlNet.register(this);
    }
}