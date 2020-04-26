package rootmc.net.rootcore.element;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;

public class BuyKitButton extends ElementButton {

    private String key,url,customName,des;
    private Integer price;


    public BuyKitButton(String key,String customname, String url,int price,String des) {
        super(customname);
        this.key = key;
        this.url = url;
        this.customName = customname;
        this.price = price;
        this.des = des;
        if (!url.equals(""))
        addImage(new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL,url));
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public String getCustomName() {
        return customName;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDes() {
        return des;
    }
}
