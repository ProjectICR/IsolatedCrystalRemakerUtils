package superhelo.icrutils.integrations.jei.category;

import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.mutable.MutableInt;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.integrations.jei.wrapper.FusionTableRecipeWrapper;

public class FusionTableRecipeCategory extends BaseRecipeCategory<FusionTableRecipeWrapper> {

    public FusionTableRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(176, 100);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getRecipeUid() {
        return "fusion_table_recipe";
    }

    @Override
    public String getRecipeTitle() {
        return "icrutils.fusion_table_recipe";
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.getTextureManager().bindTexture(new ResourceLocation(ICRUtils.MODID, "textures/gui/arrow.png"));
        Gui.drawModalRectWithCustomSizedTexture(60, 40, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FusionTableRecipeWrapper recipeWrapper, IIngredients ingredients) {
        MutableInt index = new MutableInt(1);
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, false, 80, 40);
        itemStacks.init(1, true, 40, 40);
        itemStacks.init(2, true, 80, 80);
        itemStacks.init(3, true, 80, 0);
        itemStacks.init(4, true, 120, 40);

        itemStacks.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
        ingredients.getInputs(VanillaTypes.ITEM).forEach(list -> itemStacks.set(index.getAndIncrement(), list));

        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (!input) {
                tooltip.add(this.addSecondsToToolTip(recipeWrapper.getRecipe().getTick() / 20));
            }
        });
    }

}
