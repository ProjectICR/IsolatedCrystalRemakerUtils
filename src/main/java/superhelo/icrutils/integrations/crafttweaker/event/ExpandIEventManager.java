package superhelo.icrutils.integrations.crafttweaker.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.event.StarLightRecipeTickEvent;

@ZenRegister
@ZenExpansion("crafttweaker.events.IEventManager")
public class ExpandIEventManager {

    private static final EventList<CTStarLightRecipeTickEvent> CT_STAR_LIGHT_RECIPE_EVENT_LIST = new EventList<>();

    @ZenMethod
    public static IEventHandle onStarLightRecipeTick(IEventManager manager, IEventHandler<CTStarLightRecipeTickEvent> event) {
        return CT_STAR_LIGHT_RECIPE_EVENT_LIST.add(event);
    }

    @EventBusSubscriber(modid = ICRUtils.MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onStarLightRecipe(StarLightRecipeTickEvent event) {
            if (CT_STAR_LIGHT_RECIPE_EVENT_LIST.hasHandlers()) {
                CT_STAR_LIGHT_RECIPE_EVENT_LIST.publish(new CTStarLightRecipeTickEvent(event));
            }
        }

    }

}
