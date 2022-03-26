package superhelo.icrutils.event;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.handlers.RecipeHandler;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.utils.Utils;

@EventBusSubscriber(modid = ICRUtils.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity joinEntity = event.getEntity();

        if (!event.getWorld().isRemote && joinEntity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) joinEntity;
            IItemStack stack = Utils.getIItemStack(entityItem);
            NBTTagCompound nbt = entityItem.getEntityData();

            Predicate<List<IIngredient>> isAdditionalInput = (input) ->
                !input.isEmpty() && input.stream().anyMatch(iIngredient -> iIngredient.amount(1).matches(stack));

            for (StarLightRecipe recipe : RecipeHandler.STAR_LIGHT_RECIPE_MAP.values()) {
                if (recipe.getInput().amount(1).matches(stack)) {
                    nbt.setBoolean("starLightInput", true);
                    break;
                }

                if (isAdditionalInput.test(recipe.getAdditionalInput())) {
                    nbt.setBoolean("starLightAdditionalInput", true);
                    break;
                }

            }

        }
    }

}
