package epicsquid.gadgetry.core.data;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.mysticallib.data.DeferredRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public class GCRecipeProvider extends DeferredRecipeProvider {
	public GCRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn, GadgetryCore.MODID);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
	}
}
