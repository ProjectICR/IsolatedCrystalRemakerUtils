package superhelo.icrutils.utils;

import java.util.Objects;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PacketDispatcher {

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
