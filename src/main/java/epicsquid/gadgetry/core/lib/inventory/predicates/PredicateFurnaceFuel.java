package epicsquid.gadgetry.core.lib.inventory.predicates;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class PredicateFurnaceFuel implements Predicate<ItemStack> {

  @Override
  public boolean test(ItemStack arg0) {
    return TileEntityFurnace.isItemFuel(arg0) || arg0.isEmpty();
  }

}
