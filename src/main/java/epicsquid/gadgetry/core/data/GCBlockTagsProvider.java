package epicsquid.gadgetry.core.data;

import epicsquid.mysticallib.data.DeferredBlockTagsProvider;
import net.minecraft.data.DataGenerator;

public class GCBlockTagsProvider extends DeferredBlockTagsProvider {
	public GCBlockTagsProvider(DataGenerator generatorIn) {
		super(generatorIn, "Gadgetry Core Block Tags Provider");
	}

	@Override
	protected void registerTags() {
	}
}
