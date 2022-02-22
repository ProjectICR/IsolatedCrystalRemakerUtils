package superhelo.icrutils.handlers;

import com.google.common.collect.Maps;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import java.util.Map;
import morph.avaritia.init.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import superhelo.icrutils.recipe.StarLightRecipe;

public class RecipeHandler {

    public static final Map<IItemStack, StarLightRecipe> STAR_LIGHT_RECIPE_RECIPE = Maps.newHashMap();

    public static void init() {
        new StarLightRecipe(new ItemStack(BlocksAS.blockAltar), CraftTweakerMC.getIIngredient(Item.getItemFromBlock(ModBlocks.compressedCraftingTable)), 5);
    }

}
