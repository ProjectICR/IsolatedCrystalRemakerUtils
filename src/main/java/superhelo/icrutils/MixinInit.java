package superhelo.icrutils;

import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.MixinLoader;

@MixinLoader
public class MixinInit {

    public MixinInit() {
        Mixins.addConfiguration("mixin.icrutils.json");
    }

}
