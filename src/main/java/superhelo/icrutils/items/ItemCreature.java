package superhelo.icrutils.items;

public class ItemCreature extends ItemBase {

    public ItemCreature() {
        super("creature_item");
    }
/*
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (Objects.nonNull(nbt)) {
            String creatureName = nbt.getString("name");
            tooltip.add(I18n.translateToLocal("entity." + creatureName + ".name"));
        }
    }
*/
}
