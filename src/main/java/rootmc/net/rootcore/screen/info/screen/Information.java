package rootmc.net.rootcore.screen.info.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import me.onebone.economyapi.EconomyAPI;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.info.MenuInfo;

public class Information extends FormWindowSimple implements Screen {

    public Information(Player player) {
        super("§c§lRoot§r§lNetworκ §r- Information",
                "» §bTên Tài Khoản  §f: " + player.getName()+"\n\n" +
                "» §bRootPoint      §f: " + RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId())+"RP\n\n" +
                "» §bCoin           §f: "+ EconomyAPI.getInstance().myMoney(player)+"\n\n"+
                "» §bMật khẩu cấp 2 §f: " + RootCore.get().getProvider().getPassLv2(player.getUniqueId())+"\n\n" +
                "» §bChú ý          §f: Mật khẩu cấp 2 để xác thực tính năng mới trong ứng dụng android của server, vui lòng không chia sẽ cho ai");
        addButton(new ElementButton("Đổi mật khẩu C2"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new ChangePassLV2());
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuInfo());
    }
}
