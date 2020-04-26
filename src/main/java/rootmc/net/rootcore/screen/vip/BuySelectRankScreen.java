package rootmc.net.rootcore.screen.vip;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.ConfigSection;
import net.luckperms.api.LuckPermsProvider;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.module.RootPointManager;
import rootmc.net.rootcore.screen.Screen;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class BuySelectRankScreen extends FormWindowCustom implements Screen {

    private String key;

    public BuySelectRankScreen(Integer rp, String key) {
        super(RootCore.rankCfg.getSection(key).getString("customname"));
        addElement(new ElementLabel("§lRPoint bạn có §f"+ rp + "RP\n" + RootCore.rankCfg.getSection(key).getString("des")));
        if (RootCore.rankCfg.getSection(key).getInt("rpvv") == -1){
            addElement(new ElementStepSlider("◊ §l§eXác nhận", Arrays.asList("Không", "Mua 30 ngày")));
        }else{
            addElement(new ElementStepSlider("◊ §l§eXác nhận", Arrays.asList("Không", "Mua 30 ngày", "Mua vĩnh viễn")));
        }
        this.key = key;
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        int idResponse = getResponse().getStepSliderResponse(1).getElementID();
        switch (idResponse){
            case 0:
                player.showFormWindow(new ListVipScreen(event.getPlayer().getUniqueId()));
                break;
            case 1:
                UUID uuid = player.getUniqueId();
                ConfigSection cSection = RootCore.rankCfg.getSection(key);
                int rp =  cSection.getInt("rp");
                if (RootCore.get().getRootPointManager().reduceRootPoint(uuid, rp, true) == RootPointManager.RET_SUCCESS) {
                    player.sendMessage("Mua rank thành công ");
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(),"lp user "+ player.getName()+" clear");
                    Server.getInstance().dispatchCommand(new ConsoleCommandSender(),"lp user "+ player.getName()+" parent addtemp "+key+" "+cSection.getInt("day")+ "d");
                    RootCore.get().getProvider().add_transaction(player.getName(),"SB_RANK",key +" - "+ cSection.getInt("day"), rp, RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId()));
                } else {
                    player.showFormWindow(new BuyFailScreen(key));
                }
                break;
            case 2:
                player.sendMessage("Tạm thời muốn mua rank vĩnh viễn liên hệ Admin Discord hoặc Fanpage");
                break;
        }
    }
}
