package rootmc.net.rootcore.screen;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.RootCore;

public class JoinScreen extends FormWindowCustom implements Screen {
    public JoinScreen() {
        super(RootCore.get().getConfig().getString("joinscreen.title"));
        addElement(new ElementLabel(RootCore.get().getConfig().getString("joinscreen.des")));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {

    }
}
