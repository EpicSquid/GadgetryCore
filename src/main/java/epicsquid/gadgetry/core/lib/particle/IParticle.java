package epicsquid.gadgetry.core.lib.particle;

public interface IParticle {
  public boolean alive();

  public boolean isAdditive();

  public boolean renderThroughBlocks();
}
