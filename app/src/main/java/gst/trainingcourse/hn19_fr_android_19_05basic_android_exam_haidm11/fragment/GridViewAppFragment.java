package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.R;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.adapter.GridViewAppAdapter;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.listener.ItemTouchListenner;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.listener.OnItemClickListener;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.listener.SimpleItemTouchHelperCallback;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;


public class GridViewAppFragment extends Fragment {

    private static final String ARG_APP_LIST = "ARG_APP_LIST";

    public static final String TAG = "GridViewAppFragment";

    private Context mContext;

    private final ArrayList<App> mAppList = new ArrayList<>();

    private RecyclerView mGridApp;
    private RelativeLayout relativeLayout;

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        @Override
        public void OnClick(App app) {
            if (mContext != null) {
                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
                if (intent == null) {
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.setData(Uri.parse("market://details?id=" + app.getPackageName()));
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        }
    };


    public GridViewAppFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GridViewAppFragment newInstance(ArrayList<App> appList) {
        GridViewAppFragment fragment = new GridViewAppFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_APP_LIST, appList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_APP_LIST, mAppList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<App> appList = savedInstanceState.getParcelableArrayList(ARG_APP_LIST);
            if (appList != null && !appList.isEmpty()) {
                mAppList.addAll(appList);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<App> appList = getArguments().getParcelableArrayList(ARG_APP_LIST);
            if (appList != null && !appList.isEmpty()) {
                mAppList.addAll(appList);
            }
        }
        mContext = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_gridview, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mGridApp = view.findViewById(R.id.gvListApp);
        mGridApp.setLayoutManager(new GridLayoutManager(view.getContext(),3));
        relativeLayout = view.findViewById(R.id.container);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        final GridViewAppAdapter mAdapter = new GridViewAppAdapter(mContext, mAppList, mOnItemClickListener);
        mGridApp.setAdapter(mAdapter);
        addItemTouchCallback(mGridApp,mAdapter);

    }
    private void addItemTouchCallback(RecyclerView recyclerView, final GridViewAppAdapter mAdapter) {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(new ItemTouchListenner() {
            @Override
            public void onMove(int oldPosition, int newPosition) {
                mAdapter.onMove(oldPosition, newPosition);
            }

            @Override
            public void swipe(int position, int direction) {
                mAdapter.swipe(position, direction);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
