package superhelo.icrutils.tileentity;


import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import superhelo.icrutils.api.CeremonialColumnTileInGame;

public class TileEntityCeremonialColumn extends TileEntityBase implements CeremonialColumnTileInGame {
    private final ItemStackHandler inv = new ItemStackHandler(1);

    public TileEntityCeremonialColumn() {
        super("ceremonial_column");
    }

    public ItemStackHandler getInv() {
        return inv;
    }

    @Override
    public IWorld getIWorld() {
        return CraftTweakerMC.getIWorld(this.getWorld());
    }

    @Override
    public IBlockPos getIBlockPos() {
        return CraftTweakerMC.getIBlockPos(this.getPos());
    }

    @Override
    public void setStackInInv(IItemStack stack) {
        inv.setStackInSlot(0, CraftTweakerMC.getItemStack(stack));
    }

    @Override
    public IItemStack getStackInInv() {
        return CraftTweakerMC.getIItemStack(inv.getStackInSlot(0));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.inv.deserializeNBT(compound.getCompoundTag("Inv"));
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Inv", this.inv.serializeNBT());
        return super.writeToNBT(compound);
    }
}
