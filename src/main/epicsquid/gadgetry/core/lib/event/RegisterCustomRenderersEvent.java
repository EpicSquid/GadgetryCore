package epicsquid.gadgetry.core.lib.event;

import epicsquid.gadgetry.core.lib.ELRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RegisterCustomRenderersEvent {

  public RegisterCustomRenderersEvent() {

  }

  public void addTileRender(Class<? extends TileEntity> c, TileEntitySpecialRenderer r) {
    ELRegistry.registerTileRenderer(c, r);
  }

  public void addEntityender(Class<? extends Entity> c, IRenderFactory r) {
    ELRegistry.registerEntityRenderer(c, r);
  }
}
