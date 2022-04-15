package superhelo.icrutils.handlers;

import com.google.common.collect.Maps;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.util.Pair;

public class StageHandler {

    private static final Map<Item, Pair<String, ItemStack>> STAGE_HANDLER = Maps.newHashMap();

    public static void init() {

    }

    public static void registerStageItemStack(String stage, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        ItemStack newStack = stack.copy();
        newStack.setCount(1);
        STAGE_HANDLER.put(stack.getItem(), new Pair<>(stage, newStack));
    }

    public static boolean hasStage(ItemStack stack) {
        return STAGE_HANDLER.containsKey(stack.getItem());
    }

    @Nullable
    public static String getStage(ItemStack stack) {
        if (STAGE_HANDLER.containsKey(stack.getItem())) {
            Pair<String, ItemStack> pair = STAGE_HANDLER.get(stack.getItem());

            if (CraftTweakerMC.getIItemStack(pair.getValue()).matches(CraftTweakerMC.getIItemStack(stack))) {
                return pair.getKey();
            }
        }

        return null;
    }

}
