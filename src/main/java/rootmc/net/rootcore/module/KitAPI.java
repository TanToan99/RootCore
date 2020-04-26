package rootmc.net.rootcore.module;

import cn.nukkit.utils.ConfigSection;
import rootmc.net.rootcore.RootCore;

public abstract class KitAPI {

    // Getting price and applying discount
    public static int getPrice(String categoryId, String key) {
        ConfigSection itemSection = RootCore.shopCfg.getSection(categoryId).getSection("kits").getSection(key);
        return itemSection.getInt("price");
    }

    public static String getUrl(String categoryId, String key) {
        ConfigSection itemSection = RootCore.shopCfg.getSection(categoryId).getSection("kits").getSection(key);
        return itemSection.getString("url");
    }

    public static String getCustomName(String categoryId, String key) {
        ConfigSection itemSection = RootCore.shopCfg.getSection(categoryId).getSection("kits").getSection(key);
        return itemSection.getString("customname");
    }

    public static String getDes(String categoryId, String key) {
        ConfigSection itemSection = RootCore.shopCfg.getSection(categoryId).getSection("kits").getSection(key);
        return itemSection.getString("des");
    }

}