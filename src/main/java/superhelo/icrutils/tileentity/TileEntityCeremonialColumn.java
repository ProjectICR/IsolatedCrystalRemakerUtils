package superhelo.icrutils.tileentity;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.data.NBTConverter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import superhelo.icrutils.integrations.crafttweaker.ICeremonialColumnTile;
import superhelo.icrutils.network.PacketHandler;

public class TileEntityCeremonialColumn extends TileEntityBase implements ICeremonialColumnTile {

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityCeremonialColumn.this.markDirty();
        }
    };

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public IData getData() {
        return NBTConverter.from(this.getTileData(), false);
    }

    @Override
    public void updateData(IData data) {
        if (data instanceof DataMap) {
            this.getTileData().merge(CraftTweakerMC.getNBTCompound(data));
            this.markDirty();
        } else {
            CraftTweakerAPI.logError("data argument must be DataMap", new IllegalArgumentException());
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        PacketHandler.dispatchTEToNearbyPlayers(this);
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
    public IItemStack getStackInInventory() {
        return CraftTweakerMC.getIItemStack(inventory.getStackInSlot(0));
    }

    @Override
    public void setStackInInventory(IItemStack stack) {
        inventory.setStackInSlot(0, CraftTweakerMC.getItemStack(stack));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", this.inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

}
