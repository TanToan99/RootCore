package rootmc.net.rootcore.screen.shop;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;

public class BuySuccessScreen extends FormWindowSimple implements Screen {

    String categoryId;

    public BuySuccessScreen() {
        super("Mua thành công ","Bạn đã mua thành công !");
        addButton(new ElementButton("Trở về",new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,"https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color-round-1/254000/26-512.png")));
    }

    public void onResponse(PlayerFormRespondedEvent event) {
        event.getPlayer().showFormWindow(new BuyCategoriesScreen(RootCore.get().getRootPointManager().myRootPoint(event.getPlayer().getUniqueId())));
    }
}
