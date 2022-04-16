package superhelo.icrutils.stage;

import com.google.common.collect.Maps;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.util.Pair;

public class StageHandler {

    private static final Map<Item, Pair<String, IItemStack>> STAGE_HANDLER = Maps.newHashMap();

    public static void registerStageItemStack(String stage, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        STAGE_HANDLER.put(stack.getItem(), new Pair<>(stage, CraftTweakerMC.getIItemStackWildcardSize(stack.copy())));
    }

    @Nullable
    public static String getStage(ItemStack stack) {
        if (STAGE_HANDLER.containsKey(stack.getItem())) {
            Pair<String, IItemStack> pair = STAGE_HANDLER.get(stack.getItem());

            if (pair.getValue().matches(CraftTweakerMC.getIItemStack(stack))) {
                return pair.getKey();
            }
        }

        return null;
    }

}
