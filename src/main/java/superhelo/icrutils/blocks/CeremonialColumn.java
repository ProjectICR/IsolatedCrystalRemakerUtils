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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import superhelo.icrutils.tileentity.TileEntityCeremonialColumn;

@SuppressWarnings("deprecation")
public class CeremonialColumn extends BlockBase implements ITileEntityProvider {

    private static final AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.35D, 1.0D);

    public CeremonialColumn(String registryName) {
        super(Material.ROCK, registryName);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCeremonialColumn();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof TileEntityCeremonialColumn) {
            ItemStackHandler inv = ((TileEntityCeremonialColumn) tile).getInventory();
            Block.spawnAsEntity(worldIn, pos, inv.getStackInSlot(0));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (!worldIn.isRemote) {
            TileEntityCeremonialColumn te = (TileEntityCeremonialColumn) worldIn.getTileEntity(pos);
            worldIn.notifyBlockUpdate(pos, state, worldIn.getBlockState(pos), Constants.BlockFlags.RERENDER_MAIN_THREAD);
            if (Objects.nonNull(te)) {
                ItemStackHandler inventory = te.getInventory();
                ItemStack stack = inventory.getStackInSlot(0);
                if (stack.isEmpty()) {
                    if (!heldItem.isEmpty()) {
                        ItemStack newStack = heldItem.copy();
                        newStack.setCount(1);
                        inventory.setStackInSlot(0, newStack);
                        if (!playerIn.isCreative()) {
                            playerIn.setHeldItem(hand, StackHelper.decrease(heldItem, 1, false));
                        }
                        worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return true;
                    }

                } else if (heldItem.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(playerIn, stack.copy());
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
