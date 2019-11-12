package epicsquid.gadgetry.core.client.data;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.mysticallib.client.data.DeferredBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class GCBlockstateProvider extends DeferredBlockStateProvider {

  public GCBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
    super("Gadgetry Core Blockstate and Block Model provider", gen, GadgetryCore.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    simpleBlock(ModBlocks.REDMETAL_BLOCK);
    simpleBlock(ModBlocks.REDMETAL_PLATE_BLOCK);
    simpleBlock(ModBlocks.STEEL_BLOCK);
    simpleBlock(ModBlocks.STEEL_PLATE_BLOCK);
    simpleBlock(ModBlocks.IRON_PLATE_BLOCK);
  }
}
