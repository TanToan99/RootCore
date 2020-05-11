package rootmc.net.rootcore.event;

import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;

public class ReduceRootPointEvent extends Event implements Cancellable {
    public static HandlerList handlerList = new HandlerList();

    private String playerName;
    private int amount;

    public ReduceRootPointEvent(String playerName, int amount) {
        this.playerName = playerName;
        this.amount = amount;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}