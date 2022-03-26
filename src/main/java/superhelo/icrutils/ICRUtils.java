package superhelo.icrutils;

import javax.annotation.Nonnull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import superhelo.icrutils.handlers.BlockHandler;

@Mod(modid = ICRUtils.MODID, name = ICRUtils.NAME, version = ICRUtils.VERSION, dependencies = ICRUtils.DESPENDENCIES)
public class ICRUtils {

    public static final String MODID = "icrutils";
    public static final String NAME = "ICRUtils";
    public static final String VERSION = "1.0";
    public static final String DESPENDENCIES = "required-after:crafttweaker;required-after:mixinbooter;required-after:cucumber;";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static ItemStack BUCKET = ItemStack.EMPTY;
    public static final CreativeTabs ICR_CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(BlockHandler.CEREMONIAL_COLUMN);
        }
    };

    public ICRUtils() {
        FluidRegistry.enableUniversalBucket();
    }

}
