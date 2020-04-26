package rootmc.net.rootcore.tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import com.google.gson.Gson;
import rootmc.net.rootcore.RootCore;
import rootmc.net.rootcore.utils.RequestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NapTheAsyncTask extends AsyncTask {

    List<String> data;
    String playerName;

    public NapTheAsyncTask(List<String> dataa, String playerN) {
        data = dataa;
        playerName = playerN;
    }
    @Override
    public void onRun() {
        String supplier = data.get(0);
        String seri = data.get(3);
        String pincode = data.get(2);
        String mang = getCardType(data.get(0));
        String menhgia = data.get(1);
        if(mang==null) return;
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("merchant_id","50511");
        fields.put("api_user","5a8fb84a3cefe");
        fields.put("api_password", "f4d836dd40780f1e941ae2ff66a0869d");
        fields.put("card_type",mang);
        fields.put("price_guest", menhgia);
        fields.put("seri", seri);
        fields.put("pin", pincode);
        fields.put("note", playerName);
        String requests = RequestUtils.createRequests(fields);
        String get = RequestUtils.get("https://sv.gamebank.vn/api/card2?" + requests);
        setResult(new String[]{get, menhgia,data.get(0)});
    }


    @Override
    public void onCompletion(Server server) {
        Player player = server.getPlayer(playerName);
        String[] R = (String[]) getResult();
        String result = R[0];
        if (player != null){
            if(result==null || result.equals("")){
                player.sendMessage("§r[§l§4Root§r§lPoint§r]§4 Nạp lỗi, vui lòng thử lại. Liên hệ admin để được xử lý");
            }else{
                Reponse data = new Gson().fromJson(result,Reponse.class);
                if (data.code == 0 && data.info_card >= 10000){
                    player.sendMessage("§r[§l§4Root§r§lPoint§r] " + data.msg);
                    server.broadcastMessage("§r[§l§4Root§r§lPoint§r] §aNgười chơi §f"+playerName+" §ađã nạp thành công thẻ cào §f"+R[1]+"VND. §aCám ơn bạn đã ủng hộ server !");
                    int rp = RootCore.get().getConfig().getInt("discount") * Integer.parseInt(R[1]) / 100000;
                    RootCore.get().getRootPointManager().addRootPoint(player, rp);
                    RootCore.get().getProvider().add_transaction(playerName,"TECOM_CARD",R[2] + " - Seri: "+data.seri +" - pin: "+data.pin+" - price: "+R[1], rp, RootCore.get().getRootPointManager().myRootPoint(player.getUniqueId()));
                }else{
                    player.sendMessage("§r[§l§4Root§r§lPoint§r] Nạp thất bại, vui lòng thử lại");
                    player.sendMessage("§r[§l§4Root§r§lPoint§r] " + data.msg);
                }
            }
        }/*else{
            server.broadcastMessage("§r[§l§4Root§r§lPoint§r] §aNgười chơi §f"+playerName+" §ađã nạp thành công thẻ cào §f"+R[1]+"VND. §aCám ơn bạn đã ủng hộ server !");
            RootCore.get().getRootPointManager().addRootPoint(playerName, RootCore.get().getConfig().getInt("discount") * Integer.parseInt(R[1]) / 10000);
        }*/
    }

    private String getCardType(String supplier) {
        switch (supplier){
            case("VIETTEL"): return "1";
            case("MOBI"): return "2";
            case("MOBIPHONE"): return "2";
            case("MOBIFONE"): return "2";
            case("VINA"): return "3";
            case("VINAPHONE"): return "3";
            case("GATE"): return "4";
            case("VIETNAMMOBILE"): return "6";
            case("VNM"): return "6";
            case("Zing"): return "7";
        }
        return null;
    }
}

class Reponse {
    public int code = 0;
    public String msg = "";
    public Integer info_card;
    public String seri = "";
    public String pin = "";
    public String note = "";
    public String transaction = "";
}
