package superhelo.icrutils.event.listener;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.cap.CapabilityHandler;
import superhelo.icrutils.cap.IStoreStageData;
import superhelo.icrutils.cap.StoreStageDataProvider;
import superhelo.icrutils.network.PacketHandler;
import superhelo.icrutils.network.PacketStageSync;
import superhelo.icrutils.network.PacketStageSync.Mode;
import superhelo.icrutils.recipe.StarLightRecipe;
import superhelo.icrutils.stage.StageUtils;

@EventBusSubscriber(modid = ICRUtils.MODID)
public class EventListener {

    @SubscribeEvent
    public static void addCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(ICRUtils.MODID, "stage_data"), new StoreStageDataProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        IStoreStageData original = event.getOriginal().getCapability(CapabilityHandler.STORE_STAGE_DATA, null);
        IStoreStageData now = event.getEntityPlayer().getCapability(CapabilityHandler.STORE_STAGE_DATA, null);

        if (Objects.nonNull(original) && Objects.nonNull(now)) {
            now.setStage(original.getStages());
        }

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        if (player instanceof EntityPlayerMP && player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null) && !StageUtils.getStagesFromPlayer(player).isEmpty()) {
            PacketHandler.INSTANCE.sendTo(new PacketStageSync(StageUtils.getStagesFromPlayer(player), Mode.SET), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if (!event.getWorld().isRemote && entity instanceof EntityItem) {
            EntityItem entityItem = (EntityItem) entity;
            NBTTagCompound nbt = entityItem.getEntityData();
            IItemStack stack = CraftTweakerMC.getIItemStack(entityItem.getItem());

            Predicate<List<IIngredient>> isAdditionalInput = (input) ->
                !input.isEmpty() && input.stream().anyMatch(iIngredient -> iIngredient.amount(1).matches(stack));

            for (StarLightRecipe recipe : StarLightRecipe.STAR_LIGHT_RECIPE_MAP.values()) {
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
