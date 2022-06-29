package superhelo.icrutils.utils.nbt;

import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public final class TagCompoundProxy {

    private final NBTTagCompound nbt;

    public TagCompoundProxy() {
        this.nbt = new NBTTagCompound();
    }

    public Set<String> getKeySet() {
        return this.nbt.getKeySet();
    }

    public int getSize() {
        return this.nbt.getSize();
    }

    public TagCompoundProxy setTag(String key, NBTBase value) {
        this.nbt.setTag(key, value);
        return this;
    }

    public TagCompoundProxy setByte(String key, byte value) {
        this.nbt.setByte(key, value);
        return this;
    }

    public TagCompoundProxy setShort(String key, short value) {
        this.nbt.setShort(key, value);
        return this;
    }

    public TagCompoundProxy setInteger(String key, int value) {
        this.nbt.setInteger(key, value);
        return this;
    }

    public TagCompoundProxy setLong(String key, long value) {
        this.nbt.setLong(key, value);
        return this;
    }

    public TagCompoundProxy setUniqueId(String key, UUID value) {
        this.nbt.setUniqueId(key, value);
        return this;
    }

    public TagCompoundProxy setFloat(String key, float value) {
        this.nbt.setFloat(key, value);
        return this;
    }

    public TagCompoundProxy setDouble(String key, double value) {
        this.nbt.setDouble(key, value);
        return this;
    }

    public TagCompoundProxy setString(String key, String value) {
        this.nbt.setString(key, value);
        return this;
    }

    public TagCompoundProxy setByteArray(String key, byte[] value) {
        this.nbt.setByteArray(key, value);
        return this;
    }

    public TagCompoundProxy setIntArray(String key, int[] value) {
        this.nbt.setIntArray(key, value);
        return this;
    }

    public TagCompoundProxy setBoolean(String key, boolean value) {
        this.nbt.setBoolean(key, value);
        return this;
    }

    public NBTTagCompound getNbt() {
        return this.nbt;
    }

}
