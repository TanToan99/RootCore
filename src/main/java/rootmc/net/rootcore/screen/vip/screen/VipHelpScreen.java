package rootmc.net.rootcore.screen.vip.screen;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.screen.vip.MenuVipScreen;

public class VipHelpScreen extends FormWindowSimple implements Screen {

    public VipHelpScreen() {
        super("§c§lRoot§r§lNetworκ §r- §aV§eI§cP", "");
        setContent("§l§fQuy định mua vip:\n\n" +
                "» §fCó 2 loại:§f VIP Vĩnh viễn và VIP 30 ngày\n\n" +
                "» §fVIP 30 ngày khi chuyển gói 30 mới sẽ mất gói 30 ngày cũ\n\n" +
                "» §fVip vĩnh viễn phải up theo bậc từ §6§lVIP §f-> §6§lVIP§c+ §f-> §b§lKING §f-> §b§lKING§c+\n\n" +
                "» §fNếu đang dùng gói vĩnh viên và mua thêm gói 30 ngày, sau 30 ngày sẽ về lại gói vĩnh viễn đã mua\n\n" +
                "» §fRank The King chỉ mua được khi bạn dùng KING+ Vĩnh Viễn và The King có các đặc quyền riêng biệt\n\n" +
                "» §fXem thông tin các gói đã mua ở mục thông tin §aV§eI§cP\n\n" +
                "» §fVí dụ: §fĐang dùng gói KING Vĩnh viễn và mua thêm KING+ 30 ngày thì sau 30 ngày sẽ về lại gói KING vĩnh viễn\n\n" +
                "» §cLưu ý :§f Mua các gói vip cũng là cách giúp server phát triền lâu dài và ổn định hơn !");
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
    }

    @Override
    public void onClose(Player player) {
        player.showFormWindow(new MenuVipScreen());
    }
}
