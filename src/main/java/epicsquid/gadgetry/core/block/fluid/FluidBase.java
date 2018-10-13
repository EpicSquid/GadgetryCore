package epicsquid.gadgetry.core.block.fluid;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidBase extends Fluid {
  public FluidBase(String name, ResourceLocation still, ResourceLocation flowing, Block block) {
    super(name, still, flowing);
    setBlock(block);
    setUnlocalizedName(name);
  }

  @Override
  public int getColor() {
    return Color.WHITE.getRGB();
  }
}
