package rootmc.net.rootcore.provider;

import cn.nukkit.Player;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

public interface  Provider {

    void init(File path);

    void open();

    void add_transaction(UUID uuid, String type, String content, int amount, int surplus);

    void save();

    boolean accountExists(UUID uuid);

    boolean removeAccount(UUID uuid);

    boolean createAccount(Player player, String language);

    boolean setRootPoint(UUID uuid, int amount);

    boolean addRootPoint(UUID uuid, int amount);

    boolean reduceRootPoint(UUID uuid, int amount);

    int getRootPoint(UUID uuid);

    String getPassLv2(UUID uuid);

    boolean setPassLv2(UUID uuid, String pass);

    LinkedHashMap<String, Integer> getAllRP();
}