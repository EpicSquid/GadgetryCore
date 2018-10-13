package epicsquid.gadgetry.core.inventory.predicates;

import java.util.Set;
import java.util.function.Predicate;

import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import epicsquid.gadgetry.core.recipe.RecipeBase;
import net.minecraft.item.ItemStack;

public class PredicateAlloyMaterial implements Predicate<ItemStack> {
  Set<Object> valid;

  public PredicateAlloyMaterial(int index) {
    if (index == 0) {
      valid = AlloyRecipe.metals;
    }
    if (index == 1) {
      valid = AlloyRecipe.additives1;
    }
    if (index == 2) {
      valid = AlloyRecipe.additives2;
    }
  }

  @Override
  public boolean test(ItemStack arg0) {
    for (Object o : valid) {
      if (RecipeBase.stackMatches(arg0, o)) {
        return true;
      }
    }
    return false;
  }

}
