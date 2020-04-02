package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.util;

import android.annotation.SuppressLint;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    private static DateTimeHelper mInstance;

    public static DateTimeHelper getInstance() {
        if (mInstance == null) {
            mInstance = new DateTimeHelper();
        }
        return mInstance;
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat") Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
