package epicsquid.gadgetry.core.proxy;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.event.RegisterFXEvent;
import epicsquid.gadgetry.core.lib.event.RegisterWorldGenEvent;
import epicsquid.gadgetry.core.lib.gui.GuiHandler;
import epicsquid.gadgetry.core.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent event) {
    ELRegistry.initAll();
    PacketHandler.registerMessages();
    MinecraftForge.EVENT_BUS.post(new RegisterFXEvent());
  }

  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.post(new RegisterWorldGenEvent());
  }

  public void postInit(FMLPostInitializationEvent event) {
    NetworkRegistry.INSTANCE.registerGuiHandler(GadgetryCore.INSTANCE, new GuiHandler());
  }
}