package epicsquid.gadgetry.core;

import epicsquid.gadgetry.core.lib.ELEvents;
import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.fx.ELFXHandler;
import epicsquid.gadgetry.core.lib.tile.CableManager;
import epicsquid.gadgetry.core.proxy.CommonProxy;
import epicsquid.gadgetry.core.recipe.RecipeRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = GadgetryCore.MODID, version = GadgetryCore.VERSION, name = GadgetryCore.NAME)
public class GadgetryCore {
  public static final String MODID = "gadgetrycore";
  public static final String VERSION = "0.2.0";
  public static final String NAME = "Gadgetry: Core";

  public static ModContainer CONTAINER;

  @SidedProxy(clientSide = "epicsquid.gadgetry.core.proxy.ClientProxy", serverSide = "epicsquid.gadgetry.core.proxy.CommonProxy") public static CommonProxy proxy;

  @Instance public static GadgetryCore INSTANCE;

  public static CreativeTabs tab = new CreativeTabs("gadgetrycore") {
    @Override
    public String getTabLabel() {
      return "gadgetrycore";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(RegistryManager.redmetal_ingot, 1);
    }
  };

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    CONTAINER = Loader.instance().activeModContainer();
    MinecraftForge.EVENT_BUS.register(new ELRegistry());
    MinecraftForge.EVENT_BUS.register(new ELEvents());
    MinecraftForge.EVENT_BUS.register(new CableManager());
    MinecraftForge.EVENT_BUS.register(new ELFXHandler());
    MinecraftForge.EVENT_BUS.register(new RecipeRegistry());

    MinecraftForge.EVENT_BUS.register(new EventManager());
    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
