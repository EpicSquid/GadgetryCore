package epicsquid.gadgetry.core.block.fluid;

import epicsquid.gadgetry.core.block.IBlock;
import epicsquid.gadgetry.core.lib.model.IModeledObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFluid extends BlockFluidClassic implements IBlock, IModeledObject {
  String modid = "";

  public BlockFluid(String modid, String name, boolean addToTab, Material material, Fluid fluid) {
    super(fluid, material);
    setRegistryName(modid + ":" + name);
    this.setQuantaPerBlock(6);
    this.modid = modid;
    fluid.setBlock(this);
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getBlockState().getBaseState().withProperty(LEVEL, meta);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void initModel() {
    Block block = this;
    Item item = Item.getItemFromBlock(block);

    ModelBakery.registerItemVariants(item);

    final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(modid + ":fluid", stack.getFluid().getName());

    ModelLoader.setCustomModelResourceLocation(item, 0, modelResourceLocation);

    ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
      @Override
      protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        return modelResourceLocation;
      }
    });
  }

  @Override
  public Item getItemBlock() {
    return null;
  }
}
