package rootmc.net.rootcore.screen.shop;

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

        addButton(new ElementButton("Trở về", new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL, "https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color-round-1/254000/26-512.png")));
        // If there is only back button
        if (getButtons().size() == 1)
            setContent("Không có cái gì để mua ");
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        // If back button pressed
        int rp = RootCore.get().getRootPointManager().myRootPoint(event.getPlayer().getUniqueId());
        if (!(getResponse().getClickedButton() instanceof BuyKitButton)) {
            event.getPlayer().showFormWindow(new BuyCategoriesScreen(rp));
            return;
        }
        BuyKitButton button = (BuyKitButton) getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuyScreen(button.getKey(), button.getCustomName(), button.getPrice(), rp, button.getDes()));
    }

}
