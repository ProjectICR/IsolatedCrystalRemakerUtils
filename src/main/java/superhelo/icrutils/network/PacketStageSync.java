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
import superhelo.icrutils.capability.IStoreStageData;
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
        while (buf.readableBytes() > 0) {
            stages.add(ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(mode.ordinal());
        stages.forEach(stage -> ByteBufUtils.writeUTF8String(buf, stage));
    }

    public enum Mode {
        REMOVE((data, stages) -> stages.forEach(data::removeStage)),
        ADD((data, stages) -> stages.forEach(data::addStage)),
        SET((data, stages) -> data.setStage(stages)),
        CLEAR((data, stages) -> data.clearStage()),
        SYNC(ADD.consumer);

        final BiConsumer<IStoreStageData, Set<String>> consumer;

        Mode(BiConsumer<IStoreStageData, Set<String>> consumer) {
            this.consumer = consumer;
        }

        public BiConsumer<IStoreStageData, Set<String>> getConsumer() {
            return this.consumer;
        }

    }

    public static class Handler implements IMessageHandler<PacketStageSync, IMessage> {

        @Override
        public IMessage onMessage(PacketStageSync message, MessageContext ctx) {
            if (message.mode != Mode.SYNC && ctx.side.isClient()) {
                ICRUtils.proxy.updateStageData(message.stages, message.mode);
            } else if (!StageUtils.getStagesFromPlayer(ctx.getServerHandler().player).isEmpty() && ctx.side.isServer()) {
                EntityPlayerMP player = ctx.getServerHandler().player;
                PacketHandler.INSTANCE.sendTo(PacketStageSync.load(StageUtils.getStagesFromPlayer(player), Mode.ADD), player);
            }
            return null;
        }

    }

}
