package superhelo.icrutils.core;

import com.google.common.collect.Lists;
import java.util.List;
import zone.rong.mixinbooter.ILateMixinLoader;

public class MixinLateInit implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList("mixins.icrutils.mods.json");
    }

}
