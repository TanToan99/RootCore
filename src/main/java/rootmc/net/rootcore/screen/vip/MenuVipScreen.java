package rootmc.net.rootcore.screen.vip;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.info.screen.GameType;
import rootmc.net.rootcore.screen.payment.PaymentMethodScreen;
import rootmc.net.rootcore.screen.vip.screen.VipHelpScreen;

public class MenuVipScreen extends FormWindowSimple implements Screen {

    public MenuVipScreen() {
        super("§c§lRoot§r§lNetworκ §r® §aV§eI§cP","");
        addButton(new ElementButton("§f♢ §l§1Hướng dẫn §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Thông Tin VIP §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Các Gói VIP §r§f♢"));
    }

    public MenuVipScreen(String title, String content) {
        super(title, content);
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        switch (clickedButtonId){
            case 0:
                event.getPlayer().showFormWindow(new VipHelpScreen());
                break;
            case 1:
                event.getPlayer().showFormWindow(new GameType());
                break;
            case 2:
                event.getPlayer().showFormWindow(new PaymentMethodScreen());
                break;
        }
    }

    @Override
    public void onClose(Player player) {

    }
}
