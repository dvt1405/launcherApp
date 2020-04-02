package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Comparator;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.R;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.adapter.PageViewAdapter;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.util.LoadAppInfoTask;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private final int SORT_MODE_NO = 0;

    private final int SORT_MODE_ALPHABET = 1;

    private final int SORT_MODE_INSTALLED_DATE = 2;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private PageViewAdapter mAdapter;

    private ImageButton mBtnNextPage;

    private ImageButton mBtnPreviousPage;

    private Spinner mSpinner;

    private FrameLayout mFrameLoad;

    private final ArrayList<App> mAppList = new ArrayList<>();

    private final LoadAppInfoMainInterface mInterface = new LoadAppInfoMainInterface() {
        @Override
        public PackageManager getPackageManager() {
            return MainActivity.this.getPackageManager();
        }

        @Override
        public int getRootResId() {
            return R.id.frameLoadAppInfo;
        }

        @Override
        public FragmentManager getFragmentManager() {
            return getSupportFragmentManager();
        }

        @Override
        public ArrayList<App> getResultList() {
            return mAppList;
        }

        @Override
        public void onPreLoad() {
            mFrameLoad.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostLoad() {
            bindData(mAppList);
            mFrameLoad.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }


    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);

        mBtnNextPage = findViewById(R.id.btnNext);

        mBtnPreviousPage = findViewById(R.id.btnPrevious);

        mSpinner = findViewById(R.id.spinner);

        mFrameLoad = findViewById(R.id.frameLoadAppInfo);
    }

    private void initData() {
        LoadAppInfoTask loadAppInfoTask = new LoadAppInfoTask(mInterface);
        loadAppInfoTask.execute();

        mTabLayout.setupWithViewPager(mViewPager);

        String[] optionList = {"No Sort", "Sort Alphabetically", "Sort by Installed Date"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinner.setAdapter(adapter);
    }

    private void initAction() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mAdapter != null) {
                    checkStartEndViewPager(position, mAdapter.getCount() - 1);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                if (mAdapter != null && currentItem < mAdapter.getCount() - 1) {
                    mViewPager.setCurrentItem(++currentItem);
                }
            }
        });

        mBtnPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = mViewPager.getCurrentItem();
                if (currentItem > 0) {
                    mViewPager.setCurrentItem(--currentItem);
                }
            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewSortMode(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void checkStartEndViewPager(int pos, int max) {
        if (pos == 0) {
            mBtnPreviousPage.setEnabled(false);
        } else if (pos == max) {
            mBtnNextPage.setEnabled(false);
        } else {
            mBtnNextPage.setEnabled(true);
            mBtnPreviousPage.setEnabled(true);
        }
    }

    private void viewSortMode(int sortMode) {
        ArrayList<App> appList = new ArrayList<>(mAppList);
        switch (sortMode) {
            case SORT_MODE_NO:
                break;
            case SORT_MODE_ALPHABET:
                appList.sort(new Comparator<App>() {
                    @Override
                    public int compare(App o1, App o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
                break;
            case SORT_MODE_INSTALLED_DATE:
                appList.sort(new Comparator<App>() {
                    @Override
                    public int compare(App o1, App o2) {
                        return (o2.getInstalledDate().compareToIgnoreCase(o1.getInstalledDate()));
                    }
                });
                break;
            default:
                break;
        }
        bindData(appList);
    }

    private void bindData(ArrayList<App> appList) {
        mAdapter = new PageViewAdapter(getSupportFragmentManager(), appList);
        mViewPager.setAdapter(mAdapter);
    }
}
