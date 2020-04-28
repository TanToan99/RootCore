package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.NoticeScreen;

public class EventCommand extends PluginCommand<Plugin> implements CommandExecutor {

    public EventCommand(Plugin owner) {
        super("event", owner);
        setExecutor(this);
        setDescription("Sư kiện - đua top!");
        setPermission("rootcore.command.event");
        getCommandParameters().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return true;
        ((Player) sender).showFormWindow(new NoticeScreen("§c§lRoot§r§lNetworκ §r® Sự kiện",RootCore.get().getConfig().getString("events")));
        return true;
    }
}
