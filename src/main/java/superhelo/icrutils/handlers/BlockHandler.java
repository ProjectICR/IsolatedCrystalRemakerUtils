package superhelo.icrutils.handlers;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import superhelo.icrutils.blocks.CeremonialColumn;
import superhelo.icrutils.blocks.FluxCollector;

public class BlockHandler {

    public static final List<Block> BLOCK_REGISTER = Lists.newArrayList();

    public static final Block CEREMONIAL_COLUMN = new CeremonialColumn();
    public static final Block FLUX_COLLECTOR = new FluxCollector();

}
