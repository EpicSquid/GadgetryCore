package epicsquid.gadgetry.core.block;

import epicsquid.gadgetry.core.lib.block.BlockTEBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTEOnOffHoriz extends BlockTEBase {
  public static final PropertyDirection facing = PropertyDirection.create("facing");
  public static final PropertyBool active = PropertyBool.create("active");

  public BlockTEOnOffHoriz(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, facing, active);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(facing).getIndex() + 6 * (state.getValue(active) ? 1 : 0);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(facing, EnumFacing.getFront(meta % 6)).withProperty(active, meta >= 6);
  }

  @Override
  public int getLightValue(IBlockState state) {
    if (state.getValue(active)) {
      return 15;
    }
    return 0;
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return getDefaultState().withProperty(facing, placer.getHorizontalFacing().getOpposite()).withProperty(active, false);
  }
}
