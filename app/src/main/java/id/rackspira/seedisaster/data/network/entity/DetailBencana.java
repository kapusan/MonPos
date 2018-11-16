
package id.rackspira.seedisaster.data.network.entity;

import com.google.gson.annotations.SerializedName;

public class DetailBencana {

    @SerializedName("fields")
    private Fields mFields;
    @SerializedName("id")
    private String mId;

    public Fields getFields() {
        return mFields;
    }

    public void setFields(Fields fields) {
        mFields = fields;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
