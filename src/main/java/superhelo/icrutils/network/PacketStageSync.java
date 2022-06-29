package superhelo.icrutils.network;

import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import superhelo.icrutils.ICRUtils;
import superhelo.icrutils.capability.StoreStageData;
import superhelo.icrutils.stage.StageUtils;

public class PacketStageSync implements IMessage {

    private Mode mode;
    private Set<String> stages;

    public static PacketStageSync load(Set<String> stages, Mode mode) {
        PacketStageSync pkt = new PacketStageSync();
        pkt.mode = mode;
        pkt.stages = stages;

        return pkt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.mode = Mode.values()[buf.readInt()];
        this.stages = Sets.newHashSet();

        for (int size = buf.readInt(); size > 0; size--) {
            stages.add(ByteBufUtils.readUTF8String(buf));
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(mode.ordinal());
        buf.writeInt(stages.size());
        stages.forEach(stage -> ByteBufUtils.writeUTF8String(buf, stage));
    }

    public enum Mode {
        REMOVE((data, stages) -> stages.forEach(data::removeStage)),
        ADD((data, stages) -> stages.forEach(data::addStage)),
        SET((data, stages) -> data.setStage(stages)),
        CLEAR((data, stages) -> data.clearStage()),
        SYNC(ADD.consumer);

        final BiConsumer<StoreStageData, Set<String>> consumer;

        Mode(BiConsumer<StoreStageData, Set<String>> consumer) {
            this.consumer = consumer;
        }

        public BiConsumer<StoreStageData, Set<String>> getConsumer() {
            return this.consumer;
        }

    }

    public static class Handler implements IMessageHandler<PacketStageSync, IMessage> {

        @Override
        public IMessage onMessage(PacketStageSync message, MessageContext ctx) {
            if (message.mode != Mode.SYNC && ctx.side.isClient()) {
                ICRUtils.proxy.updateStageData(message.stages, message.mode);
            } else if (ctx.side.isServer()) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                Set<String> stages = StageUtils.getStagesFromPlayer(player);
                if (!stages.isEmpty()) {
                    PacketHandler.INSTANCE.sendTo(PacketStageSync.load(stages, Mode.ADD), player);
                }
            }
            return null;
        }

    }

}
