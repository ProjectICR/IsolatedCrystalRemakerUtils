package superhelo.icrutils.handlers;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import superhelo.icrutils.blocks.CeremonialColumn;

public class BlockHandler {

    public static final List<Block> BLOCK_REGISTER = Lists.newArrayList();

    public static final Block CEREMONIAL_COLUMN = new CeremonialColumn();

}
