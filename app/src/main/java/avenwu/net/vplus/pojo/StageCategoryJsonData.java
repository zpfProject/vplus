package avenwu.net.vplus.pojo;

import io.realm.RealmObject;

/**
 * Created by chaobin on 7/11/15.
 */
public class StageCategoryJsonData extends RealmObject {
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
