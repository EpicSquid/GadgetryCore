package epicsquid.gadgetry.core.client.data;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import net.minecraft.data.DataGenerator;

public class GCLangProvider extends DeferredLanguageProvider {
	public GCLangProvider(DataGenerator gen) {
		super(gen, GadgetryCore.MODID);
	}

	@Override
	protected void addTranslations() {
	}
}
