package epicsquid.gadgetry.core.tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import epicsquid.gadgetry.core.lib.tile.TileModular;
import epicsquid.gadgetry.core.lib.tile.multiblock.IMaster;
import epicsquid.gadgetry.core.lib.tile.multiblock.ISlave;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileMultiModular extends TileModular implements IMaster {
  List<BlockPos> slaves = new ArrayList<>();

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    tag.setTag("list", IMaster.writePosList(slaves));
    return super.writeToNBT(tag);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    slaves = IMaster.readPosList(tag, "list");
  }

  @Override
  public <T> T getCapability(Capability<T> arg0, EnumFacing arg1, BlockPos arg2) {
    return getCapability(arg0, arg1);
  }

  @Override
  public Collection<BlockPos> getSlaves() {
    return slaves;
  }

  @Override
  public void addSlave(BlockPos p) {
    slaves.add(p);
    markDirty();
  }

  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
    for (BlockPos p : getSlaves()) {
      TileEntity t = world.getTileEntity(p);
      if (t instanceof ISlave) {
        ((ISlave) t).setMaster(pos);
      }
    }
    return super.shouldRefresh(world, pos, oldState, newState);
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    IBlockState s = getWorld().getBlockState(getPos());
    super.breakBlock(getWorld(), getPos(), s, player);
    super.invalidate();
    world.playEvent(2001, getPos(), Block.getStateId(s));
    for (BlockPos p : slaves) {
      world.playEvent(2001, p, Block.getStateId(s));
    }
    breakSlaves(world);
    world.destroyBlock(getPos(), player == null || player != null && !player.capabilities.isCreativeMode);
  }

  @Override
  public boolean hasCapability(Capability arg0, EnumFacing arg1, BlockPos arg2) {
    return hasCapability(arg0, arg1);
  }
}
