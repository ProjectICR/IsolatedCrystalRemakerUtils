package superhelo.icrutils.capability;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHandler {

    @CapabilityInject(StoreStageData.class)
    public static Capability<StoreStageData> STORE_STAGE_DATA;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(StoreStageData.class, new IStorage<StoreStageData>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<StoreStageData> capability, StoreStageData instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<StoreStageData> capability, StoreStageData instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound) {
                    instance.deserializeNBT((NBTTagCompound) nbt);
                }
            }
        }, StoreStageData::new);
    }

}
