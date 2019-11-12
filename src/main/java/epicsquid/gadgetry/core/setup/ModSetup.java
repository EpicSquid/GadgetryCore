package epicsquid.gadgetry.core.setup;

import epicsquid.gadgetry.core.client.data.GCBlockstateProvider;
import epicsquid.gadgetry.core.client.data.GCItemModelProvider;
import epicsquid.gadgetry.core.client.data.GCLangProvider;
import epicsquid.gadgetry.core.data.GCBlockTagsProvider;
import epicsquid.gadgetry.core.data.GCItemTagsProvider;
import epicsquid.gadgetry.core.data.GCLootTableProvider;
import epicsquid.gadgetry.core.data.GCRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {

	public ModSetup() {
	}

	public void init(FMLCommonSetupEvent event) {
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();
		if (event.includeClient()) {
			gen.addProvider(new GCBlockstateProvider(gen, event.getExistingFileHelper()));
			gen.addProvider(new GCItemModelProvider(gen, event.getExistingFileHelper()));
			gen.addProvider(new GCLangProvider(gen));
		}
		if (event.includeServer()) {
			gen.addProvider(new GCLootTableProvider(gen));
			gen.addProvider(new GCBlockTagsProvider(gen));
			gen.addProvider(new GCItemTagsProvider(gen));
			gen.addProvider(new GCRecipeProvider(gen));
		}
	}
}
