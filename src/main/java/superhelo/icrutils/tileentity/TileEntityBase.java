package superhelo.icrutils.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import superhelo.icrutils.ICRUtils;

public class TileEntityBase extends TileEntity {

    public static void init() {
        register(TileEntityFluxCollector.class, "flux_collector");
        register(TileEntityCeremonialColumn.class, "ceremonial_column");
    }

    private static void register(Class<? extends TileEntity> clazz, String registryName) {
        try {
            ResourceLocation rl = new ResourceLocation(ICRUtils.MODID, registryName);
            GameRegistry.registerTileEntity(clazz, rl);
        } catch (Exception e) {
            ICRUtils.LOGGER.error("Registering " + registryName + " failed", e);
        }
    }

}
