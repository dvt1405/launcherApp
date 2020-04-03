package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.R;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.listener.OnItemClickListener;
import gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.model.App;

public class GridViewAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ArrayList<App>> mAppList = new ArrayList<ArrayList<App>>();
    private Context mContext;
    private OnItemClickListener mListener;
    private Type type = Type.CHILD;
    private OnUpdateItem mOnUpdateItem;
    private final OnItemHolderClickListener mViewOnClickListener = new OnItemHolderClickListener() {
        @Override
        public void onItemClick(App app) {
            mListener.OnClick(app);
        }
    };
    public ArrayList<ArrayList<App>> getmAppList() {
        return mAppList;
    }

    public void setmAppList(ArrayList<ArrayList<App>> mAppList) {
        this.mAppList = mAppList;
        notifyDataSetChanged();
    }

    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mAppList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mAppList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
//        mAppList.get(toPosition).addAll(mAppList.get(fromPosition));
//        mAppList.remove(fromPosition);
//        notifyItemChanged(toPosition);
    }

    public void swipe(int position, int direction) {
        mAppList.remove(position);
        notifyItemRemoved(position);
    }

    public GridViewAppAdapter(Context context, ArrayList<App> appList, OnItemClickListener listener) {
        if (context != null) {
            mContext = context;
        }
        if (appList != null && !appList.isEmpty()) {
            for (App app : appList) {
                ArrayList<App> item = new ArrayList<>();
                item.add(app);
                mAppList.add(item);
            }

        }
        if (listener != null) {
            mListener = listener;
        }
    }

    public GridViewAppAdapter(Context context, ArrayList<App> appList, Type type) {
        if (context != null) {
            mContext = context;
        }
        if (appList != null && !appList.isEmpty()) {
            for (App app : appList) {
                ArrayList<App> item = new ArrayList<>();
                item.add(app);
                mAppList.add(item);
            }

        }
        this.type = type;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (type == Type.CHILD) {

        } else {
//            LayoutInflater inflater = LayoutInflater.from(mContext);
//            return new ViewContainerHolder(inflater.inflate(R.layout.item_child, parent, false));

        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.item_gridview_applauncher, parent, false), mViewOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bind(mAppList.get(position));
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }


    //leak memory
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvAppName;
        RecyclerView recyclerView;
        private App app;
        private OnItemHolderClickListener callback;
        ViewHolder(View view,OnItemHolderClickListener callback) {
            super(view);
            imgIcon = view.findViewById(R.id.imgAppIcon);
            tvAppName = view.findViewById(R.id.tvAppName);
            recyclerView = view.findViewById(R.id.recyclerView);
            this.callback = callback;
        }

        public App getApp() {
            return app;
        }

        public void setApp(App app) {
            this.app = app;
        }

        public void bind(final ArrayList<App> apps) {
            if (apps.size() == 1) {
                app = apps.get(0);
                if (app.getIcon() == null) {
                    try {
                        Drawable icon = itemView.getContext().getPackageManager().getApplicationIcon(app.getPackageName());
                        app.setIcon(icon);
                        imgIcon.setImageDrawable(icon);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                imgIcon.setImageDrawable(app.getIcon());
                tvAppName.setText(app.getName());
                recyclerView.setVisibility(View.GONE);
                imgIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onItemClick(app);
                    }
                });
            } else {
                imgIcon.setVisibility(View.GONE);
                tvAppName.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(new RecyclerView.Adapter() {
                    @NonNull
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                        return new ViewAHolder(inflater.inflate(R.layout.item_gridview_applauncher, null));
                    }

                    @Override
                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                        ((ViewAHolder) holder).bind(apps.get(position));
                    }

                    @Override
                    public int getItemCount() {
                        return apps.size();
                    }
                });
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
            }
        }
    }

    public interface OnUpdateItem {
        public void onUpdate(int currentPosition, int targetPosition);
    }

    private static class ViewAHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView appName;

        ViewAHolder(View view) {
            super(view);
            imgIcon = view.findViewById(R.id.imgAppIcon);
            appName = view.findViewById(R.id.tvAppName);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imgIcon.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            imgIcon.setLayoutParams(layoutParams);
        }

        public void bind(App app) {
            if (app.getIcon() == null) {
                try {
                    Drawable icon = itemView.getContext().getPackageManager().getApplicationIcon(app.getPackageName());
                    app.setIcon(icon);
                    imgIcon.setImageDrawable(icon);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            imgIcon.setImageDrawable(app.getIcon());
            appName.setText("");
        }
    }

    enum Type {
        PARENT, CHILD
    }

    interface OnItemHolderClickListener {
        public void onItemClick(App app);
    }
}
