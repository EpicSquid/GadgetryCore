package epicsquid.gadgetry.core.lib.item;

import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.model.CustomModelItem;
import epicsquid.gadgetry.core.lib.model.CustomModelLoader;
import epicsquid.gadgetry.core.lib.model.ICustomModeledObject;
import epicsquid.gadgetry.core.lib.model.IModeledObject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ItemBase extends Item implements IModeledObject, ICustomModeledObject {

  public ItemBase(String name) {
    super();
    setUnlocalizedName(name);
    setRegistryName(ELRegistry.getActiveModid(), name);
  }

  protected boolean hasCustomModel = false;

  public ItemBase setModelCustom(boolean custom) {
    this.hasCustomModel = custom;
    return this;
  }

  @Override
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }

  @Override
  public void initCustomModel() {
    if (this.hasCustomModel) {
      CustomModelLoader.itemmodels.put(getRegistryName(),
          new CustomModelItem(false, new ResourceLocation(getRegistryName().getResourceDomain() + ":items/" + getRegistryName().getResourcePath())));
    }
  }
}
