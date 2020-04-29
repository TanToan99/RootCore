package rootmc.net.rootcore.screen.info.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.info.MenuInfo;

public class BasicCommand  extends FormWindowSimple implements Screen {

    public BasicCommand() {
        super("§c§lRoot§r§lNetworκ §r® Lệnh cơ bản",RootCore.get().getConfig().getString("basicCommand"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuInfo());
    }
}