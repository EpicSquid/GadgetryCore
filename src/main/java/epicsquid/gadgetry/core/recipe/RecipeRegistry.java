package epicsquid.gadgetry.core.recipe;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.RegistryManager;
import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.event.RegisterModRecipesEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class RecipeRegistry {

  public static void registerOreDict() {
    OreDictionary.registerOre("ingotRedmetal", RegistryManager.redmetal_ingot);
    OreDictionary.registerOre("ingotSteel", RegistryManager.steel_ingot);
    OreDictionary.registerOre("blockRedmetal", RegistryManager.redmetal_block);
    OreDictionary.registerOre("blockSteel", RegistryManager.steel_block);
    OreDictionary.registerOre("nuggetRedmetal", RegistryManager.redmetal_nugget);
    OreDictionary.registerOre("nuggetSteel", RegistryManager.steel_nugget);
    OreDictionary.registerOre("matSilicon", RegistryManager.silicon);
    OreDictionary.registerOre("itemSilicon", RegistryManager.silicon);
    OreDictionary.registerOre("silicon", RegistryManager.silicon);
  }

  public static ResourceLocation getRL(String s) {
    return new ResourceLocation(GadgetryCore.MODID + ":" + s);
  }

  public static void registerShaped(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapedOreRecipe(getRL(name), result, ingredients).setRegistryName(getRL(name)));
  }

  public static void registerShapedMirrored(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapedOreRecipe(getRL(name), result, ingredients).setMirrored(true).setRegistryName(getRL(name)));
  }

  public static void registerShapeless(IForgeRegistry<IRecipe> registry, String name, ItemStack result, Object... ingredients) {
    registry.register(new ShapelessOreRecipe(getRL(name), result, ingredients).setRegistryName(getRL(name)));
  }

  @SubscribeEvent
  public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
    MinecraftForge.EVENT_BUS.post(new RegisterModRecipesEvent(event.getRegistry()));
  }

  @SubscribeEvent
  public void onRegisterCrafting(RegisterModRecipesEvent event) {
    registerOreDict();
    ELRegistry.setActiveMod(GadgetryCore.MODID, GadgetryCore.CONTAINER);
    AlloyRecipe.registerAll();
    registerShaped(event.getRegistry(), "alloy_furnace", new ItemStack(RegistryManager.alloy_furnace, 1), "III", "BFB", "BFB", 'I', "ingotIron", 'B',
        Items.BRICK, 'F', Blocks.FURNACE);
    registerShaped(event.getRegistry(), "redmetal_block", new ItemStack(RegistryManager.redmetal_block, 1), "III", "III", "III", 'I', "ingotRedmetal");
    registerShaped(event.getRegistry(), "steel_block", new ItemStack(RegistryManager.steel_block, 1), "III", "III", "III", 'I', "ingotSteel");
    registerShaped(event.getRegistry(), "redmetal_ingot", new ItemStack(RegistryManager.redmetal_ingot, 1), "III", "III", "III", 'I', "nuggetRedmetal");
    registerShaped(event.getRegistry(), "steel_ingot", new ItemStack(RegistryManager.steel_ingot, 1), "III", "III", "III", 'I', "nuggetSteel");
    registerShaped(event.getRegistry(), "steel_ingot", new ItemStack(RegistryManager.steel_ingot, 1), "III", "III", "III", 'I', "nuggetSteel");
    registerShapeless(event.getRegistry(), "steel_nugget", new ItemStack(RegistryManager.steel_nugget, 9), "ingotSteel");
    registerShapeless(event.getRegistry(), "redmetal_nugget", new ItemStack(RegistryManager.redmetal_nugget, 9), "ingotRedmetal");
    registerShapeless(event.getRegistry(), "steel_unblock", new ItemStack(RegistryManager.steel_ingot, 9), "blockSteel");
    registerShapeless(event.getRegistry(), "redmetal_unblock", new ItemStack(RegistryManager.redmetal_ingot, 9), "blockRedmetal");
    registerShapedMirrored(event.getRegistry(), "wrench", new ItemStack(RegistryManager.wrench, 1), "  I", " I ", "L  ", 'I', "ingotIron", 'L', "dyeBlue");
  }
}
