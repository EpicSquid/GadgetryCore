package epicsquid.gadgetry.core.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.init.ModBlocks;
import epicsquid.mysticallib.data.DeferredBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.ValidationResults;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class GCLootTableProvider extends LootTableProvider {
	public GCLootTableProvider(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	public String getName() {
		return "Gadgetry Core Loot Table Provider";
	}

	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
		return ImmutableList.of(Pair.of(Blocks::new, LootParameterSets.BLOCK));
	}

	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationResults validationresults) {
	}

	public static class Blocks extends DeferredBlockLootTableProvider {
		@Override
		protected void addTables() {
			self(ModBlocks.REDMETAL_BLOCK);
			self(ModBlocks.REDMETAL_PLATE_BLOCK);
			self(ModBlocks.STEEL_BLOCK);
			self(ModBlocks.STEEL_PLATE_BLOCK);
			self(ModBlocks.IRON_PLATE_BLOCK);
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return GadgetryCore.REGISTRY.getBlocks().stream().map(Supplier::get).collect(Collectors.toList());
		}
	}
}
