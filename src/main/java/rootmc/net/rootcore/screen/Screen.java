package rootmc.net.rootcore.screen;

import cn.nukkit.event.player.PlayerFormRespondedEvent;

public interface Screen {

    void onResponse(PlayerFormRespondedEvent event);
}
