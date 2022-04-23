package superhelo.icrutils.capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class StoreStageDataProvider implements ICapabilitySerializable<NBTTagCompound> {

    private final IStoreStageData data = new StoreStageData();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityHandler.STORE_STAGE_DATA.equals(capability);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return CapabilityHandler.STORE_STAGE_DATA.equals(capability) ? CapabilityHandler.STORE_STAGE_DATA.cast(data) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        data.deserializeNBT(nbt);
    }

}
