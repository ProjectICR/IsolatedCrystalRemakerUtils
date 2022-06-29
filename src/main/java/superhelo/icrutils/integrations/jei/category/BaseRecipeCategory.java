package superhelo.icrutils.integrations.jei.category;

import javax.annotation.Nonnull;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.text.translation.I18n;
import superhelo.icrutils.ICRUtils;

public abstract class BaseRecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {

    protected IDrawable background;

    @Nonnull
    @Override
    public String getUid() {
        return ICRUtils.MODID + "." + getRecipeUid();
    }

    @Nonnull
    @Override
    public String getTitle() {
        return I18n.translateToLocal(this.getRecipeTitle());
    }

    @Nonnull
    @Override
    public String getModName() {
        return ICRUtils.MODID;
    }

    public String addSecondsToToolTip(int seconds) {
        return I18n.translateToLocalFormatted("icrutils.jei.time_required", seconds);
    }

    public abstract String getRecipeUid();

    public abstract String getRecipeTitle();

}
