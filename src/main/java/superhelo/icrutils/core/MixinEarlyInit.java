package superhelo.icrutils.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.apache.logging.log4j.LogManager;
import superhelo.icrutils.ICRUtils;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@Name("ICRUtils Core")
@MCVersion(ForgeVersion.mcVersion)
public class MixinEarlyInit implements IEarlyMixinLoader, IFMLLoadingPlugin {

    @Override
    public List<String> getMixinConfigs() {
        LogManager.getLogger(ICRUtils.NAME + " Mixins").info("Init Vanilla Mixins");
        return Collections.singletonList("mixins.icrutils.vanilla.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
