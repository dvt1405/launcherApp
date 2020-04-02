package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.R;


public class LoadAppInfoFragment extends Fragment {
    public static final String TAG = "LoadAppInfoFragment";

    private static final String ARG_PROGRESS = "ARG_PROGRESS";
    // TODO: Rename and change types of parameters
    private int mProgress;

    private ProgressBar mProgressBar;

    private TextView mTvProgress;

    public LoadAppInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoadAppInfoFragment newInstance(int progress) {
        LoadAppInfoFragment fragment = new LoadAppInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PROGRESS, progress);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProgress = getArguments().getInt(ARG_PROGRESS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_load_app_info, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvProgress = view.findViewById(R.id.tvProgressAppInfoLoad);

        mProgressBar = view.findViewById(R.id.progressAppInfoLoad);
        mProgressBar.setMax(100);

        if (mProgress > 0) {
            mTvProgress.setText("Loading.." + mProgress);
            mProgressBar.setProgress(mProgress);
        }
    }

    public void setProgress(int progress) {
        mTvProgress.setText("Loading.." + progress);
        mProgressBar.setProgress(progress);
    }
}
