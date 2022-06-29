package superhelo.icrutils.integrations.crafttweaker.expend;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.event.StarLightTickEvent;
import superhelo.icrutils.integrations.crafttweaker.event.CTStarLightTickEvent;

@ZenRegister
@ZenExpansion("crafttweaker.events.IEventManager")
public class ExpandIEventManager {

    private static final EventList<CTStarLightTickEvent> CT_STAR_LIGHT_RECIPE_EVENT_LIST = new EventList<>();

    @ZenMethod
    public static IEventHandle onStarLightTick(IEventManager manager, IEventHandler<CTStarLightTickEvent> event) {
        return CT_STAR_LIGHT_RECIPE_EVENT_LIST.add(event);
    }

    @EventBusSubscriber
    public static class Handler {

        @SubscribeEvent
        public static void onStarLightRecipe(StarLightTickEvent event) {
            if (CT_STAR_LIGHT_RECIPE_EVENT_LIST.hasHandlers()) {
                CT_STAR_LIGHT_RECIPE_EVENT_LIST.publish(new CTStarLightTickEvent(event));
            }
        }

    }

}
