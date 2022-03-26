package superhelo.icrutils.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BaseEvent extends Event {

    /**
     * @return return true if event is cancelable and canceled
     */
    public boolean post() {
        return MinecraftForge.EVENT_BUS.post(this);
    }

}