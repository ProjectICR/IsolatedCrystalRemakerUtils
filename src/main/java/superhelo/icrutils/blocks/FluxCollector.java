package superhelo.icrutils.blocks;

import com.blakebr0.cucumber.helper.StackHelper;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import superhelo.icrutils.api.IFluxCore;
import superhelo.icrutils.tileentity.TileEntityFluxCollector;

public class FluxCollector extends BlockBase implements ITileEntityProvider {

    public FluxCollector() {
        super(Material.ROCK, "flux_collector");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFluxCollector();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);

        if (tile instanceof TileEntityFluxCollector) {
            IItemHandlerModifiable inventory = ((TileEntityFluxCollector) tile).getInventory();
            Block.spawnAsEntity(worldIn, pos, inventory.getStackInSlot(0));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);

            if (Objects.nonNull(tile) && tile instanceof TileEntityFluxCollector) {
                IItemHandlerModifiable inventory = ((TileEntityFluxCollector) tile).getInventory();
                ItemStack stack = inventory.getStackInSlot(0);

                if (stack.isEmpty()) {
                    if (!heldItem.isEmpty() && heldItem.getItem() instanceof IFluxCore) {
                        ItemStack newStack = heldItem.copy();
                        newStack.setCount(1);
                        inventory.setStackInSlot(0, newStack);

                        if (!playerIn.isCreative()) {
                            playerIn.setHeldItem(hand, StackHelper.decrease(heldItem, 1, false));
                        }
                        worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }

                } else if (heldItem.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(playerIn, stack.copy());
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
        return true;
    }
}
