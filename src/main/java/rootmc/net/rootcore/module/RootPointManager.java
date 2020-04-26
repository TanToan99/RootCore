package rootmc.net.rootcore.module;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.event.AddRootPointEvent;
import rootmc.net.rootcore.event.CreateAccountRPEvent;
import rootmc.net.rootcore.event.ReduceRootPointEvent;
import rootmc.net.rootcore.event.SetRootPointEvent;
import rootmc.net.rootcore.provider.Provider;
import rootmc.net.rootcore.screen.payment.TopRootPoint;

import java.util.*;

public class RootPointManager {

    private RootCore plugin;

    public static final int RET_NO_ACCOUNT = -3;
    public static final int RET_CANCELLED = -2;
    public static final int RET_INVALID = 0;
    public static final int RET_SUCCESS = 1;


    public RootPointManager(RootCore plugin){
        this.plugin = plugin;
    }

    public int reduceRootPoint(Player player, int amount) {
        return this.reduceRootPoint(player, amount, false);
    }

    public int reduceRootPoint(Player player, int amount, boolean force) {
        return this.reduceRootPoint(player.getUniqueId(), amount, force);
    }

    public int reduceRootPoint(IPlayer player, int amount) {
        return this.reduceRootPoint(player, amount, false);
    }

    public int reduceRootPoint(IPlayer player, int amount, boolean force) {
        return this.reduceRootPoint(player.getUniqueId(), amount, force);
    }

    public int reduceRootPoint(UUID id, int amount) {
        return this.reduceRootPoint(id, amount, false);
    }

    public int reduceRootPoint(UUID id, int amount, boolean force) {
        checkAndConvertLegacy(id);
        return reduceRootPointInternal(id.toString(), amount, force);
    }

    public int reduceRootPoint(String id, int amount) {
        return this.reduceRootPoint(id, amount, false);
    }

    public int reduceRootPoint(String id, int amount, boolean force) {
        Optional<UUID> uuid = checkAndConvertLegacy(id);
        return uuid.map(uuid1 -> reduceRootPoint(uuid1, amount, force))
                .orElse(reduceRootPointInternal(id, amount, force));
    }

    private int reduceRootPointInternal(String id, int amount, boolean force) {
        id = id.toLowerCase();
        if (amount < 0) {
            return RET_INVALID;
        }

        ReduceRootPointEvent event = new ReduceRootPointEvent(id, amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled() || force) {
            amount = event.getAmount();
            int money;
            if ((money = plugin.getProvider().getRootPoint(id)) != -1) {
                if (money - amount < 0) {
                    return RET_INVALID;
                } else {
                    plugin.getProvider().reduceRootPoint(id, amount);
                    return RET_SUCCESS;
                }
            } else {
                return RET_NO_ACCOUNT;
            }
        }
        return RET_CANCELLED;
    }

    public boolean hasAccount(IPlayer player) {
        return hasAccount(player.getUniqueId());
    }

    public boolean hasAccount(UUID id) {
        return hasAccount(id.toString());
    }

    public boolean hasAccount(String id) {
        return plugin.getProvider().accountExists(checkAndConvertLegacy(id).map(UUID::toString).map(String::toLowerCase).orElse(id));
    }


    private void checkAndConvertLegacy(UUID uuid) {
        IPlayer player = plugin.getServer().getOfflinePlayer(uuid);
        if (player != null && player.getName() != null) {
            checkAndConvertLegacy(uuid, player.getName());
        }
    }

    private Optional<UUID> checkAndConvertLegacy(String id) {
        Optional<UUID> uuid = plugin.getServer().lookupName(id);
        uuid.ifPresent(uuid1 -> checkAndConvertLegacy(uuid1, id));
        return uuid;
    }

    private void checkAndConvertLegacy(UUID uuid, String name) {
        name = name.toLowerCase();
        Provider provider = plugin.getProvider();
        if (!provider.accountExists(name)) {
            return;
        }

        if (provider.accountExists(uuid.toString())) {
            provider.removeAccount(name);
            return;
        }

        int money = provider.getRootPoint(name);
        provider.createAccount(uuid.toString(), money);
        provider.removeAccount(name);
    }

    //RootCore Process Function
    public boolean createAccount(Player player) {
        return createAccount(player, 0);
    }

    public boolean createAccount(Player player, int defaultMoney) {
        return this.createAccount(player, defaultMoney, false);
    }

    public boolean createAccount(Player player, int defaultMoney, boolean force) {
        return this.createAccount(player.getUniqueId(), defaultMoney, force);
    }

    public boolean createAccount(IPlayer player) {
        return this.createAccount(player, 0);
    }

    public boolean createAccount(IPlayer player, int defaultMoney) {
        return this.createAccount(player, defaultMoney, false);
    }

    public boolean createAccount(IPlayer player, int defaultMoney, boolean force) {
        return this.createAccount(player.getUniqueId(), defaultMoney, force);
    }

    public boolean createAccount(UUID id, int defaultMoney) {
        return this.createAccount(id, defaultMoney, false);
    }

