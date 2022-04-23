package superhelo.icrutils.proxy;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import superhelo.icrutils.capability.CapabilityHandler;
import superhelo.icrutils.network.PacketStageSync.Mode;

public class ClientProxy extends CommonProxy {

    @Override
    public void updateStageData(Set<String> stages, Mode mode) {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            mode.getConsumer().accept(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null), stages);
        }
    }

}
