package rootmc.net.rootcore.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;

public interface Screen {

    void onResponse(PlayerFormRespondedEvent event);

    void onClose(Player player);
}
