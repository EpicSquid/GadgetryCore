package epicsquid.gadgetry.core.init;

import epicsquid.gadgetry.core.GadgetryCore;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings("unused")
public class ModItems {
	public static RegistryObject<Item> REDMETAL_INGOT = GadgetryCore.REGISTRY.registerItem(ModMaterials.REDMETAL.ingotName(), GadgetryCore.REGISTRY.item(Item::new, ModRegistries.SIG));
	public static RegistryObject<Item> REDMETAL_NUGGET = GadgetryCore.REGISTRY.registerItem(ModMaterials.REDMETAL.nuggetName(), GadgetryCore.REGISTRY.item(Item::new, ModRegistries.SIG));

	public static void load() {

	}
}
