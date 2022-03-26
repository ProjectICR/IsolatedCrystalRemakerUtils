package superhelo.icrutils.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.utils.Utils;

public class TileEntityBase extends TileEntity {

    public static void init() {
        register("ceremonial_column");
    }

    private static void register(String registryName) {
        try {
            ResourceLocation rl = new ResourceLocation(ICRUtils.MODID, registryName);
            GameRegistry.registerTileEntity((Class<? extends TileEntity>) Class.forName("superhelo.icrutils.tileentity.TileEntity" + Utils.toUpperCamelCase(registryName)), rl);
        } catch (Exception e) {
            ICRUtils.LOGGER.error("Registering " + registryName + " failed", e);
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), -1, this.writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

}
