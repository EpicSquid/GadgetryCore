package epicsquid.gadgetry.core.lib.tile;

import net.minecraft.tileentity.TileEntity;

public interface IDelayedTileRenderer {
  public void renderLater(TileEntity tile, double x, double y, double z, float partialTicks);
}
