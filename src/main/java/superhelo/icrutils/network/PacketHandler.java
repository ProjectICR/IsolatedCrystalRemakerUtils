package superhelo.icrutils.network;

import java.util.Objects;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import superhelo.icrutils.ICRUtils;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ICRUtils.MODID);

    public static void preInit() {
        INSTANCE.registerMessage(PacketStageSync.Handler.class, PacketStageSync.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(PacketStageSync.Handler.class, PacketStageSync.class, 1, Side.SERVER);
    }

    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        World world = tile.getWorld();

        if (!world.isRemote) {
            SPacketUpdateTileEntity packet = tile.getUpdatePacket();
            PlayerChunkMapEntry trackingEntry = ((WorldServer) world).getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);

            if (Objects.nonNull(trackingEntry) && Objects.nonNull(packet)) {
                for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                    player.connection.sendPacket(packet);
                }
            }
        }
    }

}
