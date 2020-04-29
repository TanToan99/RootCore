package rootmc.net.rootcore.screen.vip;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.vip.screen.ListVipScreen;
import rootmc.net.rootcore.screen.vip.screen.VipHelpScreen;

public class MenuVipScreen extends FormWindowSimple implements Screen {

    public MenuVipScreen() {
        super("§c§lRoot§r§lNetworκ §r® §aV§eI§cP", "");
        addButton(new ElementButton("§f♢ §l§1Hướng dẫn §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Thông Tin VIP §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Các Gói VIP §r§f♢"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        Player player = event.getPlayer();
        switch (clickedButtonId) {
            case 0:
                player.showFormWindow(new VipHelpScreen());
                break;
            case 1: //todo: query with luckperm database =))
                player.showFormWindow(this);
                break;
            case 2:
                player.showFormWindow(new ListVipScreen());
                break;
        }
    }

    @Override
    public void onClose(Player player) {

    }
}
