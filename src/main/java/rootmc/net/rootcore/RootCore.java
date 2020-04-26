package rootmc.net.rootcore;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.utils.Config;
import rootmc.net.rootcore.command.EchestCommand;
import rootmc.net.rootcore.command.RPCommand;
import rootmc.net.rootcore.module.RootPointManager;
import rootmc.net.rootcore.provider.MysqlProvider;
import rootmc.net.rootcore.provider.Provider;
import rootmc.net.rootcore.screen.EventScreen;
import rootmc.net.rootcore.screen.JoinScreen;
import rootmc.net.rootcore.screen.shop.BuyCategoriesScreen;
import rootmc.net.rootcore.screen.vip.ListVipScreen;

import java.io.File;
import java.util.*;

public class RootCore extends RootCoreAPI implements Listener {

    private static RootCore instance;
    private HashMap<String, Class<?>> providerClass = new HashMap<>();
    public HashMap<UUID, Long> commandMeCache = new HashMap<UUID, Long>();
    public static Config shopCfg, rankCfg;

    @Override
    public void onLoad() {
        if (instance == null) {
            instance = this;
        }
        instance = this;

        this.addProvider("mysql", MysqlProvider.class);
    }

    @Override
    public void onEnable() {
        initConfig();
        initCommands();
        getServer().getPluginManager().registerEvents(new RootPointListener(this), this);
        selectProvider();
        rootPointManager = new RootPointManager(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        switch (command.getName().toLowerCase()){
            case "info":
                ((Player) sender).showFormWindow(new JoinScreen());
                break;
            case "event":
                ((Player) sender).showFormWindow(new EventScreen());
                break;
            case "vip":
                ((Player) sender).showFormWindow(new ListVipScreen(((Player) sender).getUniqueId()));
                break;
            case "shoprp":
                ((Player) sender).showFormWindow(new BuyCategoriesScreen(this.getRootPointManager().myRootPoint(((Player) sender).getUniqueId())));
                break;
        }
        return true;
    }

    private void initConfig(){
        saveDefaultConfig();
        saveResource("kitRPList.yml");
        saveResource("vipList.yml");
        shopCfg = new Config(new File(getDataFolder(), "kitRPList.yml"));
        rankCfg = new Config(new File(getDataFolder(), "vipList.yml"));
    }

    private void initCommands(){
        List<Command> cmList = new ArrayList<Command>();
        cmList.add(new RPCommand(this));
        cmList.add(new EchestCommand(this));
        getServer().getCommandMap().registerAll("RootCore",cmList);
    }

    public static RootCore get() {
        return instance;
    }

    //Provider RootCore
    public void addProvider(String name, Class<? extends Provider> providerClass) {
        this.providerClass.put(name, providerClass);
    }

    private void selectProvider() {
        Class<?> providerClass = this.providerClass.get("mysql");

        if (providerClass == null) {
            this.getLogger().critical("Invalid data provider was given.");
            return;
        }
        try {
            provider = (Provider) providerClass.newInstance();
            provider.init(this.getDataFolder());
        } catch (InstantiationException | IllegalAccessException e) {
            this.getLogger().critical("Invalid data provider was given.");
            return;
        }
        provider.open();
        this.getLogger().notice("Data provider was set to: " + provider.getName());
    }
}