    public boolean createAccount(UUID id, int defaultMoney, boolean force) {
        checkAndConvertLegacy(id);
        return createAccount(id.toString(), defaultMoney, force);
    }

    public boolean createAccount(String id, int defaultMoney, boolean force) {
        Optional<UUID> uuid = checkAndConvertLegacy(id);
        return uuid.map(uuid1 -> createAccount(uuid1, defaultMoney, force))
                .orElse(createAccountInternal(id, defaultMoney, force));
    }


    private boolean createAccountInternal(String id, int defaultMoney, boolean force) {
        CreateAccountRPEvent event = new CreateAccountRPEvent(id, defaultMoney);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled() || force) {
            return plugin.getProvider().createAccount(id, event.getDefaultRP());
        }
        return false;
    }

    public LinkedHashMap<String, Integer> getAllRootPoint() {
        return plugin.getProvider().getAll();
    }

    public int myRootPoint(Player player) {
        return this.myRootPoint(player.getUniqueId());
    }

    public int myRootPoint(IPlayer player) {
        return this.myRootPoint(player.getUniqueId());
    }

    public int myRootPoint(UUID id) {
        checkAndConvertLegacy(id);
        return myRootPointInternal(id.toString());
    }

    public int myRootPoint(UUID uniqueId, String id) {
        Optional<UUID> uuid = checkAndConvertLegacy(id);
        return uuid.map(this::myRootPoint).orElse(myRootPointInternal(id));
    }

    private int myRootPointInternal(String id) {
        return plugin.getProvider().getRootPoint(id.toLowerCase());
    }

    public int setRootPoint(Player player, int amount) {
        return this.setRootPoint(player, amount, false);
    }

    public int setRootPoint(Player player, int amount, boolean force) {
        return this.setRootPoint(player.getUniqueId(), amount, force);
    }

    public int setRootPoint(IPlayer player, int amount) {
        return this.setRootPoint(player, amount, false);
    }

    public int setRootPoint(IPlayer player, int amount, boolean force) {
        return this.setRootPoint(player.getUniqueId(), amount, force);
    }

    public int setRootPoint(UUID id, int amount) {
        return setRootPoint(id, amount, false);
    }

    public int setRootPoint(UUID id, int amount, boolean force) {
        checkAndConvertLegacy(id);
        return setRootPointInternal(id.toString(), amount, force);
    }

    public int setRootPoint(String id, int amount) {
        return this.setRootPoint(id, amount, false);
    }

    public int setRootPoint(String id, int amount, boolean force) {
        Optional<UUID> uuid = checkAndConvertLegacy(id);
        return uuid.map(uuid1 -> setRootPoint(uuid1, amount, force))
                .orElse(setRootPointInternal(id, amount, force));
    }

    private int setRootPointInternal(String id, int amount, boolean force) {
        id = id.toLowerCase();
        if (amount < 0) {
            return RET_INVALID;
        }

        SetRootPointEvent event = new SetRootPointEvent(id, amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled() || force) {
            if (plugin.getProvider().accountExists(id)) {
                amount = event.getAmount();
                if (amount <= 500) {
                    plugin.getProvider().setRootPoint(id, amount);
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
        return this.addRootPoint(player, amount, false);
    }

    public int addRootPoint(Player player, int amount, boolean force) {
        return this.addRootPoint(player.getUniqueId(), amount, force);
    }

    public int addRootPoint(IPlayer player, int amount) {
        return this.addRootPoint(player, amount, false);
    }

    public int addRootPoint(IPlayer player, int amount, boolean force) {
        return this.addRootPoint(player.getUniqueId(), amount, force);
    }

    public int addRootPoint(UUID id, int amount) {
        return addRootPoint(id, amount, false);
    }

    public int addRootPoint(UUID id, int amount, boolean force) {
        checkAndConvertLegacy(id);
        return addRootPointInternal(id.toString(), amount, force);
    }

    public int addRootPoint(String id, int amount) {
        return this.addRootPoint(id, amount, false);
    }

    public int addRootPoint(String id, int amount, boolean force) {
        Optional<UUID> uuid = checkAndConvertLegacy(id);
        return uuid.map(uuid1 -> addRootPoint(uuid1, amount, force))
                .orElse(addRootPointInternal(id, amount, force));
    }

    private int addRootPointInternal(String id, int amount, boolean force) {
        id = id.toLowerCase();
        if (amount < 0) {
            return RET_INVALID;
        }
        AddRootPointEvent event = new AddRootPointEvent(id, amount);
        plugin.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled() || force) {
            int money;
            if ((money = plugin.getProvider().getRootPoint(id)) != -1) {
                if (money + amount > 999999999) {
                    return RET_INVALID;
                } else {
                    plugin.getProvider().addRootPoint(id, amount);
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
            output.append("§f❀ §bRootPoint của bạn :§f ").append(myRootPoint(player.getUniqueId(), player.getName())).append("\n\n");
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

    public void saveAll() {
        if (plugin.getProvider() != null) {
            plugin.getProvider().save();
        }
    }
}
