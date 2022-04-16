package superhelo.icrutils.network;

import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;
import java.util.Set;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import superhelo.icrutils.ICRUtils;

public class PacketStageSync implements IMessage {

    private Set<String> stages;
    private int mode;

    public PacketStageSync() {
    }

    public PacketStageSync(Set<String> stages, Mode mode) {
        this.stages = stages;
        this.mode = mode.mode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.mode = buf.readInt();
        while (buf.readableBytes() > 0) {
            this.stages = Sets.newHashSet();
            stages.add(ByteBufUtils.readUTF8String(buf));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(mode);
        stages.forEach(stage -> ByteBufUtils.writeUTF8String(buf, stage));
    }

    public enum Mode {
        ADD(0),
        SET(1),
        CLEAR(2),
        REMOVE(3);

        public final int mode;

        Mode(int mode) {
            this.mode = mode;
        }

        public static Mode getInstance(int mode) {
            switch (mode) {
                case 0:
                    return ADD;
                case 1:
                    return SET;
                case 2:
                    return CLEAR;
                case 3:
                    return REMOVE;
                default:
                    return null;
            }
        }

    }

    public static class Handler implements IMessageHandler<PacketStageSync, IMessage> {

        @Override
        public IMessage onMessage(PacketStageSync message, MessageContext ctx) {
            ICRUtils.proxy.updateStageData(message.stages, Mode.getInstance(message.mode));
            return null;
        }

    }

}
