package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.fragment.GridViewAppFragment;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;

public class PageViewAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<App> mAppList = new ArrayList<>();

    private final int mNumOfAppPerPage = 16;

    public PageViewAdapter(FragmentManager fragmentManager, ArrayList<App> appList) {
        super((fragmentManager));
        if (appList != null && !appList.isEmpty()) {
            mAppList.addAll(appList);
        }
    }

    @Override
    public Fragment getItem(int i) {
        int min = i * mNumOfAppPerPage;
        int ceilMax = (i + 1) * mNumOfAppPerPage;
        int max = (ceilMax < mAppList.size()) ? ceilMax : mAppList.size();
        return GridViewAppFragment.newInstance(new ArrayList<>(mAppList.subList(min, max)));
    }

    @Override
    public int getCount() {
        return mAppList.size() / mNumOfAppPerPage + 1;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }
}
