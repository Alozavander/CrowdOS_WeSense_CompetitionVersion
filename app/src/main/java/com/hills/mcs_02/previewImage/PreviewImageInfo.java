package com.hills.mcs_02.previewImage;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Parcel;

import androidx.annotation.Nullable;

import com.previewlibrary.enitity.IThumbViewInfo;

@SuppressLint("ParcelCreator")
public class PreviewImageInfo implements IThumbViewInfo {
    private String url;  /** Image url */
    private Rect mBounds; /** The coordinates of image */

    public PreviewImageInfo(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    @Override
    public Rect getBounds() {/** Return the coordinates of the image */
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        /** The view is not used, so it returns null */
        return null;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
