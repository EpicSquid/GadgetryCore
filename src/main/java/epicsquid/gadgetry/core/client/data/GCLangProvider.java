package epicsquid.gadgetry.core.client.data;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.gadgetry.core.init.ModItems;
import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import net.minecraft.data.DataGenerator;

public class GCLangProvider extends DeferredLanguageProvider {
	public GCLangProvider(DataGenerator gen) {
		super(gen, GadgetryCore.MODID);
	}

	@Override
	protected void addTranslations() {
		addBlock(ModBlocks.REDMETAL_BLOCK);

		addItem(ModItems.REDMETAL_INGOT);
		addItem(ModItems.REDMETAL_NUGGET);

		addItemGroup(GadgetryCore.ITEM_GROUP, "Gadgetry Core");
	}
}
