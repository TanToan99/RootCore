package rootmc.net.rootcore.screen;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.RootCore;

public class EventScreen  extends FormWindowCustom implements Screen {
    public EventScreen() {
        super(RootCore.get().getConfig().getString("eventscreen.title"));
        addElement(new ElementLabel(RootCore.get().getConfig().getString("eventscreen.des")));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {

    }
}
