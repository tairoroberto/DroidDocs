package br.com.trmasolucoes.droiddocs.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tairo on 09/02/16.
 */
public class Favorite implements Parcelable{
    private long id;
    private String desc;
    private String link;

    public Favorite() {
    }

    public Favorite(long id, String link) {
        this.id = id;
        this.link = link;
    }

    protected Favorite(Parcel in) {
        id = in.readLong();
        link = in.readString();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(link);
    }
}
