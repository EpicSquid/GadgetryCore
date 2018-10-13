package epicsquid.gadgetry.core.item;

import epicsquid.gadgetry.core.lib.item.ItemBase;
import epicsquid.gadgetry.core.lib.tile.TileCable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemWrench extends ItemBase {
  public ItemWrench(String name) {
    super(name);
  }

  public static EnumFacing faceFromHit(float hitX, float hitY, float hitZ) {
    float dx = hitX - 0.5f;
    float dy = hitY - 0.5f;
    float dz = hitZ - 0.5f;
    float adx = Math.abs(dx);
    float ady = Math.abs(dy);
    float adz = Math.abs(dz);
    if (adx > ady && adx > adz) {
      return dx > 0 ? EnumFacing.EAST : EnumFacing.WEST;
    } else if (ady > adz) {
      return dy > 0 ? EnumFacing.UP : EnumFacing.DOWN;
    } else {
      return dz > 0 ? EnumFacing.SOUTH : EnumFacing.NORTH;
    }
  }

  @Override
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
    IBlockState state = world.getBlockState(pos);
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof TileCable) {
      EnumFacing f = faceFromHit(hitX, hitY, hitZ);
      boolean canConnect = ((TileCable) t).canConnect(f);
      ((TileCable) t).togglePipe(f);
      boolean newConnect = ((TileCable) t).canConnect(f);
      if (canConnect != newConnect) {
        world.notifyBlockUpdate(pos, state, state.getBlock().getActualState(state, world, pos), 8);
      }
      return EnumActionResult.SUCCESS;
    }
    return EnumActionResult.FAIL;
  }
}
