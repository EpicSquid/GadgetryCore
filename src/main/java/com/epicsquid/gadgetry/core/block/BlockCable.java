package epicsquid.gadgetry.core.block;

import java.util.ArrayList;
import java.util.List;

import epicsquid.gadgetry.core.lib.block.BlockTEBase;
import epicsquid.gadgetry.core.lib.tile.TileCable;
import epicsquid.gadgetry.core.lib.tile.TileModular;
import epicsquid.gadgetry.core.lib.tile.module.FaceConfig.FaceIO;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCable extends BlockTEBase {
  public static final PropertyBool up = PropertyBool.create("up"), down = PropertyBool.create("down"), east = PropertyBool.create("east"), west = PropertyBool
      .create("west"), north = PropertyBool.create("north"), south = PropertyBool.create("south");

  public static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875);
  public static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.6875, 0.3125, 0.3125, 1.0, 0.6875, 0.6875);
  public static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0, 0.3125, 0.3125, 0.3125, 0.6875, 0.6875);
  public static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.3125, 0.6875, 0.3125, 0.6875, 1.0, 0.6875);
  public static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.3125, 0.6875);
  public static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.3125, 0.3125, 0.6875, 0.6875, 0.6875, 1.0);
  public static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.3125, 0.3125, 0, 0.6875, 0.6875, 0.3125);

  public BlockCable(Material mat, SoundType type, float hardness, String name, Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    setOpacity(false);
    setLightOpacity(0);
  }

  public void pollBoxes(List<AxisAlignedBB> boxes, IBlockAccess world, BlockPos pos) {
    TileModular t = (TileModular) world.getTileEntity(pos);
    boxes.add(AABB_BASE);
    if (t.config.ioConfig.get(EnumFacing.UP) != FaceIO.NEUTRAL) {
      boxes.add(AABB_UP);
    }
    if (t.config.ioConfig.get(EnumFacing.DOWN) != FaceIO.NEUTRAL) {
      boxes.add(AABB_DOWN);
    }
    if (t.config.ioConfig.get(EnumFacing.EAST) != FaceIO.NEUTRAL) {
      boxes.add(AABB_EAST);
    }
    if (t.config.ioConfig.get(EnumFacing.WEST) != FaceIO.NEUTRAL) {
      boxes.add(AABB_WEST);
    }
    if (t.config.ioConfig.get(EnumFacing.NORTH) != FaceIO.NEUTRAL) {
      boxes.add(AABB_NORTH);
    }
    if (t.config.ioConfig.get(EnumFacing.SOUTH) != FaceIO.NEUTRAL) {
      boxes.add(AABB_SOUTH);
    }
  }

  @Override
  public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity,
      boolean advanced) {
    List<AxisAlignedBB> boxes = new ArrayList<AxisAlignedBB>();
    pollBoxes(boxes, world, pos);
    for (AxisAlignedBB b : boxes) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, b);
    }
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    TileModular t = (TileModular) world.getTileEntity(pos);
    double minX = 0.3125;
    double minY = 0.3125;
    double minZ = 0.3125;
    double maxX = 0.6875;
    double maxY = 0.6875;
    double maxZ = 0.6875;
    if (t != null && t.config != null && t.config.ioConfig != null) {
      if (t.config.ioConfig.get(EnumFacing.UP) != FaceIO.NEUTRAL) {
        maxY = 1.0;
      }
      if (t.config.ioConfig.get(EnumFacing.DOWN) != FaceIO.NEUTRAL) {
        minY = 0.0;
      }
      if (t.config.ioConfig.get(EnumFacing.EAST) != FaceIO.NEUTRAL) {
        maxX = 1.0;
      }
      if (t.config.ioConfig.get(EnumFacing.WEST) != FaceIO.NEUTRAL) {
        minX = 0.0;
      }
      if (t.config.ioConfig.get(EnumFacing.NORTH) != FaceIO.NEUTRAL) {
        minZ = 0.0;
      }
      if (t.config.ioConfig.get(EnumFacing.SOUTH) != FaceIO.NEUTRAL) {
        maxZ = 1.0;
      }
    }
    return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, up, down, east, west, north, south);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState();
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
    TileEntity t = world.getTileEntity(pos);
    boolean fup = false, fdown = false, fnorth = false, fsouth = false, feast = false, fwest = false;
    if (t instanceof TileCable) {
      TileCable c = (TileCable) t;
      fnorth = c.canConnect(EnumFacing.NORTH);
      fsouth = c.canConnect(EnumFacing.SOUTH);
      feast = c.canConnect(EnumFacing.EAST);
      fwest = c.canConnect(EnumFacing.WEST);
      fup = c.canConnect(EnumFacing.UP);
      fdown = c.canConnect(EnumFacing.DOWN);
      IBlockState s = getDefaultState().withProperty(north, fnorth).withProperty(south, fsouth).withProperty(east, feast).withProperty(west, fwest)
          .withProperty(up, fup).withProperty(down, fdown);
      c.updateConnections(state);
      return s;
    }
    return state;
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    TileCable c = (TileCable) world.getTileEntity(pos);
    c.updateConnections(state);
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack) {
    TileCable c = (TileCable) world.getTileEntity(pos);
    c.updateConnections(state);
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getActualState(getDefaultState(), world, pos);
  }
}
