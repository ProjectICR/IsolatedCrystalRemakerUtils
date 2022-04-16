package superhelo.icrutils.proxy;

import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import superhelo.icrutils.cap.CapabilityHandler;
import superhelo.icrutils.cap.IStoreStageData;
import superhelo.icrutils.network.PacketStageSync.Mode;
import superhelo.icrutils.utils.Utils;

public class ClientProxy extends CommonProxy {

    private static Consumer<IStoreStageData> getConsumer(Set<String> stages, Mode mode) {
        switch (mode) {
            case ADD:
                return data -> data.addStage(stages.toArray(stages.toArray(new String[0]))[0]);
            case REMOVE:
                return data -> data.removeStage(stages.toArray(stages.toArray(new String[0]))[0]);
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
        Utils.getCapability(Minecraft.getMinecraft().player, CapabilityHandler.STORE_STAGE_DATA_CAPABILITY, null)
            .ifPresent(getConsumer(stages, mode));
    }

}
