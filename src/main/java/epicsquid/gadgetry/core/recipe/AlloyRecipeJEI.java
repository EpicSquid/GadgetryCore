package epicsquid.gadgetry.core.recipe;

import java.util.List;

import javax.annotation.Nullable;

import epicsquid.gadgetry.core.GadgetryCore;
import epicsquid.gadgetry.core.RegistryManager;
import epicsquid.gadgetry.core.gui.GuiFactoryAlloyFurnace;
import epicsquid.gadgetry.core.lib.ELEvents;
import epicsquid.gadgetry.core.lib.container.SlotInventoryDefault;
import epicsquid.gadgetry.core.lib.event.RegisterJEICategoriesEvent;
import epicsquid.gadgetry.core.lib.event.RegisterJEIHandlingEvent;
import epicsquid.gadgetry.core.lib.gui.GuiModular;
import epicsquid.gadgetry.core.lib.gui.IGuiFactory;
import epicsquid.gadgetry.core.lib.util.Util;
import epicsquid.gadgetry.core.tile.TileAlloyFurnace;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlloyRecipeJEI {

  public static String RECIPE_UID = GadgetryCore.MODID + ":" + Util.getLowercaseClassName(AlloyRecipe.class);

  protected static class RecipeWrapper implements IRecipeWrapper {
    AlloyRecipe recipe;

    public RecipeWrapper(AlloyRecipe r) {
      recipe = r;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
      ingredients.setInputLists(ItemStack.class, RecipeBase.getInputList(recipe.inputs));
      ingredients.setOutput(ItemStack.class, recipe.getOutput());
    }
  }

  protected static class RecipeCategory implements IRecipeCategory {
    protected IDrawable background, icon;
    public static ResourceLocation texture = new ResourceLocation("gadgetrycore:textures/gui/container.png");
    public static int offY = -1;

    public RecipeCategory(IGuiHelper helper) {
      background = helper.createDrawable(texture, 0, 0, 180, 44, 0, 0);
    }

    @Override
    public String getUid() {
      return RECIPE_UID;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public IDrawable getIcon() {
      return icon;
    }

    @Override
    public String getTitle() {
      return I18n.format("gadgetrycore.jei.alloyrecipe.title");
    }

    @Override
    public String getModName() {
      return GadgetryCore.MODID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IDrawable getBackground() {
      return background;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
      TileAlloyFurnace t = new TileAlloyFurnace();
      IGuiFactory f = new GuiFactoryAlloyFurnace();
      Container c = f.constructContainer(Minecraft.getMinecraft().player, t);

      IGuiItemStackGroup stacks = layout.getItemStacks();
      List<List<ItemStack>> items = ingredients.getInputs(ItemStack.class);

      if (offY == -1) {
        offY = 0;
        for (int j = 0; j < items.size(); j++) {
          offY += c.inventorySlots.get(j).yPos;
        }
        offY /= items.size();
        offY -= 16;
      }

      int i;
      for (i = 0; i < items.size(); i++) {
        stacks.init(i, true, c.inventorySlots.get(i + 1).xPos - 1, c.inventorySlots.get(i + 1).yPos - 1 - offY);
        stacks.set(i, items.get(i));
      }
      stacks.init(i, false, c.inventorySlots.get(i + 1).xPos - 1, c.inventorySlots.get(i + 1).yPos - 1 - offY);
      stacks.set(i, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawExtras(Minecraft minecraft) {
      TileAlloyFurnace t = new TileAlloyFurnace() {
        @Override
        public void markDirty() {
          //
        }
      };
      ((IInventory) t.modules.get(t.INVENTORY)).setInventorySlotContents(0, new ItemStack(Items.COAL, 1));
      t.progress = new int[] { ELEvents.ticks % 100, 100 };
      t.ticks = new int[] { 800 - ELEvents.ticks % 800, 800 };
      IGuiFactory f = new GuiFactoryAlloyFurnace();
      GuiModular g = (GuiModular) f.constructGui(Minecraft.getMinecraft().player, t);
      float pticks = Minecraft.getMinecraft().getRenderPartialTicks();
      GlStateManager.translate(0, -offY, 0);
      g.elements.get(0).draw(g, pticks, 0, 0);
      g.elements.get(1).draw(g, pticks, 0, 0);
      for (int i = 0; i < 5; i++) {
        Slot s = g.inventorySlots.getSlot(i);
        if (s instanceof SlotInventoryDefault && ((SlotInventoryDefault) s).isBig()) {
          g.drawTexturedModalRect(s.xPos - 5, s.yPos - 5, 176, 32, 26, 26);
        } else {
          g.drawTexturedModalRect(s.xPos - 1, s.yPos - 1, 208, 32, 18, 18);
        }
      }

      GlStateManager.translate(0, offY, 0);
    }
  }

  @SubscribeEvent
  public void registerJEIHandler(RegisterJEIHandlingEvent event) {
    event.getRegistry().addRecipes(AlloyRecipe.recipes, RECIPE_UID);
    event.getRegistry().handleRecipes(AlloyRecipe.class, recipe -> new RecipeWrapper(recipe), RECIPE_UID);
    event.getRegistry().addRecipeCatalyst(new ItemStack(RegistryManager.alloy_furnace, 1), RECIPE_UID);
  }

  @SubscribeEvent
  public void registerJEICategory(RegisterJEICategoriesEvent event) {
    event.getRegistry().addRecipeCategories(new RecipeCategory(event.getRegistry().getJeiHelpers().getGuiHelper()));
  }
}
