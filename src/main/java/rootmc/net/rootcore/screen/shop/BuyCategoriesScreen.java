package rootmc.net.rootcore.screen.shop;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.element.CategoryButton;
import rootmc.net.rootcore.screen.MenuScreen;
import rootmc.net.rootcore.screen.Screen;

public class BuyCategoriesScreen extends FormWindowSimple implements Screen {

    public BuyCategoriesScreen(int rp) {
        super("§l§aV§eI§cP§r §l§fShopRP", "☆ §l§eRPoint của bạn: §f"+rp+"RP\n" +
                "☆ §l§eTất cả đồ trong Shop RP bạn sẽ không thể kiếm ở chỗ khác !");
        for (String key : RootCore.shopCfg.getKeys(false)) {
            String categoryName = RootCore.shopCfg.getSection(key).getString("name");
            String url = RootCore.shopCfg.getSection(key).getString("url");
            addButton(new CategoryButton(key, categoryName, url));
        }
        addButton(new ElementButton("Trở về",new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,"https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color-round-1/254000/26-512.png")));
        if (getButtons().size() == 1) {
            setContent("☆ §l§eKhông có cái gì để mua ");
        }
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        // If back button pressed
        if (!(getResponse().getClickedButton() instanceof CategoryButton)) {
            event.getPlayer().showFormWindow(new MenuScreen(event.getPlayer().getUniqueId()));
            return;
        }
        CategoryButton button = (CategoryButton)getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuyKitSelectorScreen(button.getCategoryId(),button.getCustomName()));
    }
}
