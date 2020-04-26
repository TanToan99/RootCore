package rootmc.net.rootcore.screen.vip;

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
        this.key = key;
        addButton(new ElementButton("Trở về",new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,"https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color-round-1/254000/26-512.png")));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new ListVipScreen(event.getPlayer().getUniqueId()));
    }
}
