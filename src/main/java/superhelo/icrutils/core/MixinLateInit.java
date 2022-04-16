package superhelo.icrutils.core;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import superhelo.icrutils.ICRUtils;
import zone.rong.mixinbooter.ILateMixinLoader;

public class MixinLateInit implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        LogManager.getLogger(ICRUtils.NAME + " Mixins").info("Init Mods Mixins");
        return Lists.newArrayList("mixins.icrutils.mods.json");
    }

}
