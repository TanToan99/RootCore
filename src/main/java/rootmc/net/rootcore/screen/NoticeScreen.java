package rootmc.net.rootcore.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;

public class NoticeScreen extends FormWindowSimple implements Screen {
    public NoticeScreen(String title,String content) {
        super(title,content);
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {

    }

    @Override
    public void onClose(Player player) {

    }
}
