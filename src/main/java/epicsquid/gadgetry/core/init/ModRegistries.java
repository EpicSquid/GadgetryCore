package epicsquid.gadgetry.core.init;

import java.util.function.Supplier;

import epicsquid.gadgetry.core.GadgetryCore;
import net.minecraft.item.Item;

public class ModRegistries {
  public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(GadgetryCore.ITEM_GROUP);
}
