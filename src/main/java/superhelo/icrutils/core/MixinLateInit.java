package superhelo.icrutils.core;

import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import superhelo.icrutils.ICRUtils;
import zone.rong.mixinbooter.ILateMixinLoader;

public class MixinLateInit implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        LogManager.getLogger(ICRUtils.NAME + " Mixins").info("Init Mods Mixins");
        return Collections.singletonList("mixins.icrutils.mods.json");
    }

}
