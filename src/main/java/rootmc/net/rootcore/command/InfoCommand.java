package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import rootmc.net.rootcore.screen.info.MenuInfo;

public class InfoCommand extends PluginCommand<Plugin> implements CommandExecutor {

    public InfoCommand(Plugin owner) {
        super("info", owner);
        setExecutor(this);
        setDescription("Thông tin cơ bản - lối chơi - Lệnh!");
        setPermission("rootcore.command.info");
        getCommandParameters().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;
        ((Player) sender).showFormWindow(new MenuInfo());
        return true;
    }
}
