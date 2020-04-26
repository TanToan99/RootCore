package rootmc.net.rootcore.element;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;

public class RankButton extends ElementButton {

    private String name,ctname,des;
    private int day,rp;

    public RankButton(String name, String csname, int day, int rp, String des, String url) {
        super(csname);
        this.day = day;
        this.rp = rp;
        this.name = name;
        this.des = des;
        this.ctname = csname;
        if (!url.equals(""))
            addImage(new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,url));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getRp() {
        return rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getCtname() {
        return ctname;
    }

    public void setCtname(String ctname) {
        this.ctname = ctname;
    }
}

