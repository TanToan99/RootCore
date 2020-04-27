package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.info.MenuInfo;
import rootmc.net.rootcore.screen.shop.BuyCategoriesScreen;
import rootmc.net.rootcore.screen.vip.MenuVipScreen;

public class VIPCommand extends PluginCommand<Plugin> implements CommandExecutor {

    public VIPCommand(Plugin owner) {
        super("vip", owner);
        setExecutor(this);
        setDescription("Thông Tin VIP, Mua VIP!");
        setPermission("rootcore.command.vip");
        getCommandParameters().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        ((Player) sender).showFormWindow(new MenuVipScreen());
        return true;
    }
}