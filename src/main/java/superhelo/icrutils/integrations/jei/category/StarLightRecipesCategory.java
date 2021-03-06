package superhelo.icrutils.integrations.jei.category;

import java.util.List;
import java.util.function.IntFunction;
import javax.annotation.Nonnull;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.mutable.MutableInt;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.integrations.jei.wrapper.StarLightRecipesWrapper;

public class StarLightRecipesCategory extends BaseRecipeCategory<StarLightRecipesWrapper> {

    public StarLightRecipesCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(172, 64);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getRecipeUid() {
        return "star_light_recipe";
    }

    @Override
    public String getRecipeTitle() {
        return "icrutils.starlight_fluid_transformation";
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.getTextureManager().bindTexture(new ResourceLocation(ICRUtils.MODID, "textures/gui/arrow.png"));
        Gui.drawModalRectWithCustomSizedTexture(64, 0, 0, 0, 16, 16, 16, 16);
        Gui.drawModalRectWithCustomSizedTexture(97, 0, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, StarLightRecipesWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();

        MutableInt index = new MutableInt();
        MutableInt theFirstOutputIndex = new MutableInt();

        setRecipe(itemStacks, index, ingredients.getInputs(VanillaTypes.ITEM), true, x -> 48 - x);
        theFirstOutputIndex.setValue(index.intValue());

        setRecipe(itemStacks, index, ingredients.getOutputs(VanillaTypes.ITEM), false, x -> 112 + x);

        fluidStacks.init(index.intValue(), true, 80, 0);
        fluidStacks.set(index.intValue(), ingredients.getInputs(VanillaTypes.FLUID).get(0));

        int seconds = recipeWrapper.getRecipe().getSeconds();
        itemStacks.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
            if (slotIndex == 0) {
                tooltip.add(I18n.translateToLocal("icrutils.jei.main_input"));
                tooltip.add(this.addSecondsToToolTip(seconds));
            } else if (slotIndex == theFirstOutputIndex.intValue()) {
                tooltip.add(I18n.translateToLocal("icrutils.jei.main_output"));
                tooltip.add(this.addSecondsToToolTip(seconds));
            }
        });

    }

    private void setRecipe(IGuiItemStackGroup group, MutableInt index, List<List<ItemStack>> stack, boolean input, IntFunction<Integer> consumer) {
        MutableInt x = new MutableInt();
        MutableInt y = new MutableInt();

        stack.forEach(list -> {
            int xPosition = consumer.apply(x.intValue() * 16);
            int yPosition = MathHelper.floor(y.doubleValue() / 4) * 16;
            group.init(index.intValue(), input, xPosition, yPosition);
            group.set(index.intValue(), list);
            index.increment();
            x.increment();
            y.increment();

            if (xPosition == 0 || xPosition == 160) {
                x.setValue(0);
            }
        });
    }

}
