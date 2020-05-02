package rootmc.net.rootcore.tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.utils.RequestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;

public class NapTheAsyncTask extends AsyncTask {

    List<String> data;
    String playerName;

    public NapTheAsyncTask(List<String> dataa, String playerN) {
        data = dataa;
        playerName = playerN;
    }

    @SneakyThrows
    @Override
    public void onRun() {
        String mang = data.get(0);
        String seri = data.get(3);
        String pincode = data.get(2);
        String cardType = getCardType(mang);
        String menhgia = data.get(1);
        String secure_code = "72031091a12b7bb9b2431c18b5b03a4a";
        //make key md5
        String plaintText = String.format("4563"+"tantoan1909@gmail.com"+time()+cardType+menhgia+pincode+seri+"md5"+secure_code);
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(plaintText.getBytes());
        String key = convertByteToHex1(messageDigest);
        //send request
        Map<String, String> fields = new HashMap<>();
        fields.put("merchant_id", "4563");
        fields.put("api_email", "tantoan1909@gmail.com");
        fields.put("trans_id", time());
        fields.put("card_id", cardType);
        fields.put("card_value", menhgia);
        fields.put("pin_field", pincode);
        fields.put("seri_field", seri);
        fields.put("algo_mode", "md5");
        fields.put("data_sign", key);
        String requests = RequestUtils.createRequests(fields);
        String get = RequestUtils.post("http://api.napthengay.com/v2/",requests);
        setResult(new String[]{get, menhgia,data.get(0),cardType,seri,pincode});
    }

    public String convertByteToHex1(byte[] data) {
        BigInteger number = new BigInteger(1, data);
        String hashtext = number.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    @Override
    public void onCompletion(Server server) {
        Player player = server.getPlayer(playerName);
        String[] R = (String[]) getResult();
        String result = R[0];
        if (result == null || result.equals("")) {
            player.sendMessage("§r[§l§4Root§r§lPoint§r]§4 Nạp lỗi, vui lòng thử lại. Liên hệ admin để được xử lý");
        } else {
            Reponse data = new Gson().fromJson(result, Reponse.class);
            if (data.code == 100) {
                player.sendMessage("§r[§l§4Root§r§lPoint§r] " + data.msg);
                server.broadcastMessage("§r[§l§4Root§r§lPoint§r] §aNgười chơi §f" + playerName + " §ađã nạp thành công thẻ cào §f" + R[1] + "VND. §aCám ơn bạn đã ủng hộ server !");
                int rp = RootCore.get().getConfig().getInt("discount") * Integer.parseInt(R[1]) / 100000;
                switch (R[3]){
                    case "4":
                    case "5":
                    case "6":
                        rp *= 1.2;
                        break;
                }
                RootCore.get().getRootPointManager().addRootPoint(player, rp);
                RootCore.get().getProvider().add_transaction(playerName, "TECOM_CARD", R[2] + " - Seri: " + R[4] + " - pin: " + R[5] + " - price: " + R[1], rp, RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId()));
            } else {
                player.sendMessage("§r[§l§4Root§r§lPoint§r] Nạp thất bại, vui lòng thử lại");
                player.sendMessage("§r[§l§4Root§r§lPoint§r] " + data.msg);
            }
        }
    }

    private String getCardType(String supplier) {
        switch (supplier) {
            case ("Viettel"):
                return "1";
            case ("Mobifone"):
                return "2";
            case ("Vinaphone"):
                return "3";
            case ("Zing"):
                return "4";
            case ("Gate(FPT)"):
                return "5";
            case ("VCoin"):
                return "6";
        }
        return null;
    }
}

class Reponse {
    public String amount;
    public int code;
    public String msg;
    public String trans_id;
}
