package superhelo.icrutils.proxy;

import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import superhelo.icrutils.cap.CapabilityHandler;
import superhelo.icrutils.cap.IStoreStageData;
import superhelo.icrutils.network.PacketStageSync.Mode;

public class ClientProxy extends CommonProxy {

    private static Consumer<IStoreStageData> getConsumer(Set<String> stages, Mode mode) {
        switch (mode) {
            case ADD:
                return data -> data.addStage(stages.toArray(new String[0])[0]);
            case REMOVE:
                return data -> data.removeStage(stages.toArray(new String[0])[0]);
            case SET:
                return data -> data.setStage(stages);
            case CLEAR:
                return IStoreStageData::clearStage;
            default:
                return data -> {
                };
        }
    }

    @Override
    public void updateStageData(Set<String> stages, Mode mode) {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            getConsumer(stages, mode).accept(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null));
        }
    }

}
