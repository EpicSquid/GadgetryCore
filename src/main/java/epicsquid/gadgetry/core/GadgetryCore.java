package epicsquid.gadgetry.core;

import epicsquid.gadgetry.core.init.ModBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import epicsquid.gadgetry.core.init.ModItems;
import epicsquid.gadgetry.core.setup.ModSetup;
import epicsquid.mysticallib.registry.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("gadgetrycore")
public class GadgetryCore {
	public static final Logger LOG = LogManager.getLogger();
	public static final String MODID = "gadgetrycore";

	public static final ItemGroup ITEM_GROUP = new ItemGroup("gadgetrycore") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.REDMETAL_INGOT.get());
		}
	};

	public static final ModRegistry REGISTRY = new ModRegistry(MODID);

	public static ModSetup setup = new ModSetup();

	public GadgetryCore() {

		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.load();
		ModBlocks.load();

		modBus.addListener(setup::init);
		modBus.addListener(setup::gatherData);

		REGISTRY.registerEventBus(modBus);
  }
}
