package superhelo.icrutils.handlers;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.item.Item;
import superhelo.icrutils.items.FluxCollectorCore;

public class ItemHandler {

    public static final List<Item> ITEM_REGISTER = Lists.newArrayList();

    public static final Item FLUX_CORE = new FluxCollectorCore();

}
