package rootmc.net.rootcore.module;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.event.AddRootPointEvent;
import rootmc.net.rootcore.event.CreateAccountRPEvent;
import rootmc.net.rootcore.event.ReduceRootPointEvent;
import rootmc.net.rootcore.event.SetRootPointEvent;
import rootmc.net.rootcore.screen.payment.TopRootPoint;

import java.util.*;

public class RootPointManager {

    private final RootCore plugin;

    public static final int RET_NO_ACCOUNT = -3;
    public static final int RET_CANCELLED = -2;
    public static final int RET_INVALID = 0;
    public static final int RET_SUCCESS = 1;


    public RootPointManager(RootCore plugin) {
        this.plugin = plugin;
    }

    public int reduceRootPoint(Player player, int amount) {
        if (amount < 0) {
            return RET_INVALID;
        }

        ReduceRootPointEvent event = new ReduceRootPointEvent(player.getName(), amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            amount = event.getAmount();
            int money;
            if ((money = plugin.getProvider().getRootPoint(player.getUniqueId())) != -1) {
                if (money - amount < 0) {
                    return RET_INVALID;
                } else {
                    plugin.getProvider().reduceRootPoint(player.getUniqueId(), amount);
                    return RET_SUCCESS;
                }
            } else {
                return RET_NO_ACCOUNT;
            }
        }
        return RET_CANCELLED;
    }


    public boolean hasAccount(UUID uuid) {
        return plugin.getProvider().accountExists(uuid);
    }

    public boolean createAccount(Player player) {
        CreateAccountRPEvent event = new CreateAccountRPEvent(player.getName(), 0);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            return plugin.getProvider().createAccount(player, "ENG"); //todo: set lang follow ip location
        }
        return false;
    }

    public LinkedHashMap<String, Integer> getAllRootPoint() {
        return plugin.getProvider().getAllRP();
    }

    public int myRootPoint(Player player) {
        return this.myRootPoint(player.getUniqueId());
    }

    public int myRootPoint(UUID uuid) {
        return myRootPointInternal(uuid);
    }

    private int myRootPointInternal(UUID uuid) {
        return plugin.getProvider().getRootPoint(uuid);
    }

    private int setRootPoint(Player player, int amount) {
        if (amount < 0) {
            return RET_INVALID;
        }

        SetRootPointEvent event = new SetRootPointEvent(player.getName(), amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            if (plugin.getProvider().accountExists(player.getUniqueId())) {
                amount = event.getAmount();
                if (amount <= 500) {
                    plugin.getProvider().setRootPoint(player.getUniqueId(), amount);
                    return RET_SUCCESS;
                } else {
                    return RET_INVALID;
                }
            } else {
                return RET_NO_ACCOUNT;
            }
        }
        return RET_CANCELLED;
    }

    public int addRootPoint(Player player, int amount) {
        if (amount < 0) {
            return RET_INVALID;
        }
        AddRootPointEvent event = new AddRootPointEvent(player.getName(), amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            int money;
            if ((money = plugin.getProvider().getRootPoint(player.getUniqueId())) != -1) {
                if (money + amount > 999999999) {
                    return RET_INVALID;
                } else {
                    plugin.getProvider().addRootPoint(player.getUniqueId(), amount);
                    return RET_SUCCESS;
                }
            } else {
                return RET_NO_ACCOUNT;
            }
        }
        return RET_CANCELLED;
    }


    public void sendTopRootPoint(Player player, int args) {
        final LinkedHashMap<String, Integer> rootpoint = getAllRootPoint();
        final Set<String> players = rootpoint.keySet();
        final int page = args > 0 ? Math.max(1, Math.min(args, players.size())) : 1;
        StringBuilder output = new StringBuilder();

        plugin.getServer().getScheduler().scheduleTask(() -> {
            List<String> list = new LinkedList<>(rootpoint.keySet());
            list.sort((s1, s2) -> Double.compare(rootpoint.get(s2), rootpoint.get(s1)));
            output.append("§f☆ §cTop RootPoint §f- §cTrang §f").append(page).append("/").append((players.size() + 6) / 5).append(" §f☆\n\n");
            output.append("§f❀ §bRootPoint của bạn :§f ").append(myRootPoint(player.getUniqueId())).append("\n\n");
            if (page == 1) {
                double total = 0;
                for (double val : rootpoint.values()) {
                    total += val;
                }
                output.append("§f❀ §bTổng RootPoint trong server :§f ").append(total).append("\n\n");
            }
            int duplicate = 0;
            double prev = -1D;
            for (int n = 0; n < list.size(); n++) {
                int current = (int) Math.ceil((double) (n + 1) / 5);
                if (page == current) {
                    double m = rootpoint.get(list.get(n));
                    if (m == prev) duplicate++;
                    else duplicate = 0;
                    prev = m;
                    output.append("✔ §bTOP").append(n + 1 - duplicate).append(" - §f").append(getName(list.get(n))).append(" : §f").append(m).append(" RP").append("\n\n");
                } else if (page < current) {
                    break;
                }
            }
            output.substring(0, output.length() - 1);
            player.showFormWindow(new TopRootPoint(player, output));
        });
    }

    private static String getName(String possibleUuid) {
        UUID uuid;
        try {
            uuid = UUID.fromString(possibleUuid);
        } catch (Exception e) {
            return possibleUuid;
        }

        IPlayer player = Server.getInstance().getOfflinePlayer(uuid);
        if (player != null && player.getName() != null) {
            return player.getName();
        }
        return possibleUuid;
    }
}
