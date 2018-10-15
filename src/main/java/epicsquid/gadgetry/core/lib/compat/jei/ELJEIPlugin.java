package epicsquid.gadgetry.core.lib.compat.jei;

import epicsquid.gadgetry.core.lib.event.RegisterJEICategoriesEvent;
import epicsquid.gadgetry.core.lib.event.RegisterJEIHandlingEvent;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraftforge.common.MinecraftForge;

@JEIPlugin
public class ELJEIPlugin implements IModPlugin {
  @Override
  public void register(IModRegistry registry) {
    MinecraftForge.EVENT_BUS.post(new RegisterJEIHandlingEvent(this, registry));
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    MinecraftForge.EVENT_BUS.post(new RegisterJEICategoriesEvent(this, registry));
  }
}
