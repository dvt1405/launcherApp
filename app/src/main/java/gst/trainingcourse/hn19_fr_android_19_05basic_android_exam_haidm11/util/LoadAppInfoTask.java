package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.activity.LoadAppInfoMainInterface;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.fragment.LoadAppInfoFragment;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;

public class LoadAppInfoTask extends AsyncTask<Void, Integer, ArrayList<App>> {
    public static final String TAG = "LoadAppInfoTask";

    private WeakReference<LoadAppInfoMainInterface> mInterface;

    private LoadAppInfoFragment mFragment;

    public LoadAppInfoTask(@NonNull LoadAppInfoMainInterface loadAppInfoMainInterface) {
        mInterface = new WeakReference<>(loadAppInfoMainInterface);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        LoadAppInfoMainInterface in = mInterface.get();
        in.onPreLoad();

        FragmentTransaction transaction = in.getFragmentManager().beginTransaction();
        mFragment = LoadAppInfoFragment.newInstance(0);
        transaction.replace(in.getRootResId(), mFragment, LoadAppInfoFragment.TAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    protected final ArrayList<App> doInBackground(Void... voids) {
        ArrayList<App> appArrayList = new ArrayList<>();

        PackageManager pm = mInterface.get().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0; i < packages.size(); i++) {
            if (pm.getLaunchIntentForPackage(packages.get(i).packageName) != null) {
                String appName = pm.getApplicationLabel(packages.get(i)).toString();
                String packageName = packages.get(i).packageName;
                Drawable icon = null;
                long installedDate = 0;

                try {
                    icon = pm.getApplicationIcon(packageName);
                    installedDate = pm.getPackageInfo(packageName, 0).firstInstallTime;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                appArrayList.add(new App(appName, icon, packageName, installedDate));
            }
            publishProgress(i * 100 / packages.size());
        }
        return appArrayList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mFragment.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        LoadAppInfoMainInterface in = mInterface.get();
        if (apps != null && apps.size() > 0) {
            ArrayList<App> resultList = new ArrayList<>(apps);
            in.getResultList().addAll(resultList);
            in.onPostLoad();
        }
    }
}
