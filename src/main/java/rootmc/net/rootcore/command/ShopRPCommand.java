package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.shop.BuyCategoriesScreen;

public class ShopRPCommand extends PluginCommand<Plugin> implements CommandExecutor {

    public ShopRPCommand(Plugin owner) {
        super("shoprp", owner);
        setExecutor(this);
        setDescription("Shop Root Point, nơi bán đồ vip, key và đồ hiếm!");
        setPermission("rootcore.command.shoprp");
        getCommandParameters().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player)sender;
        int rp = RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId());
        player.showFormWindow(new BuyCategoriesScreen(rp));
        return true;
    }
}
