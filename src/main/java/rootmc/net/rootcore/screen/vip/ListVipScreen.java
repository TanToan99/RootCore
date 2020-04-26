package rootmc.net.rootcore.screen.vip;

import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.ConfigSection;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.element.RankButton;
import rootmc.net.rootcore.screen.MenuScreen;
import rootmc.net.rootcore.screen.Screen;

import java.util.UUID;

public class ListVipScreen  extends FormWindowSimple implements Screen {

    public ListVipScreen(UUID id) {
        super("§c§lRoot§r§lNetworκ Nâng Rank§r®", "  ☆ §l§eRPoint bạn có "+ RootCore.get().getRootPointManager().myRootPoint(id) +"RP");
        for (String key : RootCore.rankCfg.getKeys(false)) {
            ConfigSection cSection = RootCore.rankCfg.getSection(key);
            addButton(new RankButton(key,cSection.getString("customname"),cSection.getInt("day"),cSection.getInt("rp"),cSection.getString("des"),cSection.getString("url")));
        }
        addButton(new ElementButton("Trở về",new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,"https://cdn1.iconfinder.com/data/icons/social-messaging-ui-color-round-1/254000/26-512.png")));
        if (getButtons().size() == 1) {
            setContent("☆ §l§eKhông có cái gì để mua ");
        }
    }
    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        // If back button pressed
        if (!(getResponse().getClickedButton() instanceof RankButton)) {
            event.getPlayer().showFormWindow(new MenuScreen(event.getPlayer().getUniqueId()));
            return;
        }
        RankButton button = (RankButton)getResponse().getClickedButton();
        event.getPlayer().showFormWindow(new BuySelectRankScreen(RootCore.get().getRootPointManager().myRootPoint(event.getPlayer().getUniqueId()),button.getName()));
    }
}