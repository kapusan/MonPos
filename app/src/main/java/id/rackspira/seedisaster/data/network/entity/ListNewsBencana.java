
package id.rackspira.seedisaster.data.network.entity;

import com.google.gson.annotations.SerializedName;

public class ListNewsBencana {

    @SerializedName("href")
    private String mHref;
    @SerializedName("id")
    private String mId;
    @SerializedName("score")
    private Long mScore;

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getScore() {
        return mScore;
    }

    public void setScore(Long score) {
        mScore = score;
    }

}
