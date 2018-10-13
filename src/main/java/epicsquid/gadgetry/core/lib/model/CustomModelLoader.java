package epicsquid.gadgetry.core.lib.model;

import java.util.HashMap;
import java.util.Map;

import epicsquid.gadgetry.core.lib.event.RegisterCustomModelsEvent;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.MinecraftForge;

public class CustomModelLoader implements ICustomModelLoader {
  public static Map<ResourceLocation, IModel> blockmodels = new HashMap<ResourceLocation, IModel>();
  public static Map<ResourceLocation, IModel> itemmodels = new HashMap<ResourceLocation, IModel>();

  public static FaceBakery faceBakery = new FaceBakery();

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager) {
    blockmodels.clear();
    itemmodels.clear();
    MinecraftForge.EVENT_BUS.post(new RegisterCustomModelsEvent());
  }

  @Override
  public boolean accepts(ResourceLocation modelLocation) {
    return blockmodels.containsKey(modelLocation) || itemmodels.containsKey(modelLocation);
  }

  @Override
  public IModel loadModel(ResourceLocation modelLocation) throws Exception {
    if (blockmodels.containsKey(modelLocation)) {
      return blockmodels.get(modelLocation);
    } else if (itemmodels.containsKey(modelLocation)) {
      return itemmodels.get(modelLocation);
    }
    return null;
  }

}
