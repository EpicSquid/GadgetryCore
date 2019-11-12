package epicsquid.gadgetry.core.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import static epicsquid.gadgetry.core.GadgetryCore.REGISTRY;

@SuppressWarnings("unused")
public class ModBlocks {

  public static RegistryObject<Block> REDMETAL_BLOCK = REGISTRY.registerBlock(ModMaterials.REDMETAL.blockName(), REGISTRY.block(Block::new, ModMaterials.REDMETAL.getBlockProps()), ModRegistries.SIG);

  public static void load() {
  }
}
