package epicsquid.gadgetry.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class GCTags {
	public static class Blocks extends GCTags {



		static Tag<Block> tag(String modid, String name) {
			return tag(BlockTags.Wrapper::new, modid, name);
		}

		static Tag<Block> modTag(String name) {
			return tag(GadgetryCore.MODID, name);
		}

		static Tag<Block> compatTag(String name) {
			return tag("forge", name);
		}
	}

	public static class Items extends GCTags {

		public static Tag<Item> REDMETAL_BLOCK = compatTag("storage_blocks/redmetal");
		public static Tag<Item> STEEL_BLOCK = compatTag("storage_blocks/steel");

		public static Tag<Item> REDMETAL_INGOT = compatTag("ingots/redmetal");
		public static Tag<Item> STEEL_INGOT = compatTag("ingots/steel");

		public static Tag<Item> REDMETAL_NUGGET = compatTag("nuggets/redmetal");
		public static Tag<Item> STEEL_NUGGET = compatTag("nuggets/steel");


		static Tag<Item> tag(String modid, String name) {
			return tag(ItemTags.Wrapper::new, modid, name);
		}

		static Tag<Item> modTag(String name) {
			return tag(GadgetryCore.MODID, name);
		}

		static Tag<Item> compatTag(String name) {
			return tag("forge", name);
		}
	}

	static <T extends Tag<?>> T tag(Function<ResourceLocation, T> creator, String modid, String name) {
		return creator.apply(new ResourceLocation(modid, name));
	}

	static <T extends Tag<?>> T modTag(Function<ResourceLocation, T> creator, String name) {
		return tag(creator, GadgetryCore.MODID, name);
	}

	static <T extends Tag<?>> T compatTag(Function<ResourceLocation, T> creator, String name) {
		return tag(creator, "forge", name);
	}
}
