package superhelo.icrutils.integrations.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.items.ItemHandler;
import superhelo.icrutils.recipe.FusionTableRecipe;
import superhelo.icrutils.utils.nbt.TagCompoundProxy;

public class FusionTableRecipeWrapper implements BaseRecipeWrapper<FusionTableRecipe> {

    private final FusionTableRecipe recipe;

    public FusionTableRecipeWrapper(FusionTableRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ItemStack output = new ItemStack(ItemHandler.ITEM_CREATURE);
        output.setTagCompound(new TagCompoundProxy().setString("name", recipe.getOutput().getName()).getNbt());

        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

    @Override
    public FusionTableRecipe getRecipe() {
        return recipe;
    }

}
