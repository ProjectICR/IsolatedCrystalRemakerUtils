package superhelo.icrutils.tileentity.render;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import superhelo.icrutils.tileentity.TileEntityCeremonialColumn;

public class RenderInit {

    public static void init() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCeremonialColumn.class, new RenderCeremonialColumn());
    }
}
