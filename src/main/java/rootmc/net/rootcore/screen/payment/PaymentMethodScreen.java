package rootmc.net.rootcore.screen.payment;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import rootmc.net.rootcore.screen.Screen;

public class PaymentMethodScreen extends FormWindowSimple implements Screen {

    public PaymentMethodScreen() {
        super("§c§lRoot§r§lNetworκ §r- Payment", "» §l§eChọn cách nạp RPoint. \n" +
                "§f» §l§eThẻ cào thì tiện lợi nhưng nạp chuyển khoản thì lợi hơn nhiều nha :)");
        addButton(new ElementButton("§l§2Nạp thẻ cào"));
        addButton(new ElementButton("§l§2Nạp chuyển khoản §4(x1.5)"));
        addButton(new ElementButton("Trở lại"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        int clickedButtonId = getResponse().getClickedButtonId();
        if (clickedButtonId == 0) {
            event.getPlayer().showFormWindow(new NapTheScreen());
        } else if (clickedButtonId == 1) {
            event.getPlayer().showFormWindow(new CKPaymentInfoScreen());
        } else {
           // event.getPlayer().showFormWindow(new MenuScreen(event.getPlayer().getUniqueId()));
        }
    }

    @Override
    public void onClose(Player player) {

    }
}
