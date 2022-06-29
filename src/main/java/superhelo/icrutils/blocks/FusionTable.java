package superhelo.icrutils.blocks;

import javax.annotation.Nullable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import superhelo.icrutils.tileentity.TileEntityFusionTable;

public class FusionTable extends BlockBase implements ITileEntityProvider {

    public FusionTable() {
        super(Material.ROCK, "fusion_table");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFusionTable();
    }

}
