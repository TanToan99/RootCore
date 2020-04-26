package rootmc.net.rootcore.screen.information;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.screen.Screen;

public class ChangePassLV2Screen extends FormWindowCustom implements Screen {

    public ChangePassLV2Screen(Player player) {
        super("Đổi mật khẩu LV2");
        addElement(new ElementLabel("» §bMật khẩu cấp 2 dùng để sử dụng ở các chức năng nâng cao\n\n" +
                "§r» §bChú ý: §fÍt nhất 8 ký tự, có phân biệt hoa thường"));
        addElement(new ElementInput(" ☆ §bPass", "Nhập pass ở đây"));
        addElement(new ElementInput(" ☆ §bNhập lại", "Nhập lại pass ở đây"));
    }

    public ChangePassLV2Screen(Player player,String message) {
        super("Đổi mật khẩu LV2");
        addElement(new ElementLabel("Bạn đã nhập dưới 8 ký tự, vui lòng thử lại \nChú ý: Ít nhất 8 ký tự, có phân biệt hoa thường"));
        addElement(new ElementInput(" ☆ §bPass", "Nhập pass ở đây"));
        addElement(new ElementInput(" ☆ §bNhập lại", "Nhập lại pass ở đây"));
    }
    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        String pass = getResponse().getInputResponse(1);
        String passrewrite = getResponse().getInputResponse(2);
        if (pass.equals(passrewrite) && pass.length() > 7){
            if(RootCore.get().getProvider().setPassLv2(event.getPlayer().getUniqueId().toString(),pass)){
                event.getPlayer().sendMessage("§r[§l§4Root§r] §aĐổi mật khẩu thành công !");
            }else{
                event.getPlayer().sendMessage("§r[§l§4Root§r] §aĐổi mật khẩu thất bại, lỗi hệ thống xxx !");
            }
        }else{
            event.getPlayer().showFormWindow(new ChangePassLV2Screen(event.getPlayer()));
        }
    }
}
