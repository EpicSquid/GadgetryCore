package epicsquid.gadgetry.core.data;

import epicsquid.mysticallib.data.DeferredItemTagsProvider;
import net.minecraft.data.DataGenerator;

public class GCItemTagsProvider extends DeferredItemTagsProvider {
  public GCItemTagsProvider(DataGenerator generatorIn) {
    super(generatorIn, "Gadgetry Core Item Tags Provider");
  }

  @Override
  protected void registerTags() {
  }
}
