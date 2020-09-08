package epicsquid.gadgetry.core.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.lib.block.BlockSlabBase;
import epicsquid.gadgetry.core.lib.block.IBlock;
import epicsquid.gadgetry.core.lib.block.INoCullBlock;
import epicsquid.gadgetry.core.lib.block.multiblock.BlockMultiblockSlave;
import epicsquid.gadgetry.core.lib.event.RegisterContentEvent;
import epicsquid.gadgetry.core.lib.event.RegisterCustomModelsEvent;
import epicsquid.gadgetry.core.lib.event.RegisterParticleEvent;
import epicsquid.gadgetry.core.lib.model.BakedModelColorWrapper;
import epicsquid.gadgetry.core.lib.model.CustomModelLoader;
import epicsquid.gadgetry.core.lib.model.ICustomModeledObject;
import epicsquid.gadgetry.core.lib.model.IModeledObject;
import epicsquid.gadgetry.core.lib.model.block.BakedModelBlockUnlitWrapper;
import epicsquid.gadgetry.core.lib.particle.ParticleRegistry;
import epicsquid.gadgetry.core.lib.particle.particles.ParticleFlame;
import epicsquid.gadgetry.core.lib.particle.particles.ParticleGlitter;
import epicsquid.gadgetry.core.lib.particle.particles.ParticleGlow;
import epicsquid.gadgetry.core.lib.particle.particles.ParticleSmoke;
import epicsquid.gadgetry.core.lib.particle.particles.ParticleSpark;
import epicsquid.gadgetry.core.lib.tile.multiblock.TileModularSlave;
import epicsquid.gadgetry.core.lib.tile.multiblock.TileSlave;
import epicsquid.gadgetry.core.lib.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ELRegistry {
  public static ArrayList<Item> items = new ArrayList<Item>();
  public static ArrayList<Block> blocks = new ArrayList<Block>();
  static Map<Class<? extends Entity>, IRenderFactory> entityRenderMap = new HashMap<Class<? extends Entity>, IRenderFactory>();
  static Map<Class<? extends TileEntity>, TileEntitySpecialRenderer> tileEntityRenderMap = new HashMap<Class<? extends TileEntity>, TileEntitySpecialRenderer>();
  private static int entityId = 0;

  private static String activeModid = "";

  public static Block multiblock_slave_empty, multiblock_slave_modular;

  public static String getActiveModid() {
    return activeModid;
  }

  public static void initAll() {
    ModContainer container = Loader.instance().activeModContainer();
    MinecraftForge.EVENT_BUS.post(new RegisterContentEvent(items, blocks));
    setActiveMod(GadgetryCore.MODID, container);
    blocks.add(multiblock_slave_empty = new BlockMultiblockSlave(Material.ROCK, SoundType.METAL, 1.0f, "multiblock_slave_empty", TileSlave.class)
        .setModelCustom(true));
    blocks.add(multiblock_slave_modular = new BlockMultiblockSlave(Material.ROCK, SoundType.METAL, 1.0f, "multiblock_slave_modular", TileModularSlave.class)
        .setModelCustom(true));

  }

  @SideOnly(Side.CLIENT)
  public static void registerEntityRenderer(Class<? extends Entity> entity, IRenderFactory render) {
    entityRenderMap.put(entity, render);
  }

  @SideOnly(Side.CLIENT)
  public static void registerTileRenderer(Class<? extends TileEntity> entity, TileEntitySpecialRenderer render) {
    tileEntityRenderMap.put(entity, render);
  }

  public static void addSlabPair(Material material, SoundType type, float hardness, String name, IBlockState parent, Block[] refs, boolean customModels,
      CreativeTabs tab) {
    BlockSlabBase double_slab = new BlockSlabBase(material, type, hardness, name + "_double_slab", parent, true, null).setModelCustom(customModels);
    BlockSlabBase slab = new BlockSlabBase(material, type, hardness, name + "_slab", parent, false, double_slab).setModelCustom(customModels);
    double_slab.slab = slab;
    slab.setCreativeTab(tab);
    refs[0] = slab;
    refs[1] = double_slab;
    blocks.add(slab);
    blocks.add(double_slab);
  }

  public static void addSlabPair(Material material, SoundType type, float hardness, String name, IBlockState parent, Block[] refs, boolean customModels) {
    BlockSlabBase double_slab = new BlockSlabBase(material, type, hardness, name + "_double_slab", parent, true, null).setModelCustom(customModels);
    BlockSlabBase slab = new BlockSlabBase(material, type, hardness, name + "_slab", parent, false, double_slab).setModelCustom(customModels);
    double_slab.slab = slab;
    refs[0] = slab;
    refs[1] = double_slab;
    blocks.add(slab);
    blocks.add(double_slab);
  }

  public static void registerEntity(Class<? extends Entity> entity, int eggColor1, int eggColor2) {
    String[] nameParts = entity.getTypeName().split("\\.");
    String className = nameParts[nameParts.length - 1];
    EntityRegistry.registerModEntity(new ResourceLocation(activeModid + ":" + Util.lowercase(className)), entity, Util.lowercase(className), entityId++,
        GadgetryCore.INSTANCE, 64, 1, true, eggColor1, eggColor2);
  }

  public static void registerEntity(Class<? extends Entity> entity) {
    String[] nameParts = entity.getTypeName().split("\\.");
    String className = nameParts[nameParts.length - 1];
    EntityRegistry.registerModEntity(new ResourceLocation(activeModid + ":" + Util.lowercase(className)), entity, Util.lowercase(className), entityId++,
        GadgetryCore.INSTANCE, 64, 1, true);
  }

  public static void setActiveMod(String modid, ModContainer container) {
    activeModid = modid;
    Loader.instance().setActiveModContainer(container);
  }

  @SubscribeEvent
  public void registerItems(RegistryEvent.Register<Item> event) {
    for (Item i : items) {
      event.getRegistry().register(i);
    }
    for (Block b : blocks) {
      if (b instanceof IBlock) {
        Item i = ((IBlock) b).getItemBlock();
        if (i != null) {
          event.getRegistry().register(i);
        }
      }
    }
  }

  @SubscribeEvent
  public void registerBlocks(RegistryEvent.Register<Block> event) {
    for (Block b : blocks) {
      event.getRegistry().register(b);
    }
  }

  static Set<ModelResourceLocation> noCullMRLs = new HashSet<>();
  static Set<ModelResourceLocation> colorMRLs = new HashSet<>();

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerRendering(ModelRegistryEvent event) {
    for (Item i : items) {
      if (i instanceof IModeledObject) {
        ((IModeledObject) i).initModel();
      }
    }
    for (Block b : blocks) {
      if (b instanceof IModeledObject) {
        ((IModeledObject) b).initModel();
      }
    }
  }

  public static void registerEntityRenders() {
    for (Entry<Class<? extends Entity>, IRenderFactory> e : entityRenderMap.entrySet()) {
      RenderingRegistry.registerEntityRenderingHandler(e.getKey(), e.getValue());
    }
    for (Entry<Class<? extends TileEntity>, TileEntitySpecialRenderer> e : tileEntityRenderMap.entrySet()) {
      ClientRegistry.bindTileEntitySpecialRenderer(e.getKey(), e.getValue());
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRegisterCustomModels(RegisterCustomModelsEvent event) {
    for (Block b : blocks) {
      if (b instanceof ICustomModeledObject) {
        ((ICustomModeledObject) b).initCustomModel();
      }
    }
    for (Item i : items) {
      if (i instanceof ICustomModeledObject) {
        ((ICustomModeledObject) i).initCustomModel();
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    for (Entry<String, ResourceLocation> e : ParticleRegistry.particleTextures.entrySet()) {
      event.getMap().registerSprite(e.getValue());
    }
    for (Entry<ResourceLocation, IModel> e : CustomModelLoader.itemmodels.entrySet()) {
      for (ResourceLocation r : e.getValue().getTextures()) {
        event.getMap().registerSprite(r);
      }
    }
  }

  public class RegisterTintedModelsEvent extends Event {
    public void addModel(ModelResourceLocation mrl) {
      colorMRLs.add(mrl);
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onModelBake(ModelBakeEvent event) {
    MinecraftForge.EVENT_BUS.post(new RegisterTintedModelsEvent());
    noCullMRLs.clear();
    for (Block b : blocks) {
      if (b instanceof IModeledObject) {
        if (b instanceof INoCullBlock && ((INoCullBlock) b).noCull()) {
          noCullMRLs.addAll(event.getModelManager().getBlockModelShapes().getBlockStateMapper().getVariants(b).values());
        }
      }
    }
    //TextureAtlasSprite sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.STONE.getDefaultState()).getParticleTexture();
    //ModelUtil.STANDARD_SPRITE_WIDTH = sprite.getMaxU()-sprite.getMinU();
    //ModelUtil.STANDARD_SPRITE_HEIGHT = sprite.getMaxV()-sprite.getMinV();

    Function<ResourceLocation, TextureAtlasSprite> getter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    for (ResourceLocation r : CustomModelLoader.itemmodels.keySet()) {
      ModelResourceLocation mrl = new ModelResourceLocation(r.toString().replace("#inventory", ""), "inventory");
      Object object = event.getModelRegistry().getObject(mrl);
      if (object instanceof IBakedModel) {
        IModel m = CustomModelLoader.itemmodels.get(r);
        event.getModelRegistry().putObject(mrl, m.bake(m.getDefaultState(), DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter()));
      }
    }

    for (ModelResourceLocation mrl : event.getModelRegistry().getKeys()) {
      if (noCullMRLs.contains(mrl)) {
        event.getModelRegistry().putObject(mrl, new BakedModelBlockUnlitWrapper(event.getModelManager().getModel(mrl)));
      }
      if (mrl.toString().startsWith("eidolon"))
        System.out.println(mrl.toString());
      if (colorMRLs.contains(mrl)) {
        event.getModelRegistry().putObject(mrl, new BakedModelColorWrapper(event.getModelManager().getModel(mrl)));
      }
    }
  }

  public static String PARTICLE_GLOW, PARTICLE_SMOKE, PARTICLE_SPARK, PARTICLE_GLITTER, PARTICLE_FLAME;

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRegisterCustomModels(RegisterParticleEvent event) {
    PARTICLE_GLOW = ParticleRegistry.registerParticle(GadgetryCore.MODID, ParticleGlow.class, new ResourceLocation("gadgetrycore:particle/particle_glow"));
    PARTICLE_SMOKE = ParticleRegistry.registerParticle(GadgetryCore.MODID, ParticleSmoke.class, new ResourceLocation("gadgetrycore:particle/particle_smoke"));
    PARTICLE_SPARK = ParticleRegistry.registerParticle(GadgetryCore.MODID, ParticleSpark.class, new ResourceLocation("gadgetrycore:particle/particle_sparkle"));
    PARTICLE_GLITTER = ParticleRegistry
        .registerParticle(GadgetryCore.MODID, ParticleGlitter.class, new ResourceLocation("gadgetrycore:particle/particle_sparkle"));
    PARTICLE_FLAME = ParticleRegistry.registerParticle(GadgetryCore.MODID, ParticleFlame.class, new ResourceLocation("gadgetrycore:particle/particle_fire"));
  }
}
