package superhelo.icrutils.integrations.crafttweaker.expend;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import java.util.Arrays;
import java.util.stream.Collectors;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import superhelo.icrutils.stage.StageUtils;

@ZenRegister
@ZenExpansion("crafttweaker.player.IPlayer")
public class IPlayerExpansion {

    @ZenMethod
    public static void addStageToPlayer(IPlayer player, String stage) {
        StageUtils.addStageToPlayer(CraftTweakerMC.getPlayer(player), stage);
    }

    @ZenMethod
    public static void removeStageFromPlayer(IPlayer player, String stage) {
        StageUtils.removeStageFromPlayer(CraftTweakerMC.getPlayer(player), stage);
    }

    @ZenMethod
    public static void setStagesFromPlayer(IPlayer player, String[] stages) {
        StageUtils.setStagesFromPlayer(CraftTweakerMC.getPlayer(player), Arrays.stream(stages).collect(Collectors.toSet()));
    }

    @ZenMethod
    public static void clearStagesFromPlayer(IPlayer player) {
        StageUtils.clearStagesFromPlayer(CraftTweakerMC.getPlayer(player));
    }

    @ZenMethod
    public static String[] getStagesFromPlayer(IPlayer player) {
        return StageUtils.getStagesFromPlayer(CraftTweakerMC.getPlayer(player)).toArray(new String[0]);
    }

    @ZenMethod
    public static boolean hasStage(IPlayer player, String stage) {
        return StageUtils.hasStage(CraftTweakerMC.getPlayer(player), stage);
    }

    @ZenMethod
    public static boolean hasStage(IPlayer player, IItemStack stack) {
        return StageUtils.hasStage(CraftTweakerMC.getPlayer(player), CraftTweakerMC.getItemStack(stack));
    }

}
