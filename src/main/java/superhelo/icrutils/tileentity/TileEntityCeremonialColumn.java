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
import net.minecraft.nbt.NBTTagCompound;
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
    public IData getData() {
        return NBTConverter.from(this.getTileData(), false);
    }

    @Override
    public void updateData(IData data) {
        if (data instanceof DataMap) {
            this.getTileData().merge((NBTTagCompound) NBTConverter.from(data));
            this.markDirty();
        } else {
            CraftTweakerAPI.logError("data argument must be DataMap", new IllegalArgumentException());
        }
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
