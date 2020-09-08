package epicsquid.gadgetry.core.recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import epicsquid.gadgetry.core.RegistryManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;

public class AlloyRecipe extends RecipeBase {

  public static ArrayList<AlloyRecipe> recipes = new ArrayList<AlloyRecipe>();

  public static Set<Object> metals = new HashSet<Object>();
  public static Set<Object> additives1 = new HashSet<Object>();
  public static Set<Object> additives2 = new HashSet<Object>();

  @Nullable
  public static AlloyRecipe findRecipe(ItemStack[] inputs) {
    for (int i = 0; i < recipes.size(); i++) {
      if (recipes.get(i).matches(inputs)) {
        return recipes.get(i);
      }
    }
    return null;
  }

  public static void registerAll() {
    if (Loader.isModLoaded("jei")) {
      MinecraftForge.EVENT_BUS.register(new AlloyRecipeJEI());
    }
    recipes.add(new AlloyRecipe(new ItemStack(RegistryManager.redmetal_ingot, 1), new ItemStack(Items.GOLD_INGOT, 1), new ItemStack(Items.REDSTONE, 1),
        ItemStack.EMPTY));
    recipes.add(new AlloyRecipe(new ItemStack(RegistryManager.steel_ingot, 1), new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.COAL, 1),
        new ItemStack(Items.GUNPOWDER, 1)));
    recipes.add(new AlloyRecipe(new ItemStack(RegistryManager.silicon, 1), new ItemStack(Blocks.GLASS, 1), ItemStack.EMPTY, ItemStack.EMPTY));
    for (AlloyRecipe r : recipes) {
      metals.add(r.inputs.get(0));
      additives1.add(r.inputs.get(1));
      additives2.add(r.inputs.get(2));
    }
  }

  public static void addRecipe(AlloyRecipe r) {
    recipes.add(r);
    metals.add(r.inputs.get(0));
    additives1.add(r.inputs.get(1));
    additives2.add(r.inputs.get(2));
  }

  public AlloyRecipe(ItemStack output, Object... inputs) {
    super(output, inputs);
  }

}
