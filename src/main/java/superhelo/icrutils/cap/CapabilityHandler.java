package superhelo.icrutils.cap;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {

    @CapabilityInject(IStoreStageData.class)
    public static Capability<IStoreStageData> STORE_STAGE_DATA;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(IStoreStageData.class, new IStorage<IStoreStageData>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IStoreStageData> capability, IStoreStageData instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IStoreStageData> capability, IStoreStageData instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound) {
                    instance.deserializeNBT((NBTTagCompound) nbt);
                }
            }
        }, StoreStageData::new);
    }

}
