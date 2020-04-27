package rootmc.net.rootcore.screen.info;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.info.screen.BasicCommand;
import rootmc.net.rootcore.screen.info.screen.GameType;
import rootmc.net.rootcore.screen.info.screen.Information;
import rootmc.net.rootcore.screen.payment.PaymentMethodScreen;

public class MenuInfo extends FormWindowSimple implements Screen {

    public MenuInfo() {
        super("§c§lRoot§r§lNetworκ §r® HELP","");
        addButton(new ElementButton("§f♢ §l§1Thông tin cá nhân §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Lối chơi §r§f♢"));
        addButton(new ElementButton("§f♢ §l§1Lệnh cơ bản §r§f♢"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        switch (clickedButtonId){
            case 0:
                event.getPlayer().showFormWindow(new Information(event.getPlayer()));
                break;
            case 1:
                event.getPlayer().showFormWindow(new GameType());
                break;
            case 2:
                event.getPlayer().showFormWindow(new BasicCommand());
                break;
        }
    }

    @Override
    public void onClose(Player player) {

    }
}
