package com.shycoder.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {

    @SerializedName(value = "id")
    private int mID;

    @SerializedName(value = "shortDescription")
    private String mShortDescription;

    @SerializedName(value = "description")
    private String mDescription;

    @SerializedName(value = "videoURL")
    private String mVideoURL;

    @SerializedName(value = "thumbnailURL")
    private String mThumbnailURL;

    public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        mID = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoURL = videoURL;
        mThumbnailURL = thumbnailURL;
    }

    protected Steps(Parcel in) {
        mID = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public void setID(int id) {
        mID = id;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setVideoURL(String videoURL) {
        mVideoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        mThumbnailURL = thumbnailURL;
    }

    public int getID() {
        return mID;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mID);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }
}
