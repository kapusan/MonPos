
package id.rackspira.seedisaster.data.network.entity;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BaseNewsBencana {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("data")
    private List<ListNewsBencana> mData;
    @SerializedName("href")
    private String mHref;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("totalCount")
    private Long mTotalCount;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public List<ListNewsBencana> getData() {
        return mData;
    }

    public void setData(List<ListNewsBencana> data) {
        mData = data;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public Long getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(Long totalCount) {
        mTotalCount = totalCount;
    }

}
