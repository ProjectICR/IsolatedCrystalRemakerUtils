package superhelo.icrutils.proxy;

import java.util.Objects;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.model.ModelLoader;
import superhelo.icrutils.capability.CapabilityHandler;
import superhelo.icrutils.items.ItemHandler;
import superhelo.icrutils.network.PacketStageSync.Mode;
import superhelo.icrutils.tileentity.render.RenderInit;

public class ClientProxy extends CommonProxy {

    @Override
    public void updateStageData(Set<String> stages, Mode mode) {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if (player.hasCapability(CapabilityHandler.STORE_STAGE_DATA, null)) {
            mode.getConsumer().accept(player.getCapability(CapabilityHandler.STORE_STAGE_DATA, null), stages);
        }
    }

    @Override
    public void onModelRegistry() {
        RenderInit.init();
        ItemHandler.ITEM_REGISTER.forEach(item -> ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory")));
    }

}
