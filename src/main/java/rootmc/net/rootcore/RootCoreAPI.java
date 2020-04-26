package rootmc.net.rootcore;

import cn.nukkit.plugin.PluginBase;
import lombok.Getter;
import rootmc.net.rootcore.module.RootPointManager;
import rootmc.net.rootcore.provider.Provider;

public class RootCoreAPI extends PluginBase {

    @Getter
    protected RootPointManager rootPointManager;
    @Getter
    protected Provider provider;
}
