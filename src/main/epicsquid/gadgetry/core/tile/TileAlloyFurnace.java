package epicsquid.gadgetry.core.tile;

import epicsquid.gadgetry.core.RegistryManager;
import epicsquid.gadgetry.core.block.BlockTEOnOffHoriz;
import epicsquid.gadgetry.core.inventory.predicates.PredicateAlloyMaterial;
import epicsquid.gadgetry.core.lib.inventory.predicates.PredicateEmpty;
import epicsquid.gadgetry.core.lib.inventory.predicates.PredicateFurnaceFuel;
import epicsquid.gadgetry.core.lib.tile.TileModular;
import epicsquid.gadgetry.core.lib.tile.module.Module;
import epicsquid.gadgetry.core.lib.tile.module.ModuleInventory;
import epicsquid.gadgetry.core.recipe.AlloyRecipe;
import epicsquid.gadgetry.core.recipe.RecipeBase;
import epicsquid.gadgetry.core.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAlloyFurnace extends TileModular implements ITickable {

  public static final String INVENTORY = "inventory";

  public int[] ticks = { 0, 0 };
  public int[] progress = { 0, 200 };

  public TileAlloyFurnace() {
    addModule(new ModuleInventory(INVENTORY, this, 5, "alloy_furnace", new int[] { 0, 1, 2, 3 }, new int[] { 4 }) {
      @Override
      public boolean canInsertToSlot(int slot) {
        return slot != 4;
      }

      @Override
      public boolean canExtractFromSlot(int slot) {
        return true;
      }
    }.setSlotPredicate(0, new PredicateFurnaceFuel()).setSlotPredicate(1, new PredicateAlloyMaterial(0)).setSlotPredicate(2, new PredicateAlloyMaterial(1))
        .setSlotPredicate(3, new PredicateAlloyMaterial(2)).setSlotPredicate(4, new PredicateEmpty()));
  }

  @Override
  public void update() {
    IBlockState state = world.getBlockState(getPos());
    if (!world.isRemote) {
      if (ticks[0] <= 0) {
        if (progress[0] > 0) {
          progress[0] = 0;
          markDirty();
        }
        IInventory inv = (IInventory) modules.get(INVENTORY);
        int burnTime = TileEntityFurnace.getItemBurnTime(inv.getStackInSlot(0));
        AlloyRecipe r = AlloyRecipe.findRecipe(new ItemStack[] { inv.getStackInSlot(1), inv.getStackInSlot(2), inv.getStackInSlot(3) });
        if (r != null && burnTime > 0 && progress[0] < progress[1] - 1) {
          ItemStack recipeOutput = ItemStack.EMPTY;
          if (r != null) {
            recipeOutput = r.getOutput();
          }
          if (r != null && inv.getStackInSlot(1).getCount() >= r.getCount(r.inputs.get(0)) && inv.getStackInSlot(2).getCount() >= r.getCount(r.inputs.get(1))
              && inv.getStackInSlot(3).getCount() >= r.getCount(r.inputs.get(2)) && (inv.getStackInSlot(4).isEmpty()
              || RecipeBase.stackMatches(recipeOutput, inv.getStackInSlot(4))
              && inv.getStackInSlot(4).getCount() <= inv.getStackInSlot(4).getMaxStackSize() - recipeOutput.getCount())) {
            inv.decrStackSize(0, 1);
            ticks[0] = burnTime + 1;
            ticks[1] = burnTime + 1;
            world.setBlockState(getPos(), state.withProperty(BlockTEOnOffHoriz.active, true), 8);
            world.notifyBlockUpdate(getPos(), state, state.withProperty(BlockTEOnOffHoriz.active, true), 8);
            markDirty();
          }
        }
      } else if (ticks[0] >= 0) {
        ticks[0]--;
        IInventory inv = (IInventory) modules.get(INVENTORY);
        AlloyRecipe r = AlloyRecipe.findRecipe(new ItemStack[] { inv.getStackInSlot(1), inv.getStackInSlot(2), inv.getStackInSlot(3) });
        ItemStack recipeOutput = ItemStack.EMPTY;
        if (r != null) {
          recipeOutput = r.getOutput();
        }
        if (r != null && inv.getStackInSlot(1).getCount() >= r.getCount(r.inputs.get(0)) && inv.getStackInSlot(2).getCount() >= r.getCount(r.inputs.get(1))
            && inv.getStackInSlot(3).getCount() >= r.getCount(r.inputs.get(2)) && (inv.getStackInSlot(4).isEmpty()
            || RecipeBase.stackMatches(recipeOutput, inv.getStackInSlot(4))
            && inv.getStackInSlot(4).getCount() <= inv.getStackInSlot(4).getMaxStackSize() - recipeOutput.getCount())) {
          progress[0]++;
          if (progress[0] >= progress[1]) {
            progress[0] = 0;
            inv.decrStackSize(1, r.getCount(r.inputs.get(0)));
            inv.decrStackSize(2, r.getCount(r.inputs.get(1)));
            inv.decrStackSize(3, r.getCount(r.inputs.get(2)));
            if (inv.getStackInSlot(4).isEmpty()) {
              inv.setInventorySlotContents(4, recipeOutput);
            } else {
              inv.getStackInSlot(4).grow(recipeOutput.getCount());
            }
            markDirty();
          }
        } else {
          if (progress[0] > 0) {
            progress[0] = 0;
            markDirty();
          }
        }
        if (r != null && ticks[0] <= 0) {
          int burnTime = TileEntityFurnace.getItemBurnTime(inv.getStackInSlot(0));
          if (burnTime > 0) {
            inv.decrStackSize(0, 1);
            ticks[0] = Math.max(ticks[0], burnTime + 1);
            ticks[1] = Math.max(ticks[1], burnTime + 1);
            markDirty();
          }
        }
        if (ticks[0] <= 0) {
          progress[0] = 0;
          markDirty();
          world.setBlockState(getPos(), state.withProperty(BlockTEOnOffHoriz.active, false), 8);
          world.notifyBlockUpdate(getPos(), state, state.withProperty(BlockTEOnOffHoriz.active, false), 8);
        }
        markDirty();
      }
    }
    if (progress[0] > 0 && Util.rand.nextInt(14) == 0) {
      spawnParticle(state);
    }
    for (Module m : modules.values()) {
      m.onUpdate(this);
    }
  }

  public void spawnParticle(IBlockState state) {
    EnumFacing enumfacing = (EnumFacing) state.getValue(BlockTEOnOffHoriz.facing);
    double d0 = (double) pos.getX() + 0.5;
    double d1 = (double) pos.getY() + Util.rand.nextDouble() * 6.0 / 16.0;
    double d2 = (double) pos.getZ() + 0.5;
    double d3 = 0.52;
    double d4 = Util.rand.nextDouble() * 0.6D - 0.3D;

    if (Util.rand.nextDouble() < 0.1D) {
      world.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS,
          1.0F, 1.0F, false);
    }

    switch (enumfacing) {
    case WEST:
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      world.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      break;
    case EAST:
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      world.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
      break;
    case NORTH:
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
      world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
      break;
    case SOUTH:
      world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
      world.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
    }
  }

  @Override
  public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
    return newState.getBlock() != RegistryManager.alloy_furnace;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    tag.setInteger("ticks", ticks[0]);
    tag.setInteger("lastFuel", ticks[1]);
    tag.setInteger("progress", progress[0]);
    tag.setInteger("maxProgress", progress[1]);
    return super.writeToNBT(tag);
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    ticks[0] = tag.getInteger("ticks");
    ticks[1] = tag.getInteger("lastFuel");
    progress[0] = tag.getInteger("progress");
    progress[1] = tag.getInteger("maxProgress");
  }
}
