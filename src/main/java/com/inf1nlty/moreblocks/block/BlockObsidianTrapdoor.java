package com.inf1nlty.moreblocks.block;

import btw.block.blocks.LadderBlockBase;
import btw.util.sounds.BTWSoundManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class BlockObsidianTrapdoor extends Block {
    public static final double THICKNESS = 0.1875D;
    private static boolean flagBetterPlaceRule;
    private static boolean betterPlaceRuleIsLower;
    private Icon blockIcon;

    public BlockObsidianTrapdoor(int blockID) {
        super(blockID, Material.rock);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(10.0F);
        this.setResistance(2000.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("moreblocks:obsidian_trapdoor");
        this.disableStats();
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("moreblocks:obsidian_trapdoor");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderBlocks, int meta, float brightness) {
        final float thickness = (float) THICKNESS;
        final float yCenter = (1.0F - thickness) / 2.0F;
        renderBlocks.setRenderBounds(0.0F, yCenter, 0.0F, 1.0F, yCenter + thickness, 1.0F);
        Tessellator tess = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        for (int side = 0; side < 6; ++side) {
            tess.startDrawingQuads();
            tess.setNormal(
                    side == 0 ? 0 : (side == 1 ? 0 : (side == 2 ? 0 : (side == 3 ? 0 : (side == 4 ? -1 : 1)))),
                    side == 0 ? -1 : (side == 1 ? 1 : 0),
                    side == 2 ? -1 : (side == 3 ? 1 : 0)
            );
            switch (side) {
                case 0: renderBlocks.renderFaceYNeg(this, 0.0D, 0.0D, 0.0D, getIcon(0, meta)); break;
                case 1: renderBlocks.renderFaceYPos(this, 0.0D, 0.0D, 0.0D, getIcon(1, meta)); break;
                case 2: renderBlocks.renderFaceZNeg(this, 0.0D, 0.0D, 0.0D, getIcon(2, meta)); break;
                case 3: renderBlocks.renderFaceZPos(this, 0.0D, 0.0D, 0.0D, getIcon(3, meta)); break;
                case 4: renderBlocks.renderFaceXNeg(this, 0.0D, 0.0D, 0.0D, getIcon(4, meta)); break;
                case 5: renderBlocks.renderFaceXPos(this, 0.0D, 0.0D, 0.0D, getIcon(5, meta)); break;
            }
            tess.draw();
        }
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
    }

    public static boolean isTrapdoorOpen(int meta) {
        return (meta & 4) != 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, metadata ^ 4);
        if (isTrapdoorOpen(metadata)) {
            world.playSound(x, y, z, BTWSoundManager.TRAPDOOR_CLOSE.sound(), 1.0F, 1.0F);
        } else {
            world.playSound(x, y, z, BTWSoundManager.TRAPDOOR_OPEN.sound(), 1.0F, 1.0F);
        }

        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        int newMetadata = switch (side) {
            case 3 -> 1;
            case 4 -> 2;
            case 5 -> 3;
            default -> 0;
        };
        if (side != 1 && side != 0 && hitY > 0.5F) {
            newMetadata += 8;
        }
        if (side == 0 || side == 1) {
            flagBetterPlaceRule = true;
            betterPlaceRuleIsLower = (side == 1);
        }
        return newMetadata;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        if (flagBetterPlaceRule) {
            int facing = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            int metadataMod = betterPlaceRuleIsLower ? 0 : 8;
            int newMeta = metadataMod + switch (facing) {
                case 1 -> 3;
                case 2 -> 1;
                case 3 -> 2;
                default -> 0;
            };
            world.setBlockMetadataWithNotify(x, y, z, newMeta, 2);
            flagBetterPlaceRule = false;
        }
    }

    public boolean isBlockClimbable(World world, int x, int y, int z) {
        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
        return blockBelow instanceof LadderBlockBase && world.getBlockMetadata(x, y - 1, z) == (world.getBlockMetadata(x, y, z) & 3);
    }

    public int getWeightOnPathBlocked(IBlockAccess blockAccess, int x, int y, int z) {
        return -4;
    }

    public boolean canPathThroughBlock(IBlockAccess blockAccess, int x, int y, int z, Entity entity, PathFinder pathFinder) {
        return pathFinder.CanPathThroughClosedWoodDoor() || pathFinder.canPathThroughOpenWoodDoor() && this.getBlocksMovement(blockAccess, x, y, z);
    }

    public boolean isBreakableBarricade(World world, int x, int y, int z) {
        return true;
    }

    public boolean isBreakableBarricadeOpen(IBlockAccess blockAccess, int x, int y, int z) {
        return isTrapdoorOpen(blockAccess.getBlockMetadata(x, y, z));
    }

    public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
        return isTrapdoorOpen(blockAccess.getBlockMetadata(x, y, z));
    }

    public int getHarvestToolLevel(IBlockAccess blockAccess, int x, int y, int z) {
        return 3;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
    }

    @SuppressWarnings("deprecation")
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (float)THICKNESS, 1.0F);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return this.getBlockBoundsFromPoolBasedOnState(world, x, y, z).offset(x, y, z);
    }

    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);
        if (isTrapdoorOpen(metadata)) {
            int direction = metadata & 3;
            return switch (direction) {
                case 0 -> AxisAlignedBB.getAABBPool().getAABB(0.0, 0.0, 1.0-THICKNESS, 1.0, 1.0, 1.0);
                case 1 -> AxisAlignedBB.getAABBPool().getAABB(0.0, 0.0, 0.0, 1.0, 1.0, THICKNESS);
                case 2 -> AxisAlignedBB.getAABBPool().getAABB(1.0-THICKNESS, 0.0, 0.0, 1.0, 1.0, 1.0);
                default -> AxisAlignedBB.getAABBPool().getAABB(0.0, 0.0, 0.0, THICKNESS, 1.0, 1.0);
            };
        } else {
            return (metadata & 8) != 0
                    ? AxisAlignedBB.getAABBPool().getAABB(0.0, 1.0-THICKNESS, 0.0, 1.0, 1.0, 1.0)
                    : AxisAlignedBB.getAABBPool().getAABB(0.0, 0.0, 0.0, 1.0, THICKNESS, 1.0);
        }
    }

    public boolean canItemPassIfFilter(ItemStack filteredItem) {
        int filterableProperties = filteredItem.getItem().getFilterableProperties(filteredItem);
        return (filterableProperties & 14) != 0;
    }

    public boolean getCanGrassGrowUnderBlock(World world, int x, int y, int z, boolean var5) {
        return true;
    }

    public boolean hasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int x, int y, int z, int facing, boolean var6) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);
        int facingOpposite = Facing.oppositeSide[facing];
        switch (facingOpposite) {
            case 0 -> {
                return metadata >= 8 && metadata < 12;
            }
            case 1 -> {
                return metadata < 4;
            }
            case 2 -> {
                return metadata == 4 || metadata == 12;
            }
            case 3 -> {
                return metadata == 5 || metadata == 13;
            }
            case 4 -> {
                return metadata == 6 || metadata == 14;
            }
            case 5 -> {
                return metadata == 7 || metadata == 15;
            }
            default -> {
                return false;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public Icon getHopperFilterIcon() {
        return this.blockIcon;
    }

    @Environment(EnvType.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return this.getBlockBoundsFromPoolBasedOnState(world, x, y, z).offset(x, y, z);
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side) {
        return this.currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds(neighborX, neighborY, neighborZ, side);
    }

    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderBlocks, int x, int y, int z) {
        super.setBlockBoundsForItemRender();
        super.setBlockBoundsBasedOnState(renderBlocks.blockAccess, x, y, z);
        renderBlocks.setRenderBounds(this.getBlockBoundsFromPoolBasedOnState(renderBlocks.blockAccess, x, y, z));
        switch (renderBlocks.blockAccess.getBlockMetadata(x, y, z)) {
            case 0:
            case 8:
                renderBlocks.setUVRotateTop(3);
                renderBlocks.setUVRotateBottom(3);
                break;
            case 1:
            case 9:
                renderBlocks.setUVRotateTop(0);
                renderBlocks.setUVRotateBottom(0);
                break;
            case 2:
            case 10:
            case 14:
            case 15:
                renderBlocks.setUVRotateTop(1);
                renderBlocks.setUVRotateBottom(1);
                break;
            case 3:
            case 11:
                renderBlocks.setUVRotateTop(2);
                renderBlocks.setUVRotateBottom(2);
                break;
            case 4:
            case 5:
                renderBlocks.setUVRotateEast(3);
                renderBlocks.setUVRotateWest(3);
                renderBlocks.setUVRotateNorth(1);
                renderBlocks.setUVRotateSouth(1);
                renderBlocks.setUVRotateTop(1);
                renderBlocks.setUVRotateBottom(1);
                break;
            case 6:
            case 7:
                renderBlocks.setUVRotateEast(1);
                renderBlocks.setUVRotateWest(1);
                renderBlocks.setUVRotateNorth(3);
                renderBlocks.setUVRotateSouth(3);
                renderBlocks.setUVRotateTop(1);
                renderBlocks.setUVRotateBottom(1);
            case 12:
            case 13:
        }

        renderBlocks.renderStandardBlock(this, x, y, z);
        renderBlocks.clearUVRotation();
        return true;
    }
}