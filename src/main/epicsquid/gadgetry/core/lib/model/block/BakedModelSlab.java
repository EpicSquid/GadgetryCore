package epicsquid.gadgetry.core.lib.model.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import epicsquid.gadgetry.core.lib.model.CustomModelBase;
import epicsquid.gadgetry.core.lib.model.DefaultTransformations;
import epicsquid.gadgetry.core.lib.model.ModelUtil;
import epicsquid.gadgetry.core.lib.model.parts.Cube;
import epicsquid.gadgetry.core.lib.struct.Vec4f;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class BakedModelSlab extends BakedModelBlock {
  Cube cube_down, cube_up;

  public static Vec4f FULL_FACE_UV = new Vec4f(0, 0, 16, 16);
  public static Vec4f BOTTOM_SIDE_UV = new Vec4f(0, 8, 16, 8);
  public static Vec4f TOP_SIDE_UV = new Vec4f(0, 0, 16, 8);
  public static Vec4f[] bottomUV = new Vec4f[] { BOTTOM_SIDE_UV, BOTTOM_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, BOTTOM_SIDE_UV, BOTTOM_SIDE_UV };
  public static Vec4f[] topUV = new Vec4f[] { TOP_SIDE_UV, TOP_SIDE_UV, FULL_FACE_UV, FULL_FACE_UV, TOP_SIDE_UV, TOP_SIDE_UV };

  public BakedModelSlab(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, CustomModelBase model) {
    super(state, format, bakedTextureGetter, model);
    TextureAtlasSprite[] texes = new TextureAtlasSprite[] { texwest, texeast, texdown, texup, texnorth, texsouth };
    cube_down = ModelUtil.makeCube(format, 0, 0, 0, 1, 0.5, 1, null, texes, 0).setNoCull(EnumFacing.UP);
    cube_up = ModelUtil.makeCube(format, 0, 0.5, 0, 1, 0.5, 1, null, texes, 0).setNoCull(EnumFacing.DOWN);
  }

  @Override
  public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
    List<BakedQuad> quads = new ArrayList<BakedQuad>();
    getFaceQuads(quads, side, state);
    return quads;
  }

  public void getFaceQuads(List<BakedQuad> quads, EnumFacing side, IBlockState state) {
    if (state == null) {
      cube_down.addToList(quads, side);
    } else {
      EnumBlockHalf half = state.getValue(BlockSlab.HALF);
      if (half == EnumBlockHalf.BOTTOM) {
        cube_down.addToList(quads, side);
      } else if (half == EnumBlockHalf.TOP) {
        cube_up.addToList(quads, side);
      }
    }
  }

  @Override
  public boolean isAmbientOcclusion() {
    return true;
  }

  @Override
  public boolean isGui3d() {
    return true;
  }

  @Override
  public boolean isBuiltInRenderer() {
    return false;
  }

  @Override
  public TextureAtlasSprite getParticleTexture() {
    return particle;
  }

  @Override
  public ItemOverrideList getOverrides() {
    return new ItemOverrideList(Arrays.asList());
  }

  @Override
  public Pair<? extends IBakedModel, javax.vecmath.Matrix4f> handlePerspective(ItemCameraTransforms.TransformType type) {
    Matrix4f matrix = null;
    if (DefaultTransformations.blockTransforms.containsKey(type)) {
      matrix = DefaultTransformations.blockTransforms.get(type).getMatrix();
      return Pair.of(this, matrix);
    }
    return net.minecraftforge.client.ForgeHooksClient.handlePerspective(this, type);
  }

}
