package epicsquid.gadgetry.core.lib.tile.multiblock;

import epicsquid.gadgetry.core.lib.tile.ITile;
import net.minecraft.util.math.BlockPos;

public interface ISlave extends ITile {
  public void setMaster(BlockPos tile);

  public BlockPos getMaster();
}
