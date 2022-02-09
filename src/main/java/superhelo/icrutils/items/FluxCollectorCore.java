package superhelo.icrutils.items;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import superhelo.icrutils.api.IFluxCore;
import superhelo.icrutils.tileentity.TileEntityFluxCollector;

public class FluxCollectorCore extends ItemBase implements IFluxCore {

    public FluxCollectorCore() {
        super("flux_collector_core");
    }

    @Override
    public int absorptionRate(World world, BlockPos pos) {
        String level = getLevel(world, pos);

        if (Objects.nonNull(level)) {
            return CoreEnum.valueOf(level).getAbsorptionRate();
        }

        return 0;
    }

    @Override
    public int absorptionQuantity(World world, BlockPos pos) {
        String level = getLevel(world, pos);

        if (Objects.nonNull(level)) {
            return CoreEnum.valueOf(level).getAbsorptionQuantity();
        }

        return 0;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            Arrays.stream(CoreEnum.values()).forEach(level -> {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setString("level", level.toString());

                ItemStack stack = new ItemStack(this);
                stack.setTagCompound(nbt);
                items.add(stack);
            });
        }
    }

    @Override
    public int getDamage(ItemStack stack) {
        if(super.getDamage(stack) != 0) {
            super.setDamage(stack, 0);
        }
        return 0;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (stack.hasTagCompound() && nbt.hasKey("level")) {
            String level = nbt.getString("level").toLowerCase();
            tooltip.add(I18n.translateToLocal("tooltip." + level + ".info"));
        }
    }

    @Nullable
    private String getLevel(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);

        if (Objects.nonNull(tile) && tile instanceof TileEntityFluxCollector) {
            ItemStack stack = ((TileEntityFluxCollector) tile).getInventory().getStackInSlot(0);
            NBTTagCompound nbt = stack.getTagCompound();

            if (Objects.nonNull(nbt) && nbt.hasKey("level")) {
                return nbt.getString("level");
            }
        }

        return null;
    }

    private enum CoreEnum {

        ALCHEMICAL_BRASS(0, 0),
        TERRA_STEEL(0, 0),
        OSMIUM(0, 0),
        PLATINUM(0, 0),
        DRAGON(0, 0);

        final int absorptionRate;
        final int absorptionQuantity;

        CoreEnum(int absorptionRate, int absorptionQuantity) {
            this.absorptionRate = absorptionRate;
            this.absorptionQuantity = absorptionQuantity;
        }

        public int getAbsorptionRate() {
            return absorptionRate;
        }

        public int getAbsorptionQuantity() {
            return absorptionQuantity;
        }
    }

}
