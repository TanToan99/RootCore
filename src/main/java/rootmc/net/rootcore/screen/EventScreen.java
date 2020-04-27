package rootmc.net.rootcore.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.RootCore;

public class EventScreen  extends FormWindowCustom implements Screen {
    public EventScreen() {
        super("§c§lRoot§r§lNetworκ §r® Sự kiện");
        addElement(new ElementLabel(RootCore.get().getConfig().getString("events")));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {

    }

    @Override
    public void onClose(Player player) {

    }
}
