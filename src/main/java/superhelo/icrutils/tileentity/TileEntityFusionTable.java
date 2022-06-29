package superhelo.icrutils.tileentity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.mutable.MutableInt;
import superhelo.icrutils.recipe.FusionTableRecipe;
import superhelo.icrutils.utils.Utils;
import superhelo.icrutils.utils.WorldUtils;
import superhelo.icrutils.utils.item.ItemStackProxy;

public class TileEntityFusionTable extends TileEntityBase implements ITickable {

    private MutableInt time = new MutableInt();

    @Override
    public void update() {
        if (world.isRemote) {
            return;
        }
        WorldServer worldServer = (WorldServer) this.world;
        List<TileEntityCeremonialColumn> tiles = this.getTileAround();
        List<ItemStackProxy> stackList = tiles
            .stream()
            .map(tile -> tile.getInventory().getStackInSlot(0))
            .filter(Utils::isNotEmpty)
            .map(ItemStackProxy::new)
            .collect(Collectors.toList());

        if (!stackList.isEmpty()) {
            Optional<FusionTableRecipe> recipe = FusionTableRecipe.RECIPES
                .stream()
                .filter(recipeIn -> recipeIn.getInputs().size() == stackList.size())
                .filter(recipeIn -> recipeIn.match(stackList))
                .findFirst();

            recipe.ifPresent(recipeIn -> {
                tiles.stream()
                    .map(TileEntity::getPos)
                    .forEach(pos -> {
                        if (this.world.getWorldTime() % 10 == 0) {
                            for (int i = 1; i <= 10; i++) {
                                worldServer.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + 0.5D, pos.getY() + 1.5D + (i / 2.0D), pos.getZ() + 0.5D, 1, 0D, 0D, 0D, 0D);
                            }
                        }
                    });
                if (time.incrementAndGet() >= recipeIn.getTick()) {
                    time.setValue(0);
                    tiles.stream()
                        .map(TileEntityCeremonialColumn::getInventory)
                        .forEach(inventory -> inventory.setStackInSlot(0, ItemStack.EMPTY));
                    worldServer.playSound(null, this.pos, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.VOICE, 5F, 3F);
                    WorldUtils.spawnEntity(this.world, this.pos.up(), recipeIn.getOutput().newInstance(this.world));
                }
            });
        }

    }

    private List<TileEntityCeremonialColumn> getTileAround() {
        TileEntityCeremonialColumn[] array = new TileEntityCeremonialColumn[4];

        for (int i = 0; i < EnumFacing.HORIZONTALS.length; i++) {
            TileEntity tile = this.world.getTileEntity(pos.offset(EnumFacing.HORIZONTALS[i], 3));
            if (Objects.isNull(tile) || !(tile instanceof TileEntityCeremonialColumn)) {
                return Collections.emptyList();
            }
            array[i] = (TileEntityCeremonialColumn) tile;
        }
        return Arrays.asList(array);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.time = new MutableInt(compound.getInteger("time"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("time", this.time.intValue());
        return super.writeToNBT(compound);
    }

}
