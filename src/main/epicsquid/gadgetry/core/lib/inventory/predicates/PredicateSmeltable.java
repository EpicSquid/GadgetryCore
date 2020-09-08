package epicsquid.gadgetry.core.lib.inventory.predicates;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class PredicateSmeltable implements Predicate<ItemStack> {

  @Override
  public boolean test(ItemStack arg0) {
    return FurnaceRecipes.instance().getSmeltingResult(arg0) != null && !FurnaceRecipes.instance().getSmeltingResult(arg0).isEmpty() || arg0.isEmpty();
  }

}
