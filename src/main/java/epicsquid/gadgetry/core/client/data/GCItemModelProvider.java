package epicsquid.gadgetry.core.client.data;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.gadgetry.core.init.ModItems;
import epicsquid.mysticallib.client.data.DeferredItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class GCItemModelProvider extends DeferredItemModelProvider {

	public GCItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super("Gadgetry Core Item Model Generator", generator, GadgetryCore.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		blockItem(ModBlocks.REDMETAL_BLOCK);

		generated(ModItems.REDMETAL_INGOT);
		generated(ModItems.REDMETAL_NUGGET);
	}
}
