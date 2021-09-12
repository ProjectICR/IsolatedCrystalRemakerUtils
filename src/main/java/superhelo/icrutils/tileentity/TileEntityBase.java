package superhelo.icrutils.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import superhelo.icrutils.ICRUtils;

public class TileEntityBase extends TileEntity {
    private final String registryName;

    public TileEntityBase(String registryName) {
        this.registryName = registryName;
    }

    public static void init() {
        register(TileEntityCeremonialColumn.class);
    }

    private static void register(Class<? extends TileEntityBase> clazz) {
        try {
            ResourceLocation rl = new ResourceLocation(ICRUtils.MODID, ((TileEntityBase) clazz.newInstance()).registryName);
            GameRegistry.registerTileEntity(clazz, rl);
        } catch (Exception e) {
            ICRUtils.logger.fatal("Registering a TileEntity failed!", e);
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
