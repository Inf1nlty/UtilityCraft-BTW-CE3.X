package btw.community.moreblocks;

import btw.BTWAddon;
import btw.AddonHandler;
import com.inf1nlty.moreblocks.block.MBBlocks;
import com.inf1nlty.moreblocks.init.MBInitializer;
import com.inf1nlty.moreblocks.item.MBItems;
import com.inf1nlty.moreblocks.network.SteelLockerNet;

public class MoreBlocksAddon extends BTWAddon {
    @Override
    public void initialize() {
        AddonHandler.logMessage(getName() + " v" + getVersionString() + " Initializing...");
        MBItems.initMBItems();
        MBBlocks.initMBBlocks();
        MBInitializer.initMBRecipes();

        SteelLockerNet.register(this);
    }
}