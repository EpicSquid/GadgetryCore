package epicsquid.gadgetry.core.lib.event;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.registries.IForgeRegistry;

public class RegisterModRecipesEvent extends Event {
  IForgeRegistry<IRecipe> registry = null;

  public RegisterModRecipesEvent(IForgeRegistry<IRecipe> registry) {
    this.registry = registry;
  }

  public IForgeRegistry<IRecipe> getRegistry() {
    return registry;
  }
}
