package rootmc.net.rootcore.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class AddRootPointEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private String player;
    private int amount;

    public AddRootPointEvent(String player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }

    public String getPlayer() {
        return this.player;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}