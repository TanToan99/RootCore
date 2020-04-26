package rootmc.net.rootcore.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class CreateAccountRPEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private String player;
    private int defaultRP;

    public CreateAccountRPEvent(String player, int defaultRP) {
        this.player = player;
        this.defaultRP = defaultRP;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }

    public String getPlayer() {
        return this.player;
    }

    public int getDefaultRP() {
        return this.defaultRP;
    }

    public void setDefaultRootPoint(int amount) {
        this.defaultRP = amount;
    }
}