package epicsquid.gadgetry.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import epicsquid.gadgetry.core.item.ItemWrench;
import epicsquid.gadgetry.core.lib.tile.TileModular;
import epicsquid.gadgetry.core.lib.tile.module.FaceConfig.FaceIO;
import epicsquid.gadgetry.core.network.MessageTEUpdate;
import epicsquid.gadgetry.core.network.PacketHandler;
import epicsquid.gadgetry.core.util.Primitives;
import epicsquid.gadgetry.core.util.RenderUtil;
import epicsquid.gadgetry.core.util.Vec4d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventManager {
  public static boolean acceptUpdates = true;

  public static Map<BlockPos, TileEntity> toUpdate = new HashMap<BlockPos, TileEntity>();
  public static Map<BlockPos, TileEntity> overflow = new HashMap<BlockPos, TileEntity>();

  public static void markForUpdate(BlockPos pos, TileEntity tile) {
    if (!tile.getWorld().isRemote && acceptUpdates) {
      if (!toUpdate.containsKey(pos)) {
        toUpdate.put(pos, tile);
      } else {
        toUpdate.replace(pos, tile);
      }
    } else if (!tile.getWorld().isRemote) {
      if (!overflow.containsKey(pos)) {
        overflow.put(pos, tile);
      } else {
        overflow.replace(pos, tile);
      }
    }
  }

  @SubscribeEvent
  public void onServerTick(TickEvent.WorldTickEvent event) {
    if (!event.world.isRemote && event.phase == TickEvent.Phase.END) {
      NBTTagList list = new NBTTagList();
      acceptUpdates = false;
      TileEntity[] updateArray = toUpdate.values().toArray(new TileEntity[0]);
      acceptUpdates = true;
      for (Entry<BlockPos, TileEntity> e : overflow.entrySet()) {
        toUpdate.put(e.getKey(), e.getValue());
      }
      overflow.clear();
      for (int i = 0; i < updateArray.length; i++) {
        TileEntity t = updateArray[i];
        list.appendTag(t.getUpdateTag());
      }
      if (!list.hasNoTags()) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("data", list);
        PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(tag));
      }
      toUpdate.clear();
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onRenderWorld(RenderWorldLastEvent event) {
    Tessellator tess = Tessellator.getInstance();
    BufferBuilder b = tess.getBuffer();
    EntityPlayer p = Minecraft.getMinecraft().player;
    World world = Minecraft.getMinecraft().world;
    if (p != null && p.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemWrench || p.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemWrench) {
      RayTraceResult r = p.rayTrace(5.0, Minecraft.getMinecraft().getRenderPartialTicks());
      if (r != null && r.typeOfHit != null && r.typeOfHit == Type.BLOCK) {
        TileEntity t = world.getTileEntity(r.getBlockPos());
        if (t instanceof TileModular) {
          GlStateManager.disableCull();
          Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("gadgetrycore:textures/icon/hovericons.png"));
          b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
          FaceIO f = ((TileModular) t).config.ioConfig.get(r.sideHit);
          if (f != f.NEUTRAL && ((TileModular) t).canModifyIO) {
            double u = 0;
            double v = 0;
            if (f == f.IN) {
              //
            }
            if (f == f.OUT) {
              u = 0.25;
            }
            if (f == f.INOUT) {
              u = 0.5;
            }
            EnumFacing s = r.sideHit;
            Vec4d uv = new Vec4d(u, v, 0.25, 0.25);
            b.setTranslation(-TileEntityRendererDispatcher.staticPlayerX + s.getDirectionVec().getX() * 0.0625,
                -TileEntityRendererDispatcher.staticPlayerY + s.getDirectionVec().getY() * 0.0625,
                -TileEntityRendererDispatcher.staticPlayerZ + s.getDirectionVec().getZ() * 0.0625);
            Primitives.addCubeToBuffer(b, (double) r.getBlockPos().getX(), (double) r.getBlockPos().getY(), (double) r.getBlockPos().getZ(),
                (double) r.getBlockPos().getX() + 1.0, (double) r.getBlockPos().getY() + 1.0, (double) r.getBlockPos().getZ() + 1.0,
                new Vec4d[] { uv, uv, uv, uv, uv, uv }, 1f, 1f, 1f, 1f, s == EnumFacing.NORTH, s == EnumFacing.SOUTH, s == EnumFacing.UP, s == EnumFacing.DOWN,
                s == EnumFacing.EAST, s == EnumFacing.WEST, RenderUtil.maxLightX, RenderUtil.maxLightY);
          }
          tess.draw();
          b.setTranslation(0, 0, 0);
          GlStateManager.enableCull();
        }
      }
    }
  }

  @SubscribeEvent
  public void onActivateBlock(PlayerInteractEvent.RightClickBlock event) {
    if (event.getItemStack().getItem() instanceof ItemWrench && event.getEntityPlayer().isSneaking()) {
      event.setCanceled(true);
      TileEntity t = event.getWorld().getTileEntity(event.getPos());
      if (t instanceof TileModular) {
        TileModular m = ((TileModular) t);
        if (m.canModifyIO) {
          FaceIO f = m.config.ioConfig.get(event.getFace());
          int i = f.ordinal() + 1;
          if (i == FaceIO.INOUT.ordinal()) {
            i++;
          }
          if (i >= f.values().length) {
            i = 0;
          }
          m.config.setIO(event.getFace(), f.values()[i]);
          m.markDirty();
        }
      }
    }
  }
}
