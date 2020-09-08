package epicsquid.gadgetry.core.lib.event;

import epicsquid.gadgetry.core.lib.particle.ParticleBase;
import epicsquid.gadgetry.core.lib.particle.ParticleRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterParticleEvent extends Event {
  public RegisterParticleEvent() {
    super();
  }

  public void registerParticle(String modid, Class<? extends ParticleBase> particleClass, ResourceLocation texture) {
    ParticleRegistry.registerParticle(modid, particleClass, texture);
  }
}
