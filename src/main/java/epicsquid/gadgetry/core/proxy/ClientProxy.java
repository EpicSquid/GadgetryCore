package epicsquid.gadgetry.core.proxy;

import java.lang.reflect.InvocationTargetException;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.hax.Hax;
import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.event.RegisterColorHandlersEvent;
import epicsquid.gadgetry.core.lib.event.RegisterParticleEvent;
import epicsquid.gadgetry.core.lib.model.CustomModelLoader;
import epicsquid.gadgetry.core.lib.particle.ParticleRenderer;
import epicsquid.gadgetry.core.shaders.ShaderManager;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
  public static ParticleRenderer particleRenderer = new ParticleRenderer();

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    ShaderManager.init();

    try {
      Hax.init();
    } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }

    ELRegistry.registerEntityRenders();
    OBJLoader.INSTANCE.addDomain(GadgetryCore.MODID);
    ModelLoaderRegistry.registerLoader(new CustomModelLoader());
    MinecraftForge.EVENT_BUS.post(new RegisterParticleEvent());
  }

  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
    MinecraftForge.EVENT_BUS.post(new RegisterColorHandlersEvent());
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }
}
