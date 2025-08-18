package com.inf1nlty.moreblocks.client.gui;

import com.inf1nlty.moreblocks.block.tileentity.TileEntitySteelChest;
import com.inf1nlty.moreblocks.inventory.ContainerSteelChest;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class GuiSteelChest extends GuiContainer {

    private static final ResourceLocation TEX =
            new ResourceLocation("moreblocks","textures/gui/steel_chest_gui.png");

    private static final int TEX_W=512, TEX_H=512;

    private static final int MAIN_U=0, MAIN_V=0, MAIN_W=356, MAIN_H=150;
    private static final int PLAYER_U=90, PLAYER_V=150, PLAYER_W=176, PLAYER_H=90;

    private static final int SLOT = 18;

    private static final int MAIN_SLOT_X_BASE = (MAIN_W - TileEntitySteelChest.SLOT_COLS * SLOT)/2; // 7
    private static final int MAIN_SLOT_Y_BASE = 18;

    private static final int PLAYER_BG_X = PLAYER_U;
    private static final int PLAYER_BG_Y = PLAYER_V;
    private static final int PLAYER_SLOT_X_BASE = PLAYER_BG_X + (PLAYER_W - 9*SLOT)/2; // 97
    private static final int PLAYER_SLOT_Y_BASE = PLAYER_BG_Y + 7;
    private static final int HOTBAR_GAP = 4;

    private static final int MAIN_CORR_X = 1;
    private static final int MAIN_CORR_Y = 0;
    private static final int PLAYER_CORR_X = 1;
    private static final int PLAYER_CORR_Y = 1;
    private static final int INVENTORY_LABEL_SHIFT_UP = 0;

    public static int chestSlotX(){ return MAIN_SLOT_X_BASE + MAIN_CORR_X; }
    public static int chestSlotY(){ return MAIN_SLOT_Y_BASE + MAIN_CORR_Y; }
    public static int playerSlotX(){ return PLAYER_SLOT_X_BASE + PLAYER_CORR_X; }
    public static int playerSlotY(){ return PLAYER_SLOT_Y_BASE + PLAYER_CORR_Y; }
    public static int hotbarGap(){ return HOTBAR_GAP; }

    private final IInventory inv;

    public GuiSteelChest(InventoryPlayer playerInv, IInventory inv) {
        super(new ContainerSteelChest(playerInv, inv));
        this.inv = inv;
        this.xSize = Math.max(MAIN_W, PLAYER_BG_X + PLAYER_W);
        this.ySize = PLAYER_BG_Y + PLAYER_H;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String titleKey = inv.getInvName();
        String title = inv.isInvNameLocalized() ? titleKey : I18n.getString(titleKey);
        fontRenderer.drawString(title, chestSlotX(), 6, 0x404040);

        int labelY = playerSlotY() - 12 - INVENTORY_LABEL_SHIFT_UP;
        fontRenderer.drawString(I18n.getString("container.inventory"),
                playerSlotX(),
                labelY,
                0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mx, int my) {
        GL11.glColor4f(1,1,1,1);
        mc.renderEngine.bindTexture(TEX);
        int gl = (width  - xSize)/2;
        int gt = (height - ySize)/2;

        blit(gl + MAIN_U,      gt + MAIN_V,      MAIN_U,   MAIN_V,   MAIN_W,   MAIN_H);
        blit(gl + PLAYER_BG_X, gt + PLAYER_BG_Y, PLAYER_U, PLAYER_V, PLAYER_W, PLAYER_H);
    }

    private void blit(int x,int y,int u,int v,int w,int h){
        float fW = 1f / TEX_W;
        float fH = 1f / TEX_H;
        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x,     y+h, zLevel, u*fW,(v+h)*fH);
        t.addVertexWithUV(x+w,   y+h, zLevel,(u+w)*fW,(v+h)*fH);
        t.addVertexWithUV(x+w,   y,   zLevel,(u+w)*fW, v*fH);
        t.addVertexWithUV(x,     y,   zLevel, u*fW, v*fH);
        t.draw();
    }
}