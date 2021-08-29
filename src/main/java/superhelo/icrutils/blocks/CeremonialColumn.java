package superhelo.icrutils.blocks;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import superhelo.icrutils.tileentity.TileEntityCeremonialColumn;

public class CeremonialColumn extends BlockBase implements ITileEntityProvider {

    public CeremonialColumn(String registryName) {
        super(Material.ROCK, registryName);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCeremonialColumn();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if(!worldIn.isRemote) {
            TileEntityCeremonialColumn te = (TileEntityCeremonialColumn) worldIn.getTileEntity(pos);
            if(Objects.nonNull(te)) {
                ItemStack invStack = te.getInv().getStackInSlot(0);
                if(!heldItem.isEmpty()) {
                    if(invStack.isEmpty()) {
                        ItemStack newStack = heldItem.copy();
                        newStack.setCount(1);
                        te.getInv().setStackInSlot(0, newStack);
                        if(!playerIn.isCreative()) {
                            heldItem.shrink(1);
                        }
                        return true;
                    }

                } else if(!invStack.isEmpty()) {
                    playerIn.setHeldItem(hand, invStack.copy());
                    te.getInv().setStackInSlot(0, ItemStack.EMPTY);
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
