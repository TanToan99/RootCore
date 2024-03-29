package rootmc.net.rootcore.screen.info.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.info.MenuInfo;

public class GameType extends FormWindowSimple implements Screen {
    public GameType() {
        super("§c§lRoot§r§lNetworκ §r® Lối chơi",RootCore.get().getConfig().getString("gameType"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuInfo());
    }
}
