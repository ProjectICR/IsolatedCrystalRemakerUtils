package superhelo.icrutils.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import superhelo.icrutils.tileentity.TileEntityCeremonialColumn;

@SideOnly(Side.CLIENT)
public class RenderCeremonialColumn extends TileEntitySpecialRenderer<TileEntityCeremonialColumn> {

    @Override
    public void renderTileEntityFast(TileEntityCeremonialColumn tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha, BufferBuilder bufferBuilder) {
        ItemStack stack = tile.getInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            double tick = Minecraft.getSystemTime() / 800.0D;
            float scale = (stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 1.65D, z + 0.5D);
            GlStateManager.scale(scale, scale, scale);
            GlStateManager.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
            GlStateManager.rotate((float) (((tick * 40.0D) % 360)), 0, 1, 0);
            GlStateManager.disableLighting();
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }

    }
}
