package epicsquid.gadgetry.core;

import java.util.ArrayList;

import epicsquid.gadgetry.core.block.BlockTEOnOffHoriz;
import epicsquid.gadgetry.core.gui.GuiFactoryAlloyFurnace;
import epicsquid.gadgetry.core.item.ItemWrench;
import epicsquid.gadgetry.core.lib.ELRegistry;
import epicsquid.gadgetry.core.lib.block.BlockBase;
import epicsquid.gadgetry.core.lib.event.RegisterContentEvent;
import epicsquid.gadgetry.core.lib.event.RegisterGuiFactoriesEvent;
import epicsquid.gadgetry.core.lib.gui.GuiHandler;
import epicsquid.gadgetry.core.lib.item.ItemBase;
import epicsquid.gadgetry.core.tile.TileAlloyFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryManager {
  public static ArrayList<Item> items = new ArrayList<Item>();
  public static ArrayList<Block> blocks = new ArrayList<Block>();

  public static Item wrench, redmetal_ingot, redmetal_nugget, steel_ingot, steel_nugget, silicon;

  public static Block redmetal_block, steel_block, alloy_furnace;

  @SubscribeEvent
  public void registerContent(RegisterContentEvent event) {
    ELRegistry.setActiveMod(GadgetryCore.MODID, GadgetryCore.CONTAINER);
    event.addItem(redmetal_ingot = new ItemBase("redmetal_ingot").setCreativeTab(GadgetryCore.tab));
    event.addItem(redmetal_nugget = new ItemBase("redmetal_nugget").setCreativeTab(GadgetryCore.tab));
    event.addItem(steel_ingot = new ItemBase("steel_ingot").setCreativeTab(GadgetryCore.tab));
    event.addItem(steel_nugget = new ItemBase("steel_nugget").setCreativeTab(GadgetryCore.tab));
    event.addItem(wrench = new ItemWrench("wrench").setCreativeTab(GadgetryCore.tab));
    event.addItem(silicon = new ItemBase("silicon").setCreativeTab(GadgetryCore.tab));

    event.addBlock(
        redmetal_block = new BlockBase(Material.ROCK, SoundType.METAL, 2.4f, "redmetal_block").setHarvestReqs("pickaxe", 0).setCreativeTab(GadgetryCore.tab));
    event.addBlock(
        steel_block = new BlockBase(Material.ROCK, SoundType.METAL, 2.4f, "steel_block").setHarvestReqs("pickaxe", 0).setCreativeTab(GadgetryCore.tab));
    event.addBlock(
        alloy_furnace = new BlockTEOnOffHoriz(Material.ROCK, SoundType.METAL, 2.0f, "alloy_furnace", TileAlloyFurnace.class).setHarvestReqs("pickaxe", 0)
            .setCreativeTab(GadgetryCore.tab));

    for (Block b : blocks) {
      b.setCreativeTab(GadgetryCore.tab);
    }
    for (Item i : items) {
      i.setCreativeTab(GadgetryCore.tab);
    }
  }

  @SubscribeEvent
  public void onRegisterGuiFactories(RegisterGuiFactoriesEvent event) {
    GuiHandler.registerGui(new GuiFactoryAlloyFurnace());
  }
}
