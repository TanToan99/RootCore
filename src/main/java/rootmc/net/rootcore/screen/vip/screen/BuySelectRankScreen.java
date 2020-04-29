package rootmc.net.rootcore.screen.vip.screen;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.data.RankData;
import rootmc.net.rootcore.module.VIPManager;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.vip.MenuVipScreen;

import java.util.Arrays;

public class BuySelectRankScreen extends FormWindowCustom implements Screen {

    public BuySelectRankScreen(String customName) { //Integer rp, todo: rewrite rp manager
        super(customName);
        RankData rankData = RootCore.get().getVipManager().getRankByCustomName(customName);
        addElement(new ElementLabel(rankData.getDes()));
        if (rankData.getRpvv() < 0) {
            addElement(new ElementStepSlider("◊ §l§eXác nhận§f", Arrays.asList("Không", "Mua 30 ngày")));
        } else {
            addElement(new ElementStepSlider("◊ §l§eXác nhận§f", Arrays.asList("Không", "Mua 30 ngày", "Mua vĩnh viễn")));
        }
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int idResponse = getResponse().getStepSliderResponse(1).getElementID();
        switch (idResponse) {
            case 0:
                player.showFormWindow(new ListVipScreen());
                break;
            case 1:
                VIPManager vipManagerr = RootCore.get().getVipManager();
                String customName = getTitle();
                RankData rankData = vipManagerr.getRankByCustomName(customName);
                if (RootCore.get().getRootPointManager().reduceRootPoint(player.getUniqueId(), rankData.getRp(), true) == 1) {
                    Server.getInstance().broadcastMessage("§r[§l§4Root§r] §aNgười chơi §f" + player.getName() + "§a đã mua rank " + rankData.getCusName() + " §a 30 ngày !");
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(), "lp user " + player.getName() + " clear");
                    if (vipManagerr.hasRankF(player.getName())) {
                        Server.getInstance().dispatchCommand(new ConsoleCommandSender(), "lp user " + player.getName() + " parent set " + vipManagerr.getrankF(player.getName()));
                    }
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(), "lp user " + player.getName() + " parent addtemp " + rankData.getKey() + " " + rankData.getDay() + "d");
                    RootCore.get().getProvider().add_transaction(player.getName(), "SB_RANK", rankData.getKey() + " - " + rankData.getDay(), rankData.getRp(), RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId()));
                } else {
                    player.showFormWindow(new BuyFailScreen("Bạn không có đủ rp để mua rank, nạp thẻ bằng lệnh /rp và thử lại!"));
                }
                break;
            case 2:
                String customNamea = getTitle();
                VIPManager vipManager = RootCore.get().getVipManager();
                RankData rankDataa = vipManager.getRankByCustomName(customNamea);
                if (!vipManager.canBuyLevel(player.getName(), rankDataa.getLevel())) {
                    player.showFormWindow(new BuyFailScreen("Bạn cần phải mua từ rank Vĩnh Viễn : §6§lVIP §f-> §6§lVIP§c+ §f-> §b§lKING §f-> §b§lKING§c+ theo thứ tự !"));
                    return;
                }
                if (RootCore.get().getRootPointManager().reduceRootPoint(player.getUniqueId(), rankDataa.getRpvv(), true) == 1) {
                    vipManager.writeData(player.getName(), rankDataa.getLevel()); //if error -> dont ask me :))
                    Server.getInstance().broadcastMessage("§r[§l§4Root§r] §aNgười chơi §f" + player.getName() + "§a đã mua rank " + rankDataa.getCusName() + " §a Vĩnh Viễn !");
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(), "lp user " + player.getName() + " clear");
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(), "lp user " + player.getName() + " parent set " + rankDataa.getKey());
                    RootCore.get().getProvider().add_transaction(player.getName(), "SB_RANK", rankDataa.getKey() + " - VV", rankDataa.getRpvv(), RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId()));
                } else {
                    player.showFormWindow(new BuyFailScreen("Bạn không có đủ rp để mua rank, nạp thẻ bằng lệnh /rp và thử lại!"));
                }
                break;
        }
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuVipScreen());
    }
}
