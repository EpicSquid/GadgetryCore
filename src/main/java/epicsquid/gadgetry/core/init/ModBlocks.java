package epicsquid.gadgetry.core.init;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;

import static epicsquid.gadgetry.core.GadgetryCore.REGISTRY;

@SuppressWarnings("unused")
public class ModBlocks {

  public static RegistryObject<Block> REDMETAL_BLOCK = REGISTRY.registerBlock(ModMaterials.REDMETAL.blockName(), REGISTRY.block(Block::new, ModMaterials.REDMETAL.getBlockProps()), ModRegistries.SIG);
  public static RegistryObject<Block> REDMETAL_PLATE_BLOCK = REGISTRY.registerBlock("redmetal_plate_block", REGISTRY.block(Block::new, ModMaterials.REDMETAL.getBlockProps()), ModRegistries.SIG);
  public static RegistryObject<Block> STEEL_BLOCK = REGISTRY.registerBlock(ModMaterials.STEEL.blockName(), REGISTRY.block(Block::new, ModMaterials.STEEL.getBlockProps()), ModRegistries.SIG);
  public static RegistryObject<Block> STEEL_PLATE_BLOCK = REGISTRY.registerBlock("steel_plate_block", REGISTRY.block(Block::new, ModMaterials.STEEL.getBlockProps()), ModRegistries.SIG);
  public static RegistryObject<Block> IRON_PLATE_BLOCK = REGISTRY.registerBlock("iron_plate_block", REGISTRY.block(Block::new, () -> Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), ModRegistries.SIG);

  public static void load() {
  }
}
