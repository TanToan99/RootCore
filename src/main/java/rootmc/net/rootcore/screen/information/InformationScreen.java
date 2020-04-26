package rootmc.net.rootcore.screen.information;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import me.onebone.economyapi.EconomyAPI;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.MenuScreen;
import rootmc.net.rootcore.screen.Screen;

public class InformationScreen extends FormWindowSimple implements Screen {

    public InformationScreen(Player player) {
        super("§c§lRoot§r§lNetworκ §r- Information",
                "» §bTên Tài Khoản  §f: " + player.getName()+"\n\n" +
                "» §bRootPoint      §f: " + RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId())+"RP\n\n" +
                "» §bCoin           §f: "+ EconomyAPI.getInstance().myMoney(player)+"\n\n"+
                "» §bMật khẩu cấp 2 §f: " + RootCore.get().getProvider().getPassLv2(player.getUniqueId().toString())+"\n\n" +
                "» §bChú ý          §f: Mật khẩu cấp 2 để xác thực tính năng mới trong ứng dụng, vui lòng không chia sẽ cho ai");
        addButton(new ElementButton("Đổi mật khẩu C2"));
        addButton(new ElementButton("Trở về"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        if (clickedButtonId == 0) {
            event.getPlayer().showFormWindow(new ChangePassLV2Screen(event.getPlayer()));
        }else{
            event.getPlayer().showFormWindow(new MenuScreen(event.getPlayer().getUniqueId()));
        }
    }
}
