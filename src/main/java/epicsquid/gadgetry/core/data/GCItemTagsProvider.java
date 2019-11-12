package epicsquid.gadgetry.core.data;

import epicsquid.gadgetry.core.GCTags;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.gadgetry.core.init.ModItems;
import epicsquid.mysticallib.data.DeferredItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;

public class GCItemTagsProvider extends DeferredItemTagsProvider {
	public GCItemTagsProvider(DataGenerator generatorIn) {
		super(generatorIn, "Gadgetry Core Item Tags Provider");
	}

	@Override
	protected void registerTags() {
		addItemsToTag(GCTags.Items.REDMETAL_BLOCK, ModBlocks.REDMETAL_BLOCK);
		addItemsToTag(GCTags.Items.REDMETAL_INGOT, ModItems.REDMETAL_INGOT);
		addItemsToTag(GCTags.Items.REDMETAL_NUGGET, ModItems.REDMETAL_NUGGET);

		addItemsToTag(GCTags.Items.STEEL_BLOCK, ModBlocks.STEEL_BLOCK);
		addItemsToTag(GCTags.Items.STEEL_INGOT, ModItems.STEEL_INGOT);
		addItemsToTag(GCTags.Items.STEEL_NUGGET, ModItems.STEEL_NUGGET);

		appendToTag(Tags.Items.STORAGE_BLOCKS, GCTags.Items.REDMETAL_BLOCK, GCTags.Items.STEEL_BLOCK);
		appendToTag(Tags.Items.INGOTS, GCTags.Items.REDMETAL_INGOT, GCTags.Items.STEEL_INGOT);
		appendToTag(Tags.Items.NUGGETS, GCTags.Items.REDMETAL_NUGGET, GCTags.Items.STEEL_NUGGET);
	}
}
