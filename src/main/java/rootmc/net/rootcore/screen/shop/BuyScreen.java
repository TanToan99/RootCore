package rootmc.net.rootcore.screen.shop;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import com.fleynaro.advancedkits.Kit;
import com.fleynaro.advancedkits.Main;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.module.RootPointManager;
import rootmc.net.rootcore.screen.Screen;

import java.util.Arrays;

public class BuyScreen extends FormWindowCustom implements Screen {

    private int price;
    private String key;

    public BuyScreen(String key, String customName, int price, int pmoney, String des) {
        super(customName);
        this.price = price;
        this.key = key;
        addElement(new ElementLabel("◊ §l§eRPoint của bạn: §f" + pmoney + "RP \n"));
        addElement(new ElementLabel("◊ §l§eTên : §f" + key + "\n"));
        addElement(new ElementLabel("◊ §l§eGiá : §f" + price + "RPoint/1 \n"));
        addElement(new ElementLabel("◊ §l§eMô Tả: " + des));
        addElement(new ElementStepSlider("◊ §l§eXác nhận:", Arrays.asList("Không", "Mua")));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int prp = RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId());
        if (getResponse().getStepSliderResponse(4).getElementID() == 0) {
            player.showFormWindow(new BuyCategoriesScreen(prp));
        } else {
            if (RootCore.get().getRootPointManager().reduceRootPoint(player, price) == RootPointManager.RET_SUCCESS) {
                Kit kit = Main.instance.getKit(key);
                kit.addTo(player);
                player.showFormWindow(new BuySuccessScreen());
                RootCore.get().getProvider().add_transaction(player.getName(),"SB_SHOPRP","KIT: " + key, price, prp - price);
            } else {
                player.showFormWindow(new BuyFailScreen());
            }
        }
    }
}
