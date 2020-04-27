package rootmc.net.rootcore.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.plugin.Plugin;
import rootmc.net.rootcore.RootCore;

public class RPCommand extends PluginCommand<Plugin> implements CommandExecutor {

    @SuppressWarnings("unchecked")
    public RPCommand(Plugin owner) {
        super("rp", owner);
        setExecutor(this);
        setDescription("Root Point in RootNetwork - Minecraft Server");
        setPermission("rootcore.command.rp");
        getCommandParameters().clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isPlayer()){
            if (args.length == 0) {
                sender.sendMessage("/rp give <tên> <số rp>");
                sender.sendMessage("/rp reduce <tên> <số rp>");
                sender.sendMessage("/rp set <tên> <số rp>");
                return true;
            }
            switch (args[0]){
                case "give":
                    Player player = sender.getServer().getPlayer(args[1]);
                    if (player != null) {
                        if (!isInteger(args[2])) {
                            sender.sendMessage("/rp give <tên> <số rp>");
                            return true;
                        }
                        if(RootCore.get().getProvider().addRootPoint(player.getUniqueId().toString(),Integer.parseInt(args[2]))){
                            player.sendMessage("Bạn được cộng "+args[2]+"RP vào tài khoản");
                            sender.sendMessage("Cộng thành công ");
                        }
                    }else{
                        sender.sendMessage("người chơi không online");
                    }
                    break;
                case "reduce":
                    Player player1 = sender.getServer().getPlayer(args[1]);
                    if (player1 != null) {
                        if (!isInteger(args[2])) {
                            sender.sendMessage("/rp give <tên> <số rp>");
                            return true;
                        }
                        if(RootCore.get().getProvider().reduceRootPoint(player1.getUniqueId().toString(),Integer.parseInt(args[2]))){
                            player1.sendMessage("Bạn bị trừ "+args[2] +"RP vào tài khoản");
                            sender.sendMessage("Trừ thành công ");
                        }
                    }else{
                        sender.sendMessage("người chơi không online");
                    }
                    break;
                case "set":
                    Player player2 = sender.getServer().getPlayer(args[1]);
                    if (player2 != null) {
                        if (!isInteger(args[2])) {
                            sender.sendMessage("/rp give <tên> <số rp>");
                            return true;
                        }
                        if(RootCore.get().getProvider().setRootPoint(player2.getUniqueId().toString(),Integer.parseInt(args[2]))){
                            player2.sendMessage("Bạn được cộng "+args[2]+"RP vào tài khoản");
                            sender.sendMessage("Cộng thành công ");
                        }
                    }else{
                        sender.sendMessage("người chơi không online");
                    }
                    break;
                default:
                    sender.sendMessage("/rp give <tên> <số rp>");
                    sender.sendMessage("/rp reduce <tên> <số rp>");
                    sender.sendMessage("/rp set <tên> <số rp>");
            }
            return true;
        }

        Player player = sender.getServer().getPlayer(sender.getName());
        if (player != null){
            //player.showFormWindow(new MenuScreen(player.getUniqueId()));
        }
        return true;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
