package rootmc.net.rootcore;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.ItemTool;
import cn.nukkit.network.protocol.LoginPacket;
import cn.nukkit.scheduler.Task;
import me.onebone.economyapi.EconomyAPI;
import rootmc.net.rootcore.screen.NoticeScreen;
import rootmc.net.rootcore.screen.info.screen.GameType;
import rootmc.net.rootcore.screen.Screen;

import java.util.Map;
import java.util.UUID;

public class RootPointListener implements Listener {

    private RootCore plugin;
    
    RootPointListener(RootCore plugin){
        this.plugin = plugin;
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (!(event.getWindow() instanceof Screen)) return;
        if (event.wasClosed()){
            ((Screen)event.getWindow()).onClose(event.getPlayer());
            return;
        }
        if (event.getResponse() == null) return;
        ((Screen)event.getWindow()).onResponse(event);
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }

    @EventHandler
    public void action(DataPacketReceiveEvent e) {
        if (e.getPacket() instanceof LoginPacket) {
            ((LoginPacket)e.getPacket()).username = ((LoginPacket)e.getPacket()).username.replaceAll(" ", "_");
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        plugin.getServer().getScheduler().scheduleTask(new Task() {
            @Override
            public void onRun(int i) {
                plugin.getRootPointManager().createAccount(event.getPlayer());
            }
        });
        plugin.getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                event.getPlayer().showFormWindow(new NoticeScreen("§c§lRoot§r§lNetworκ §r® Update",RootCore.get().getConfig().getString("update")));
            }
        }, 80);
    }

    //fix and me commmand
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event){
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        String[] args = event.getMessage().split(" ");
        switch (args[0]){
            case "/fix":
                if (plugin.getConfig().getBoolean("fix",false)) {
                    if (args.length != 2) {
                        player.sendMessage("◊ §cBạn phải sử dụng đúng lệnh §f/fix <hand|all> ");
                        player.sendMessage("◊ §cSố tiền phí /fix sẽ tùy thuộc vào rank của bạn ");
                        return;
                    }
                    switch (args[1]) {
                        case "all":
                            int count = 0;
                            int coinn = -1;
                            Map<Integer, Item> contents = player.getInventory().getContents();
                            for (Item item : contents.values()) {
                                if (isRepairable(item)) {
                                    if (item.getDamage() != 0)
                                        count++;
                                }
                            }
                            Item[] armors = player.getInventory().getArmorContents();
                            for (Item item : armors) {
                                if (isRepairable(item)) {
                                    if (item.getDamage() != 0)
                                        count++;
                                }
                            }
                            //todo: edit price fix
                            if (player.hasPermission("rootcore.rank.king+")) {
                                coinn = 0;
                            } else if (player.hasPermission("rootcore.rank.king")) {
                                coinn = Math.min(5000, 100 * count);
                            } else if (player.hasPermission("rootcore.rank.vip+")) {
                                coinn = Math.min(9000, 300 * count);
                            } else if (player.hasPermission("rootcore.rank.vip")) {
                                coinn = 500 * count;
                            } else {
                                player.sendMessage("◊ §eBạn phải là VIP để sử dụng lệnh §f/fix <hand|all> §e");
                                return;
                            }
                            if (count == 0) {
                                player.sendMessage("◊ §aTúi đồ của bạn không cần fix !");
                                return;
                            }
                            if (EconomyAPI.getInstance().reduceMoney(player, coinn) == EconomyAPI.RET_SUCCESS) {
                                for (Item item : contents.values()) {
                                    if (isRepairable(item)) {
                                        item.setDamage(0);
                                    }
                                }
                                for (Item item : armors) {
                                    if (isRepairable(item)) {
                                        item.setDamage(0);
                                    }
                                }
                                player.getInventory().setContents(contents);
                                player.getInventory().setArmorContents(armors);
                                player.sendMessage("◊ §aBạn đã sửa thành công toàn bộ túi đồ với giá §f" + coinn + "coin");
                            } else {
                                player.sendMessage("◊ §cBạn không đủ " + coinn + "coin để sử dụng lệnh /fix hand !");
                            }
                            break;
                        case "hand":
                            Item item = player.getInventory().getItemInHand();
                            if (!isRepairable(item)) {
                                player.sendMessage("◊ §eVật phẩm bạn cầm không khả dụng để /fix, cầm trên tay và thử lại ");
                                return;
                            }
                            int coin = -1;
                            if (player.hasPermission("rootcore.rank.king+")) {
                                coin = 0;
                            } else if (player.hasPermission("rootcore.rank.king")) {
                                coin = 100;
                            } else if (player.hasPermission("rootcore.rank.vip+")) {
                                coin = 300;
                            } else if (player.hasPermission("rootcore.rank.vip")) {
                                coin = 500;
                            } else {
                                player.sendMessage("◊ §eBạn phải là VIP để sử dụng lệnh §f/fix <hand|all> §e");
                                return;
                            }
                            if (item.getDamage() == 0) {
                                player.sendMessage("◊ §aItem §f" + item.getCustomName() + " §a bạn cầm không cần fix !");
                                return;
                            }
                            if (EconomyAPI.getInstance().reduceMoney(player, coin) == EconomyAPI.RET_SUCCESS) {
                                item.setDamage(0);
                                player.getInventory().setItemInHand(item);
                                player.sendMessage("◊ §eBạn đã sửa thành công §f" + item.getCustomName() + " §evới giá §f" + coin + "coin  !");
                            } else {
                                player.sendMessage("◊ §cBạn không đủ " + coin + "coin để sử dụng lệnh /fix hand !");
                            }
                            break;
                        default:
                            player.sendMessage("◊ §cBạn phải sử dụng đúng lệnh §f/fix <hand|all> ");
                            player.sendMessage("◊ §cSố tiền phí /fix sẽ tùy thuộc vào rank của bạn ");
                            return;
                    }
                }else{
                    Item item = player.getInventory().getItemInHand();
                    if (!isRepairable(item)) {
                        player.sendMessage("◊ §eVật phẩm bạn cầm không khả dụng để /fix, cầm trên tay và thử lại ");
                        return;
                    }
                    if (EconomyAPI.getInstance().reduceMoney(player, 500) == EconomyAPI.RET_SUCCESS) {
                        item.setDamage(0);
                        player.getInventory().setItemInHand(item);
                        player.sendMessage("◊ §eBạn đã sửa thành công §f" + item.getCustomName() + " §evới giá §f" + 500 + "coin  !");
                    } else {
                        player.sendMessage("◊ §cBạn không đủ " + 500 + "coin để sử dụng lệnh /fix hand !");
                    }
                }
                event.setCancelled();
                break;
            case "/me":
                if (player.hasPermission("nukkit.command.me") && !player.isOp()) {
                    UUID uuid = player.getUniqueId();
                    long current = System.currentTimeMillis();
                    if(plugin.commandMeCache.containsKey(uuid)){
                        long end = plugin.commandMeCache.get(player.getUniqueId());
                        if (current >= end){
                            plugin.commandMeCache.remove(player.getUniqueId());
                        }else{
                            event.setCancelled();
                            player.sendMessage("◊ §cBạn còn "+ (end - current) / 1000 +" giây nữa để sử dụng lại lệnh này, rank càng cao thời gian càng thấp !");
                        }
                    }
                    int time = 1;
                    if (player.hasPermission("rootcore.rank.king+")){
                        time = 10 * 60;
                    }else if (player.hasPermission("rootcore.rank.king")){
                        time = 20 * 60;
                    }else if (player.hasPermission("rootcore.rank.vip+")){
                        time = 30 * 60;
                    }else{
                        player.sendMessage("◊ §cBạn phải ở rank vip+ trở lên mới sử dụng được lệnh này ");
                        return;
                    }
                    plugin.commandMeCache.put(player.getUniqueId(), current + time * 1000L);
                }
                break;
        }
    }

    public boolean isRepairable(Item item) {
        return item instanceof ItemTool || item instanceof ItemArmor;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onchat(PlayerChatEvent event){
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if(player.hasPermission("rootcore.rank.king") || player.hasPermission("rootcore.rank.king+") || player.hasPermission("rootcore.rank.vip+") || player.hasPermission("rootcore.rank.vip")){
            String itemName = player.getInventory().getItemInHand().getName();
            event.setMessage(event.getMessage().replace("@xem","§b§l"+ itemName+"§r§f"));
        }
    }
    //TODO: rewrite full slot vip join
}
