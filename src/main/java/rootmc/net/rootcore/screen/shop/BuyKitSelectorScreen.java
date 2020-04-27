package rootmc.net.rootcore.screen.shop;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import com.fleynaro.advancedkits.Kit;
import com.fleynaro.advancedkits.Main;
import rootmc.net.rootcore.module.KitAPI;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.element.BuyKitButton;
import rootmc.net.rootcore.screen.Screen;

public class BuyKitSelectorScreen extends FormWindowSimple implements Screen {

    private String categoryId;

    public BuyKitSelectorScreen(String categoryId, String customCateName) {
        super(customCateName, "");
        this.categoryId = categoryId;

        for (String key : RootCore.shopCfg.getSection(categoryId).getSection("kits").getKeys(false)) {
            String url = KitAPI.getUrl(categoryId, key);
            Kit kit = Main.instance.getKit(key);
            if (kit == null) {
                Server.getInstance().getLogger().warning("Kit " + key + " khong hop le trong SHOP RP, vui long kiem tra lai");
                continue;
            }
            String customname = KitAPI.getCustomName(categoryId, key);
            int price = KitAPI.getPrice(categoryId, key);
            String des = KitAPI.getDes(categoryId, key);
            addButton(new BuyKitButton(key, customname, url, price, des));
        }
        if (getButtons().size() == 1)
            setContent("Không có cái gì để mua, vote del op khánh ");
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        int rp = RootCore.get().getRootPointManager().myRootPoint(event.getPlayer().getUniqueId());
        BuyKitButton button = (BuyKitButton) getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuyScreen(button.getKey(), button.getCustomName(), button.getPrice(), rp, button.getDes()));
    }

    @Override
    public void onClose(Player player) {
        int rp = RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId());
        player.showFormWindow(new BuyCategoriesScreen(rp));
    }

}
