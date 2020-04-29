package rootmc.net.rootcore.data;

import lombok.Getter;

public class RankData {

    @Getter
    private final String key;
    @Getter
    private final String des;
    @Getter
    private final int day;
    @Getter
    private final int rp;
    @Getter
    private final int rpvv;
    @Getter
    private final int level;
    @Getter
    private final String url;
    @Getter
    private final String cusName;

    public RankData(String key, String cusName, String des, int day, int level, int rp, int rpvv, String url) {
        this.key = key;
        this.cusName = cusName;
        this.des = des;
        this.day = day;
        this.level = level;
        this.rp = rp;
        this.rpvv = rpvv;
        this.url = url;
    }

}
