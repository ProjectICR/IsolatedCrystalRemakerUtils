package superhelo.icrutils.integrations.jei.wrapper;

import mezz.jei.api.recipe.IRecipeWrapper;

public interface BaseRecipeWrapper<T> extends IRecipeWrapper {

    T getRecipe();

}
