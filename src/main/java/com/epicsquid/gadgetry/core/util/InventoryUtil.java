package epicsquid.gadgetry.core.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtil {
  public static int attemptInsert(ItemStack stack, IItemHandler inventory, boolean simulate) {
    int count = stack.getCount();
    ItemStack toInsert = stack.copy();
    for (int i = 0; i < inventory.getSlots() && !toInsert.isEmpty(); i++) {
      ItemStack s = inventory.insertItem(i, toInsert.copy(), simulate);
      toInsert.setCount(s.getCount());
    }
    return count - toInsert.getCount();
  }

  public static int attemptInsert(ItemStack stack, IItemHandler inventory, boolean simulate, int startSlot, int endSlot) {
    int count = stack.getCount();
    ItemStack toInsert = stack.copy();
    for (int i = startSlot; i < endSlot && !toInsert.isEmpty(); i++) {
      ItemStack s = inventory.insertItem(i, toInsert.copy(), simulate);
      toInsert.setCount(s.getCount());
    }
    return count - toInsert.getCount();
  }
}
