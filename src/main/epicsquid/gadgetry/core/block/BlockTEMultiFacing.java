package epicsquid.gadgetry.core.block;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import epicsquid.gadgetry.core.lib.block.multiblock.IMultiblock;
import epicsquid.gadgetry.core.lib.tile.multiblock.IMaster;
import epicsquid.gadgetry.core.lib.tile.multiblock.ISlave;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTEMultiFacing extends BlockTEFacing implements IMultiblock {

  public BlockTEMultiFacing(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    this.setNoCull(true);
  }

  public void addSlave(World world, IMaster master, BlockPos masterPos, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state, 8);
    world.notifyBlockUpdate(pos, Blocks.AIR.getDefaultState(), state, 8);
    master.addSlave(pos);
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ISlave) {
      ((ISlave) t).setMaster(masterPos);
    }
    world.notifyNeighborsOfStateChange(pos, this, true);
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {
    Set<BlockPos> s = getSlavePositions(pos, EnumFacing.UP).keySet();
    for (BlockPos p : s) {
      IBlockState st = world.getBlockState(p);
      if (!st.getBlock().isReplaceable(world, p) && st.getBlock() != Blocks.AIR) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
    return canPlaceBlockAt(world, pos);
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof IMaster) {
      Map<BlockPos, IBlockState> m = getSlavePositions(pos, EnumFacing.UP);
      for (Entry<BlockPos, IBlockState> p : m.entrySet()) {
        addSlave(world, (IMaster) t, pos, p.getKey(), p.getValue());
      }
    }
    t.markDirty();
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    Set<BlockPos> positions = getSlavePositions(pos, EnumFacing.UP).keySet();
    int[] lmap = new int[positions.size()];
    int ct = 0;
    for (BlockPos p : positions) {
      lmap[ct] = world.getCombinedLight(pos, 1);
      ct++;
    }
    int max = lmap[0];
    for (int i = 1; i < lmap.length; i++) {
      if (lmap[i] > max) {
        max = lmap[i];
      }
    }
    return max;
  }

  @Override
  public int getPackedLightmapCoords(IBlockState state, IBlockAccess world, BlockPos pos) {
    Set<BlockPos> positions = getSlavePositions(pos, EnumFacing.UP).keySet();
    int[] lmap = new int[positions.size()];
    int ct = 0;
    for (BlockPos p : positions) {
      lmap[ct] = world.getBlockState(p).getPackedLightmapCoords(world, p);
      ct++;
    }
    int max = lmap[0];
    for (int i = 1; i < lmap.length; i++) {
      if (lmap[i] > max) {
        max = lmap[i];
      }
    }
    return max;
  }
}
