package rootmc.net.rootcore.screen.payment;

import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.TextFormat;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;

import java.util.*;

public class TopRootPoint extends FormWindowCustom implements Screen {


    public TopRootPoint(Player player, StringBuilder output) {
        super("§c§lRoot§r§lNetworκ §r- Top RootPoint");
        addElement(new ElementLabel(output.toString()));
        addElement(new ElementInput("§aNhập trang tiếp theo để coi", "Nhập số trang ở đây"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        String page = getResponse().getInputResponse(1);
        int page1 = Integer.parseInt(page);
        if (page1 > 0){
            RootCore.get().getRootPointManager().sendTopRootPoint(event.getPlayer(),page1);
        }
    }


}
