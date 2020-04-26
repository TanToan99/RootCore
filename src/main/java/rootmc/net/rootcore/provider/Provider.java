package rootmc.net.rootcore.provider;

import java.io.File;
import java.util.LinkedHashMap;

public interface  Provider {

    void init(File path);

    void open();

    void add_transaction(String player, String type, String content, int amount, int surplus);

    void save();

    void close();

    boolean accountExists(String uuid);

    boolean removeAccount(String uuid);

    boolean createAccount(String uuid, int defaultMoney);

    boolean setRootPoint(String uuid, int amount);

    boolean addRootPoint(String uuid, int amount);

    boolean reduceRootPoint(String uuid, int amount);

    int getRootPoint(String uuid);

    String getPassLv2(String uuid);

    boolean setPassLv2(String uuid, String pass);

    LinkedHashMap<String, Integer> getAll();

    String getName();
}