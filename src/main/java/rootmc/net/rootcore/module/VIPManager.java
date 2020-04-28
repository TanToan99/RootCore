package rootmc.net.rootcore.module;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.data.RankData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VIPManager {

    private final RootCore plugin;
    private final List<RankData> rankDatas = new ArrayList<>();
    private final Map<String, Integer> playerRankLv = new HashMap<>();

    public VIPManager(RootCore plugin) {
        this.plugin = plugin;
        initConfig();
    }

    void initConfig() {
        Config rankCfg = new Config(new File(plugin.getDataFolder(), "vipList.yml"));
        for (String key : rankCfg.getKeys(false)) {
            ConfigSection cSection = rankCfg.getSection(key);
            rankDatas.add(new RankData(key, cSection.getString("customname"), cSection.getString("des"), cSection.getInt("day"), cSection.getInt("level"), cSection.getInt("rp"), cSection.getInt("rpvv"), cSection.getString("url")));
        }

        Config vipData = new Config(new File(plugin.getDataFolder(), "vipData.yml"));
        vipData.getKeys(false).forEach(leader -> playerRankLv.put(leader, vipData.getInt(leader)));
    }

    void saveData() {
        Config vipData = new Config(new File(plugin.getDataFolder(), "vipData.yml"));
        for (Map.Entry<String, Integer> data : playerRankLv.entrySet()) {
            vipData.set(data.getKey(), data.getValue());
        }
        vipData.save();
    }


    public List<RankData> getrankDatas() {
        return rankDatas;
    }

    public RankData getRankByKey(String customName) {
        return rankDatas.stream().filter(i -> i.getKey().equalsIgnoreCase(customName)).findFirst().orElse(null);
    }

    public RankData getRankByCustomName(String customName) {
        return rankDatas.stream().filter(i -> i.getCusName().equalsIgnoreCase(customName)).findFirst().orElse(null);
    }

    public boolean canBuyLevel(String playerName, int level) {
        if (!playerRankLv.containsKey(playerName) && level == 1) return true;
        return playerRankLv.containsKey(playerName) && (level == 1 + playerRankLv.get(playerName));
    }

    public void writeData(String playerName, int level) {
        playerRankLv.put(playerName, level);
        saveData();
    }

    public boolean hasRankF(String playerName) {
        return playerRankLv.containsKey(playerName);
    }

    public String getrankF(String playerName) {
        return getRankKeyF(playerRankLv.get(playerName)); //need check not null
    }

    public String getRankKeyF(int level) {
        RankData rank = rankDatas.stream().filter(i -> i.getLevel() == level).findFirst().orElse(null);
        if (rank != null) {
            return rank.getKey();
        }
        return "";
    }
}
