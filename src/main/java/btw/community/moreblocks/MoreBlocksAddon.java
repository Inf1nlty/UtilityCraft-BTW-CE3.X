package btw.community.moreblocks;

import btw.BTWAddon;
import btw.AddonHandler;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.init.MBRecipes;
import com.inf1nlty.moreblocks.item.MoreBlocksItems;
import com.inf1nlty.moreblocks.network.ColoredCementCtrlNet;
import com.inf1nlty.moreblocks.network.SteelLockerNet;

public class MoreBlocksAddon extends BTWAddon {
    @Override
    public void initialize() {
        AddonHandler.logMessage(getName() + " v" + getVersionString() + " Initializing...");
        MBBlocks.initMBBlocks();
        MoreBlocksItems.initMBItems();
        MBRecipes.initMBRecipes();

        SteelLockerNet.register(this);
        ColoredCementCtrlNet.register(this);
    }
}