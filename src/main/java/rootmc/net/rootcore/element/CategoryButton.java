/*
 * A button, which represents a category of items and stores a string id of the category.
 * Created by Leonidius20 on 26.06.18.
 * This class is a part of "Trading Interface".
 */
package rootmc.net.rootcore.element;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;

public class CategoryButton extends ElementButton {

    private final String customName;
    private String categoryId;

    public CategoryButton(String categoryId, String name, String url) {
        super(name);
        this.categoryId = categoryId;
        this.customName = name;
        if (!url.equals(""))
            addImage(new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_URL, url));
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCustomName() {
        return customName;
    }
}

