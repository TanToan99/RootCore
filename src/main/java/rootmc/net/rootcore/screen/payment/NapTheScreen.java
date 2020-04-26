package rootmc.net.rootcore.screen.payment;

import cn.nukkit.Server;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import rootmc.net.rootcore.screen.Screen;
import rootmc.net.rootcore.tasks.NapTheAsyncTask;

import java.util.Arrays;
import java.util.List;

public class NapTheScreen extends FormWindowCustom implements Screen {


    public NapTheScreen() {
        super("§c§lRoot§r§lNetworκ §r- Nạp Thẻ Cào");
        addElement(new ElementLabel(" §f» §eBạn có §f 0  §eRootPoint \n\n" +
                " §f» §eQuy đổi: \n\n" +
                "   §f❀ §e10k  §f= 10 §eRP \n\n" +
                "   §f❀ §e20k  §f= 20 §eRP \n\n" +
                "   §f❀ §e50k  §f= 50 §eRP\n\n" +
                "   §f❀ §e100k §f= 100 §eRP\n\n" +
                "   §f❀ §e200k §f= 200 §eRP\n\n" +
                "   §f❀ §e500k §f= 500 §eRP\n\n" +
                " §f» §eLưu ý:§f Chọn sai mệnh giá có thể mất thẻ\n"));
        addElement(new ElementDropdown(" » §eChọn nhà mạng", Arrays.asList("VIETTEL", "MOBIPHONE", "VINAPHONE", "GATE")));
        addElement(new ElementDropdown(" » §eChọn mệnh giá", Arrays.asList("10000", "20000", "50000", "100000", "200000", "500000")));
        addElement(new ElementInput(" » §eNhập Seri", "Nhập số seri ở đây"));
        addElement(new ElementInput(" » §eNhập Pin", "Nhập số Pin ở đây"));
    }

    @Override
    public void onResponse(PlayerFormRespondedEvent event) {
        String seri = getResponse().getInputResponse(3);
        String pin = getResponse().getInputResponse(4);
        String nhamang = getResponse().getDropdownResponse(1).getElementContent();
        String menhgia = getResponse().getDropdownResponse(2).getElementContent();
        if (seri.equals("") || pin.equals("") || !isNumeric(seri) || !isNumeric(pin)) {
            event.getPlayer().showFormWindow(new NapTheScreen());
            return;
        }
        List<String> data = Arrays.asList(nhamang, menhgia, pin, seri);
        event.getPlayer().sendMessage("§r[§l§4Root§r§lPoint§r] Đang xử lý thẻ, vui lòng đợi trong giây lát");
        Server.getInstance().getScheduler().scheduleAsyncTask(new NapTheAsyncTask(data, event.getPlayer().getName()));
    }

    public static boolean isNumeric(final Object str) {
        if (!(str instanceof String)) {
            return str instanceof Integer;
        }
        String intnum = (String) str;
        for (int sz = intnum.length(), i = 0; i < sz; ++i) {
            if (!Character.isDigit(intnum.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
