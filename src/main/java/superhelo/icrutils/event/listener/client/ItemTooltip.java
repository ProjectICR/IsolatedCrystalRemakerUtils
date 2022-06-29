package superhelo.icrutils.event.listener.client;

import java.util.Objects;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import superhelo.icrutils.stage.StageHandler;

@EventBusSubscriber(value = Side.CLIENT)
public class ItemTooltip {

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String stage = StageHandler.getStage(stack);

        if (Objects.nonNull(stage)) {
            event.getToolTip().add(TextFormatting.RED + I18n.format("icrutils.tooltip.stage", stage));
        }

    }

}
