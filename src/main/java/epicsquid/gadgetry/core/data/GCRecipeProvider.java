package epicsquid.gadgetry.core.data;

import epicsquid.gadgetry.core.GCTags;
import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.gadgetry.core.init.ModItems;
import epicsquid.mysticallib.data.DeferredRecipeProvider;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class GCRecipeProvider extends DeferredRecipeProvider {
	public GCRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn, GadgetryCore.MODID);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		storage(ModItems.REDMETAL_INGOT, ModBlocks.REDMETAL_BLOCK, consumer);
		storage(ModItems.REDMETAL_NUGGET, ModItems.REDMETAL_INGOT, consumer);

		storage(ModItems.STEEL_INGOT, ModBlocks.STEEL_BLOCK, consumer);
		storage(ModItems.STEEL_NUGGET, ModItems.STEEL_INGOT, consumer);

		// Redmetal From Gold
		CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(Tags.Items.INGOTS_GOLD), ModItems.REDMETAL_INGOT.get(), 0, 100)
						.addCriterion("has_" + safeName(ModItems.REDMETAL_INGOT.get().getRegistryName()), this.hasItem(ModItems.REDMETAL_INGOT.get()))
						.build(consumer, safeId(ModItems.REDMETAL_INGOT.get()) + "_from_blasting");

		CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(Tags.Items.INGOTS_IRON), ModItems.STEEL_INGOT.get(), 0, 100)
						.addCriterion("has_" + safeName(ModItems.STEEL_INGOT.get().getRegistryName()), this.hasItem(ModItems.STEEL_INGOT.get()))
						.build(consumer, safeId(ModItems.STEEL_INGOT.get()) + "_from_blasting");
	}
}
