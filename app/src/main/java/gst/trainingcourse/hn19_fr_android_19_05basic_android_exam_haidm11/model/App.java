package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.util.DateTimeHelper;

public class App implements Parcelable {
    private String mName;
    private Drawable mIcon;
    private String mPackageName;
    private long mInstalledDate;

    @SuppressWarnings("unused")
    public App() {
        mName = "No Name";
        mIcon = null;
        mPackageName = "";
        mInstalledDate = 0;
    }

    @SuppressWarnings("unused")
    public App(String appName, String packageName, long installedDate) {
        setName(appName);
        setPackageName(packageName);
        setInstalledDate(installedDate);
    }

    public App(String appName, Drawable icon, String packageName, long installedDate) {
        setName(appName);
        setIcon(icon);
        setPackageName(packageName);
        setInstalledDate(installedDate);
    }

    private void setName(String appName) {
        if (appName != null && !appName.isEmpty()) {
            mName = appName;
        }
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            mIcon = icon;
        }
    }

    private void setPackageName(String packageName) {
        mPackageName = (packageName != null && !packageName.isEmpty()) ? packageName : "";
    }

    private void setInstalledDate(long installedDate) {
        mInstalledDate = installedDate;
    }

    public String getName() {
        return mName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getInstalledDate() {
        return DateTimeHelper.getInstance().convertTime(mInstalledDate);
    }

    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>() {
        public App createFromParcel(Parcel in) {
            return new App(in);
        }

        public App[] newArray(int size) {
            return new App[size];
        }
    };

    private App(Parcel in) {
        if (in != null) {
            String name;
            String packageName;
            if ((name = in.readString()) != null) {
                mName = name;
            }
            if ((packageName = in.readString()) != null) {
                mPackageName = packageName;
            }
            mInstalledDate = in.readLong();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mPackageName);
        dest.writeLong(mInstalledDate);
    }
}
