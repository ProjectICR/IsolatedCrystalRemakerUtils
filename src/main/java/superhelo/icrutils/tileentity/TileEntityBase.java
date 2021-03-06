package superhelo.icrutils.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import superhelo.icrutils.ICRUtils;

public class TileEntityBase extends TileEntity {

    public static void init() {
        register(TileEntityFusionTable.class, "fusion_table");
        register(TileEntityCeremonialColumn.class, "ceremonial_column");
    }

    private static void register(Class<? extends TileEntity> clazz, String registryName) {
        try {
            ResourceLocation rl = new ResourceLocation(ICRUtils.MODID, registryName);
            GameRegistry.registerTileEntity(clazz, rl);
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
