package rootmc.net.rootcore.screen.payment;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.screen.Screen;

public class CKPaymentInfoScreen extends FormWindowSimple implements Screen {
    public CKPaymentInfoScreen() {
        super("§c§lRoot§r§lNetworκ §r- Payment Info", "࿀ §bChuyển khoản Momo §r\n\n" +
                "§bSDT:§f 0963523490 - Lê Ngọc Tấn Toàn – §r\n\n" +
                "§bNội dung:§f NAP_<Tên Ingame> §r\n\n" +
                "࿀ §bChuyển khoản Vietinbank §r\n\n" +
                "§bSố Tài khoản:§f 102001644129 - Lê Ngọc Tấn Toàn§r\n\n"+
                "§bNội dung:§f NAP_<Tên Ingame> §r\n\n" +
                "࿀§b Ghi chú: §r\n\n" +
                "§b- §fBạn chỉ cần ra cửa hàng di động bất kỳ FPTShop, Viễn Thông A, Viettel Store,...nói nạp vào SDT là đơn giản nhất rồi nha :)§r\n\n" +
                "§b- §fSố RP sẽ được chốt và chuyển khoản ngay khi bạn nạp vào tài khoản trong ngày và nhanh nhất§r\n\n" +
                "§b- §fLiên hệ Fanpage để biết thêm các cách thức nạp");
        addButton(new ElementButton("Trở về"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        if (clickedButtonId == 0) {
            event.getPlayer().showFormWindow(new PaymentMethodScreen());
        }
    }

    @Override
    public void onClose(Player player) {

    }
}
