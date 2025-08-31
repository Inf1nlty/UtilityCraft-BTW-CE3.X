package com.inf1nlty.extendedcraft.block;

import com.inf1nlty.extendedcraft.block.tileentity.TileEntityColoredCement;
import com.inf1nlty.extendedcraft.material.ColoredCementMaterial;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class BlockColoredCement extends BlockContainer {

    public static final int MAX_CEMENT_SPREAD_DIST = 16;
    public static final int CEMENT_TICKS_TO_DRY = 12;
    public static final int CEMENT_TICKS_TO_PARTIALLY_DRY = 8;

    private final Block concreteBlock;

    @Environment(EnvType.CLIENT)
    private Icon[] wetIcons;
    @Environment(EnvType.CLIENT)
    private Icon[] dryingIcons;

    private final boolean[] tempSpreadToSideFlags = new boolean[4];
    private static final String[] COLOR_NAMES = {
            "white","orange","magenta","light_blue","yellow","lime","pink","gray",
            "light_gray","cyan","purple","blue","brown","green","red","black"
    };

    public BlockColoredCement(int id, Block concreteBlock) {
        super(id, new ColoredCementMaterial(MapColor.stoneColor));
        this.concreteBlock = concreteBlock;
        setHardness(100F);
        setLightOpacity(255);
        setUnlocalizedName("colored_cement");
        setStepSound(Block.soundSandFootstep);
        setTickRandomly(true);
        setShovelsEffectiveOn();
        setCreativeTab(null);
        Block.useNeighborBrightness[id] = true;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityColoredCement();
    }

    @Override public boolean renderAsNormalBlock() { return false; }
    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean canCollideCheck(int meta, boolean flag) { return flag && meta == 0; }
    @Override public int idDropped(int meta, Random rand, int fortune) { return 0; }
    @Override public int quantityDropped(Random rand) { return 0; }
    @Override public int tickRate(World world) { return 20; }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        if (world.getBlockId(x, y + 1, z) != blockID)
            return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+0.5F, z+1);
        return AxisAlignedBB.getAABBPool().getAABB(x, y, z, x+1, y+1F, z+1);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        if (world.getBlockId(x, y, z) == blockID) {
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate(world));
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote) return;
        int spreadDist = getCementSpreadDist(world, x, y, z);
        if (spreadDist < 0) return;
        int meta = world.getBlockMetadata(x, y, z);

        if (spreadDist > 0) {
            int newDist = -100;
            newDist = checkForLesserSpreadDist(world, x-1,y,z,newDist);
            newDist = checkForLesserSpreadDist(world, x+1,y,z,newDist);
            newDist = checkForLesserSpreadDist(world, x,y,z-1,newDist);
            newDist = checkForLesserSpreadDist(world, x,y,z+1,newDist);
            newDist = checkForLesserSpreadDist(world, x,y+1,z,newDist);
            if (newDist < 0) newDist = -1; else newDist++;
            int distUp = getCementSpreadDist(world, x, y+1, z);
            if (distUp >=0 && distUp < newDist) newDist = distUp + 1;
            if (newDist > 0 && newDist < spreadDist) {
                spreadDist = newDist;
                setCementSpreadDist(world,x,y,z,spreadDist);
                setCementDryTime(world,x,y,z,0);
            }
        }

        int dryTime = getCementDryTime(world,x,y,z)+1;
        int minDry = checkNeighboursCloserToSourceForMinDryTime(world,x,y,z);
        if (minDry <= dryTime) dryTime = (minDry <= 0)?0:(minDry-1);

        if (dryTime > CEMENT_TICKS_TO_DRY) {
            world.setBlockAndMetadataWithNotify(x,y,z,concreteBlock.blockID, meta);
            return;
        } else {
            setCementDryTime(world,x,y,z,dryTime);
            world.scheduleBlockUpdate(x,y,z,blockID,tickRate(world));
            if (isBlockOpenToSpread(world,x,y-1,z)) {
                int targetDist = spreadDist+1;
                if (targetDist <= MAX_CEMENT_SPREAD_DIST) {
                    world.setBlockAndMetadataWithNotify(x,y-1,z,blockID,meta);
                    setCementSpreadDist(world,x,y-1,z,targetDist);
                }
            } else if (spreadDist >=0 && (spreadDist==0 || blockBlocksFlow(world,x,y-1,z))) {
                boolean[] flags = checkSideBlocksForPotentialSpread(world,x,y,z);
                int newSpread = spreadDist+1;
                if (newSpread <= MAX_CEMENT_SPREAD_DIST) {
                    if (flags[0]) attemptToSpreadToBlock(world,x-1,y,z,newSpread,meta);
                    if (flags[1]) attemptToSpreadToBlock(world,x+1,y,z,newSpread,meta);
                    if (flags[2]) attemptToSpreadToBlock(world,x,y,z-1,newSpread,meta);
                    if (flags[3]) attemptToSpreadToBlock(world,x,y,z+1,newSpread,meta);
                }
            }
        }
    }

    // TE helpers
    private TileEntityColoredCement getTE(IBlockAccess a,int x,int y,int z){
        TileEntity t = a.getBlockTileEntity(x,y,z);
        return (t instanceof TileEntityColoredCement)?(TileEntityColoredCement)t:null;
    }
    public int getCementSpreadDist(IBlockAccess a,int x,int y,int z){
        TileEntityColoredCement te = getTE(a,x,y,z);
        return te==null?-1:te.getSpreadDist();
    }
    public int getCementDryTime(IBlockAccess a,int x,int y,int z){
        TileEntityColoredCement te = getTE(a,x,y,z);
        return te==null?0:te.getDryTime();
    }
    public void setCementSpreadDist(World w,int x,int y,int z,int d){
        TileEntityColoredCement te = getTE(w,x,y,z);
        if (te!=null){
            te.setSpreadDist(d);
            w.notifyBlocksOfNeighborChange(x,y,z,blockID);
            w.markBlockRangeForRenderUpdate(x,y,z,x,y,z);
        }
    }
    public void setCementDryTime(World w,int x,int y,int z,int dry){
        TileEntityColoredCement te = getTE(w,x,y,z);
        if (te!=null){
            te.setDryTime(dry);
            w.notifyBlocksOfNeighborChange(x,y,z,blockID);
            w.markBlockRangeForRenderUpdate(x,y,z,x,y,z);
        }
    }
    public boolean isCementPartiallyDry(IBlockAccess a,int x,int y,int z){
        return getCementDryTime(a,x,y,z) >= CEMENT_TICKS_TO_PARTIALLY_DRY;
    }

    private int checkNeighboursCloserToSourceForMinDryTime(World w,int x,int y,int z){
        int min=1000;
        int dist=getCementSpreadDist(w,x,y,z);
        min=getLesserDryTimeIfCloser(w,x,y+1,z,dist,min);
        min=getLesserDryTimeIfCloser(w,x+1,y,z,dist,min);
        min=getLesserDryTimeIfCloser(w,x-1,y,z,dist,min);
        min=getLesserDryTimeIfCloser(w,x,y,z+1,dist,min);
        min=getLesserDryTimeIfCloser(w,x,y,z-1,dist,min);
        return min;
    }
    private int getLesserDryTimeIfCloser(World w,int x,int y,int z,int srcDist,int dryTime){
        if (w.getBlockMaterial(x,y,z)==blockMaterial){
            int targetDist=getCementSpreadDist(w,x,y,z);
            if (targetDist < srcDist){
                int targetDry=getCementDryTime(w,x,y,z);
                if (targetDry < dryTime) return targetDry;
            }
        }
        return dryTime;
    }
    private int checkForLesserSpreadDist(World w,int x,int y,int z,int src){
        int t=getCementSpreadDist(w,x,y,z);
        if (t<0) return src;
        if (src<0 || t<src) return t;
        return src;
    }

    private boolean isBlockOpenToSpread(World w,int x,int y,int z){
        if (y<0) return false;
        Material m=w.getBlockMaterial(x,y,z);
        if (m==blockMaterial) return false;
        return !blockBlocksFlow(w,x,y,z);
    }
    private boolean blockBlocksFlow(World w,int x,int y,int z){
        Block b=Block.blocksList[w.getBlockId(x,y,z)];
        return b!=null && b.blockMaterial!=blockMaterial && b.getPreventsFluidFlow(w,x,y,z,this);
    }
    private void attemptToSpreadToBlock(World w,int x,int y,int z,int newDist,int meta){
        if (isBlockOpenToSpread(w,x,y,z)){
            int existing=w.getBlockId(x,y,z);
            if (existing>0){
                Block.blocksList[existing].dropBlockAsItem(w,x,y,z,w.getBlockMetadata(x,y,z),0);
            }
            w.setBlockAndMetadataWithNotify(x,y,z,blockID,meta);
            setCementSpreadDist(w,x,y,z,newDist);
            setCementDryTime(w,x,y,z,0);
            w.scheduleBlockUpdate(x,y,z,blockID,tickRate(w));
        }
    }
    private boolean[] checkSideBlocksForPotentialSpread(World w,int x,int y,int z){
        for(int i=0;i<4;i++){
            int nx=x, ny=y, nz=z;
            switch(i){
                case 0 -> nx--;
                case 1 -> nx++;
                case 2 -> nz--;
                case 3 -> nz++;
            }
            if (blockBlocksFlow(w,nx,ny,nz) ||
                    (w.getBlockMaterial(nx,ny,nz)==blockMaterial &&
                            getCementSpreadDist(w,nx,ny,nz)==0)){
                tempSpreadToSideFlags[i]=false;
            } else tempSpreadToSideFlags[i]=true;
        }
        return tempSpreadToSideFlags;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess access,int nx,int ny,int nz,int side){
        Material m = access.getBlockMaterial(nx,ny,nz);
        if (m == blockMaterial) return false;
        if (m == Material.ice) return false;
        if (side == 1) return true;
        return super.shouldSideBeRendered(access,nx,ny,nz,side);
    }

    // ----- Render core -----
    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer,int x,int y,int z){
        return renderColoredCement(renderer, renderer.blockAccess, x,y,z,this);
    }

    public float getRenderHeight(IBlockAccess access,int x,int y,int z){
        if (access.getBlockMaterial(x,y,z)!=blockMaterial) return 1F;
        int dist=getCementSpreadDist(access,x,y,z);
        float h=(dist+1F)/(MAX_CEMENT_SPREAD_DIST+2F);
        if (isCementPartiallyDry(access,x,y,z)) h*=0.10F; else h*=0.5F;
        return h;
    }

    @Environment(EnvType.CLIENT)
    public float getCornerHeightFromNeighbours(IBlockAccess a,int x,int y,int z){
        int factors=0; float sum=0F;
        for(int n=0;n<4;n++){
            int tx = x - (n & 1);
            int tz = z - (n >> 1 & 1);
            if (a.getBlockMaterial(tx,y+1,tz)==blockMaterial) return 1F;
            Material m=a.getBlockMaterial(tx,y,tz);
            if (m==blockMaterial){
                if (a.isBlockOpaqueCube(tx,y+1,tz)) return 1F;
                if (getCementSpreadDist(a,tx,y,tz)==0){
                    sum += getRenderHeight(a,tx,y,tz)*10F;
                    factors+=10;
                }
                sum+=getRenderHeight(a,tx,y,tz); factors++;
            } else if (!m.isSolid()){
                sum+=0.60F; factors++;
            }
        }
        return (factors>0)? 1F - sum/factors : 1F;
    }

    @Environment(EnvType.CLIENT)
    public boolean renderColoredCement(RenderBlocks rb, IBlockAccess a, int x, int y, int z, Block block) {
        Tessellator t = Tessellator.instance;

        boolean bTopFaceFlag = block.shouldSideBeRendered(a, x, y+1, z, 1);
        boolean bBottomFaceFlag = block.shouldSideBeRendered(a, x, y-1, z, 0);
        boolean bSideFaceFlags[] = new boolean[4];
        bSideFaceFlags[0] = block.shouldSideBeRendered(a, x, y, z-1, 2);
        bSideFaceFlags[1] = block.shouldSideBeRendered(a, x, y, z+1, 3);
        bSideFaceFlags[2] = block.shouldSideBeRendered(a, x-1, y, z, 4);
        bSideFaceFlags[3] = block.shouldSideBeRendered(a, x+1, y, z, 5);

        if(!bTopFaceFlag && !bBottomFaceFlag && !bSideFaceFlags[0] && !bSideFaceFlags[1] && !bSideFaceFlags[2] && !bSideFaceFlags[3])
            return false;

        int meta = a.getBlockMetadata(x, y, z);
        if (meta < 0 || meta > 15) meta = 0;

        float cornerHeight1 = getCornerHeightFromNeighbours(a, x, y, z);
        float cornerHeight2 = getCornerHeightFromNeighbours(a, x, y, z+1);
        float cornerHeight3 = getCornerHeightFromNeighbours(a, x+1, y, z+1);
        float cornerHeight4 = getCornerHeightFromNeighbours(a, x+1, y, z);

        boolean hasRendered = false;

        // Top
        if (bTopFaceFlag) {
            hasRendered = true;
            Icon i1 = block.getBlockTexture(a, x, y, z, 1);
            double x1 = i1.getMinU();
            double x2 = i1.getMaxU();
            double y1 = i1.getMinV();
            double y2 = i1.getMaxV();
            t.setBrightness(block.getMixedBrightnessForBlock(a, x, y+1, z));
            t.setColorOpaque_F(1, 1, 1);
            t.addVertexWithUV(x,   y+cornerHeight1, z,   x1, y1);
            t.addVertexWithUV(x,   y+cornerHeight2, z+1, x1, y2);
            t.addVertexWithUV(x+1, y+cornerHeight3, z+1, x2, y2);
            t.addVertexWithUV(x+1, y+cornerHeight4, z,   x2, y1);
        }

        // Bottom
        if (bBottomFaceFlag) {
            t.setBrightness(block.getMixedBrightnessForBlock(a, x, y-1, z));
            t.setColorOpaque_F(1, 1, 1);
            rb.renderFaceYNeg(block, x, y, z, block.getBlockTexture(a, x, y, z, 0));
            hasRendered = true;
        }

        // Sides
        for (int iSide = 0; iSide < 4; iSide++) {
            int k1 = x;
            int i2 = y;
            int k2 = z;
            if (iSide == 0) { k2--; }
            else if (iSide == 1) { k2++; }
            else if (iSide == 2) { k1--; }
            else if (iSide == 3) { k1++; }

            if (bSideFaceFlags[iSide]) {
                float f10, f12, f14, f17, f16, f18;
                if ( iSide == 0 ) { f10 = cornerHeight1; f12 = cornerHeight4; f14 = x; f17 = x+1; f16 = z; f18 = z; }
                else if (iSide == 1) { f10 = cornerHeight3; f12 = cornerHeight2; f14 = x+1; f17 = x; f16 = z+1; f18 = z+1; }
                else if (iSide == 2) { f10 = cornerHeight2; f12 = cornerHeight1; f14 = x; f17 = x; f16 = z+1; f18 = z; }
                else { f10 = cornerHeight4; f12 = cornerHeight3; f14 = x+1; f17 = x+1; f16 = z; f18 = z+1; }

                Icon l2 = block.getBlockTexture(a, x, y, z, iSide+2);
                double d4 = l2.getMinU();
                double d5 = l2.getMaxU();
                double d6 = l2.getInterpolatedV((1.0D - f10) * 16D);
                double d7 = l2.getInterpolatedV((1.0F - f12) * 16D);
                double d8 = l2.getMaxV();
                t.setBrightness(block.getMixedBrightnessForBlock(a, k1, i2, k2));
                t.setColorOpaque_F(1, 1, 1);
                t.addVertexWithUV(f14, (float)y + f10, f16, d4, d6);
                t.addVertexWithUV(f17, (float)y + f12, f18, d5, d7);
                t.addVertexWithUV(f17, y, f18, d5, d8);
                t.addVertexWithUV(f14, y, f16, d4, d8);
                hasRendered = true;
            }
        }
        return hasRendered;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister reg){
        wetIcons=new Icon[16];
        dryingIcons=new Icon[16];
        for(int i=0;i<16;i++){
            wetIcons[i]=reg.registerIcon("extendedcraft:"+COLOR_NAMES[i]+"_cement");
            dryingIcons[i]=reg.registerIcon("extendedcraft:"+COLOR_NAMES[i]+"_concrete_powder");
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side,int meta){
        if (meta<0 || meta>15) meta=0;
        return wetIcons!=null? wetIcons[meta]: null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getBlockTexture(IBlockAccess a,int x,int y,int z,int side){
        int meta=a.getBlockMetadata(x,y,z);
        if (meta<0 || meta>15) meta=0;
        if (isCementPartiallyDry(a,x,y,z))
            return dryingIcons!=null? dryingIcons[meta]: null;
        return wetIcons!=null? wetIcons[meta]: null;
    }

    @Override
    public String getLocalizedName(){ return ""; }
}