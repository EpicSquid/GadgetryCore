package epicsquid.gadgetry.core.lib.fx;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.proxy.ClientProxy;
import net.minecraft.nbt.NBTTagCompound;

public class FXRegistry {
  public static Map<Integer, Function<NBTTagCompound, Void>> effects = new HashMap<>();

  static int id = 0;

  public static int registerEffect(Function<NBTTagCompound, Void> func) {
    if (GadgetryCore.proxy instanceof ClientProxy) {
      effects.put(id, func);
    }
    return id++;
  }
}
