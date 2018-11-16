
package id.rackspira.seedisaster.data.network.entity;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BaseDetailNewsBencana {

    @SerializedName("data")
    private List<DetailBencana> mData;

    public List<DetailBencana> getData() {
        return mData;
    }

    public void setData(List<DetailBencana> data) {
        mData = data;
    }

}
