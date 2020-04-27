package rootmc.net.rootcore.screen.vip.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;

public class BuyFailScreen extends FormWindowSimple implements Screen {

    private String key;

    public BuyFailScreen(String key) {
        super("Mua thất bại", "Bạn không có đủ rp để mua rank !");
        this.key = key;}

    public void onResponse(PlayerFormRespondedEvent event) {
    }

    @Override
    public void onClose(Player player) {
        int rp = RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId());
        player.showFormWindow(new ListVipScreen(rp));
    }
}
