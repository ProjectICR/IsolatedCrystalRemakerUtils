package superhelo.icrutils.event.listener;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
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

@EventBusSubscriber(modid = ICRUtils.MODID)
public class EventListener {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (!event.getWorld().isRemote && entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) entity;
            NBTTagCompound nbt = entityItem.getEntityData();
            IItemStack stack = CraftTweakerMC.getIItemStack(entityItem.getItem());

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
