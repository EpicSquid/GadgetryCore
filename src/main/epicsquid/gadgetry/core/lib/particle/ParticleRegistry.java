package epicsquid.gadgetry.core.lib.particle;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.lib.util.Util;
import epicsquid.gadgetry.core.proxy.ClientProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleRegistry {
  public static Map<String, Constructor<? extends ParticleBase>> particles = new HashMap<String, Constructor<? extends ParticleBase>>();
  public static Map<String, ResourceLocation> particleTextures = new HashMap<String, ResourceLocation>();

  public static String registerParticle(String modid, Class<? extends ParticleBase> particleClass, ResourceLocation texture) {
    String name = Util.getLowercaseClassName(particleClass);
    if (GadgetryCore.proxy instanceof ClientProxy) {
      try {
        if (particles.containsKey(name) || particleTextures.containsKey(name)) {
          System.out.println("WARNING: PARTICLE ALREADY REGISTERED WITH NAME \"" + name + "\"!");
        } else {
          particles.put(name,
              particleClass.getConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class, double[].class));
          particleTextures.put(name, texture);
        }
      } catch (NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
      }
    }
    return name;
  }
}
