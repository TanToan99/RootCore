package rootmc.net.rootcore;

import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import rootmc.net.rootcore.command.*;
import rootmc.net.rootcore.module.RootPointManager;
import rootmc.net.rootcore.module.VIPManager;
import rootmc.net.rootcore.provider.MysqlProvider;
import rootmc.net.rootcore.provider.Provider;

import java.io.File;
import java.util.*;

public class RootCore extends RootCoreAPI implements Listener {

    private static RootCore instance;
    public HashMap<UUID, Long> commandMeCache = new HashMap<>();
    public static Config shopCfg;

    @Override
    public void onLoad() {
        if (instance == null) {
            instance = this;
        }
        instance = this;
    }

    @Override
    public void onEnable() {
        initConfig();
        initCommands();
        getServer().getPluginManager().registerEvents(new RootPointListener(this), this);
        selectProvider();
        rootPointManager = new RootPointManager(this);
        vipManager = new VIPManager(this);
    }


    private void initConfig(){
        saveDefaultConfig();
        saveResource("kitRPList.yml");
        saveResource("vipList.yml");
        shopCfg = new Config(new File(getDataFolder(), "kitRPList.yml"));
    }

    private void initCommands(){
        List<Command> cmList = new ArrayList<Command>();
        cmList.add(new EchestCommand(this));
        cmList.add(new EventCommand(this));
        cmList.add(new InfoCommand(this));
        cmList.add(new RPCommand(this));
        cmList.add(new ShopRPCommand(this));
        cmList.add(new VIPCommand(this));
        getServer().getCommandMap().registerAll("RootCore",cmList);
    }

    public static RootCore get() {
        return instance;
    }

    private void selectProvider() {
        Class<?> providerClass = MysqlProvider.class;
        try {
            provider = (Provider) providerClass.newInstance();
            provider.init(this.getDataFolder());
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().critical("Invalid data provider was given.");
            return;
        }
        provider.open();
        this.getLogger().notice("Data provider was set to: MySQL");
    }
}
