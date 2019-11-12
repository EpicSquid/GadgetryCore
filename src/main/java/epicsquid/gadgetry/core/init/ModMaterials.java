package epicsquid.gadgetry.core.init;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.mysticallib.material.MaterialType;
import net.minecraft.util.SoundEvents;

import static epicsquid.mysticallib.material.MaterialType.Type;

public class ModMaterials {
	public static final String REDMETAL_NAME = "redmetal";
	public static final String STEEL_NAME = "steel";

	public static MaterialType REDMETAL = new MaterialType(REDMETAL_NAME).itemMaterial(200, 4.0f, 2.0f, 1, 15).item(() -> ModItems.REDMETAL_INGOT).nugget(() -> ModItems.REDMETAL_NUGGET).block(() -> ModBlocks.REDMETAL_BLOCK).armorMaterial(15, new int[]{2, 5, 6, 2}, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f).setModId(GadgetryCore.MODID).putDamageSpeed(
					Type.AXE, 5.0f, -3.1f,
					Type.KNIFE, 2.5f, -1.5f);

	public static MaterialType STEEL = new MaterialType(STEEL_NAME).itemMaterial(550, 4.0f, 2.0f, 1, 7).item(() -> ModItems.STEEL_INGOT).nugget(() -> ModItems.STEEL_NUGGET).block(() -> ModBlocks.STEEL_BLOCK).armorMaterial(15, new int[]{2, 5, 6, 2}, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0f).setModId(GadgetryCore.MODID).putDamageSpeed(
					Type.AXE, 5.0f, -3.1f,
					Type.KNIFE, 2.5f, -1.5f);
}
