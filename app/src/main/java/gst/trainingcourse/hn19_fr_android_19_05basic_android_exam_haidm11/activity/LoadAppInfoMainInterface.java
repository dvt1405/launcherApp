package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.activity;

import android.content.pm.PackageManager;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;

public interface LoadAppInfoMainInterface {
    PackageManager getPackageManager();

    int getRootResId();

    FragmentManager getFragmentManager();

    ArrayList<App> getResultList();

    void onPreLoad();

    void onPostLoad();
}
