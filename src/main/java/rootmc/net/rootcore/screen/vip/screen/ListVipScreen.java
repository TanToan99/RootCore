package rootmc.net.rootcore.screen.vip.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.vip.MenuVipScreen;

public class ListVipScreen extends FormWindowSimple implements Screen {

    public ListVipScreen() { //int rp
        super("§c§lRoot§r§lNetworκ - Nâng Rank§r®",""); //, "  ☆ §l§eRPoint :" + rp + "RP" todo: rewrite rp manager
        RootCore.get().getVipManager().getrankDatas().forEach(i -> {
            addButton(new ElementButton(i.getCusName(),new ElementButtonImageData("url",i.getUrl())));
        });
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new BuySelectRankScreen(getResponse().getClickedButton().getText()));
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuVipScreen());
    }
}