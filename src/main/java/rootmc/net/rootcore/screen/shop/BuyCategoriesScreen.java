package rootmc.net.rootcore.screen.shop;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.element.CategoryButton;
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
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        CategoryButton button = (CategoryButton)getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuyKitSelectorScreen(button.getCategoryId(),button.getCustomName()));
    }

    @Override
    public void onClose(Player player) {

    }
}
