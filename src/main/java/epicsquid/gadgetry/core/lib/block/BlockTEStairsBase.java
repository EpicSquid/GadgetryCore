package epicsquid.gadgetry.core.lib.block;

import epicsquid.gadgetry.core.lib.tile.ITile;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTEStairsBase extends BlockStairsBase implements ITileEntityProvider {
  Class<? extends TileEntity> teClass;

  public BlockTEStairsBase(IBlockState state, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(state, type, hardness, name);
    this.teClass = teClass;
    BlockTEBase.attemptRegistry(teClass);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY,
      float hitZ) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      return ((ITile) t).activate(world, pos, state, player, hand, face, hitX, hitY, hitZ);
    }
    return false;
  }

  @Override
  public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, state, player);
    }
  }

  @Override
  public void onBlockExploded(World world, BlockPos pos, Explosion e) {
    TileEntity t = world.getTileEntity(pos);
    if (t instanceof ITile) {
      ((ITile) t).breakBlock(world, pos, world.getBlockState(pos), null);
    }
  }

  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    try {
      return teClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

}
