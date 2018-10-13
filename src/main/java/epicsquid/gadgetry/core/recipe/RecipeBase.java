package epicsquid.gadgetry.core.recipe;

import java.util.ArrayList;
import java.util.List;

import epicsquid.gadgetry.core.lib.util.OreStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeBase {
  public ArrayList<Object> inputs = new ArrayList<Object>();
  private ItemStack output = null;

  public static List<ItemStack> getIngredientStacks(Object o) {
    List<ItemStack> l = new ArrayList<>();
    if (o instanceof ItemStack) {
      l.add((ItemStack) o);
    } else if (o instanceof String) {
      l.addAll(OreDictionary.getOres((String) o));
    } else if (o instanceof OreStack) {
      l.addAll(OreDictionary.getOres(((OreStack) o).oreId));
      for (ItemStack i : l) {
        i.setCount(((OreStack) o).amount);
      }
    }
    return l;
  }

  public static List<List<ItemStack>> getInputList(List<Object> inputs) {
    List<List<ItemStack>> inputList = new ArrayList<>();
    for (Object o : inputs) {
      inputList.add(getIngredientStacks(o));
    }
    return inputList;
  }

  public RecipeBase(ItemStack output, Object... inputs) {
    this.output = output;
    for (Object o : inputs) {
      if (o instanceof Item) {
        this.inputs.add(new ItemStack((Item) o, 1));
      } else if (o instanceof Block) {
        this.inputs.add(new ItemStack((Block) o, 1));
      } else {
        this.inputs.add(o);
      }
    }
  }

  public ItemStack getOutput() {
    return output.copy();
  }

  public static int getCount(Object recipeInput) {
    if (recipeInput instanceof ItemStack) {
      return ((ItemStack) recipeInput).getCount();
    } else if (recipeInput instanceof OreStack) {
      return ((OreStack) recipeInput).amount;
    }
    return 1;
  }

  public static boolean stackMatches(ItemStack stack, Object recipeInput) {
    if (recipeInput instanceof ItemStack) {
      if (!ItemStack.areItemsEqual(stack, (ItemStack) recipeInput)) {
        return false;
      }
    } else if (recipeInput instanceof OreStack) {
      int id = OreDictionary.getOreID(((OreStack) recipeInput).oreId);
      int[] ids = OreDictionary.getOreIDs(stack);
      boolean hasMatch = false;
      for (int pid : ids) {
        if (pid == id) {
          hasMatch = true;
        }
      }
      if (!hasMatch) {
        return hasMatch;
      }
    } else if (recipeInput instanceof String) {
      int id = OreDictionary.getOreID((String) recipeInput);
      int[] ids = OreDictionary.getOreIDs(stack);
      boolean hasMatch = false;
      for (int pid : ids) {
        if (pid == id) {
          hasMatch = true;
        }
      }
      if (!hasMatch) {
        return hasMatch;
      }
    }
    return true;
  }

  public boolean matches(Object[] inputs) {
    for (int i = 0; i < inputs.length; i++) {
      Object o = this.inputs.get(i);
      if (!(inputs[i] instanceof ItemStack)) {
        return false;
      } else if (!stackMatches((ItemStack) inputs[i], o)) {
        return false;
      }
    }
    return true;
  }
}
