package rootmc.net.rootcore.screen;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.information.InformationScreen;
import rootmc.net.rootcore.screen.payment.PaymentMethodScreen;
import rootmc.net.rootcore.screen.shop.BuyCategoriesScreen;
import rootmc.net.rootcore.screen.vip.ListVipScreen;

import java.util.UUID;

public class MenuScreen extends FormWindowSimple implements Screen {

    public MenuScreen(UUID id) {
        super("§c§lRoot§r§lNetworκ §r®", "♢ §l§e Your RPoint:§f"+ RootCore.get().getRootPointManager().myRootPoint(id) +" RP");
        addButton(new ElementButton("§f♢ §l§1Thông Tin Tài Khoản §r§f♢",new ElementButtonImageData("url","https://cdn.iconscout.com/icon/premium/png-256-thumb/info-2074305-1763167.png")));
        addButton(new ElementButton("§f♢ §l§1Nạp RPoint §r§f♢",new ElementButtonImageData("url","https://cdn.iconscout.com/icon/premium/png-256-thumb/money-bag-115-565103.png")));
        addButton(new ElementButton("§f♢ §l§1Đua Top RPoint §r§f♢",new ElementButtonImageData("url","https://d1nhio0ox7pgb.cloudfront.net/_img/o_collection_png/green_dark_grey/256x256/plain/podium.png")));
        addButton(new ElementButton("§f♢ §l§1Cửa hàng RPoint §r§f♢",new ElementButtonImageData("url","https://i.ya-webdesign.com/images/store-icon-png-6.png")));
        addButton(new ElementButton("§f♢ §l§1Nâng cấp Rank VIP §r§f♢",new ElementButtonImageData("url","https://exp.gg/vn/wp-content/uploads/2018/08/challenger-rewards-lol.png")));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        switch (clickedButtonId){
            case 0:
                event.getPlayer().showFormWindow(new InformationScreen(event.getPlayer()));
                break;
            case 1:
                event.getPlayer().showFormWindow(new PaymentMethodScreen());
                break;
            case 2:
                RootCore.get().getRootPointManager().sendTopRootPoint(event.getPlayer(),0);
                break;
            case 3:
                event.getPlayer().showFormWindow(new BuyCategoriesScreen(RootCore.get().getRootPointManager().myRootPoint(event.getPlayer().getUniqueId(), event.getPlayer().getName())));
                break;
            case 4:
                event.getPlayer().showFormWindow(new ListVipScreen(event.getPlayer().getUniqueId()));
        }
    }
}
