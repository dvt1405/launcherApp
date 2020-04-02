package gst.trainingcourse.hn19_fr_android_19_05basic_android_exam_haidm11.listener;

public interface ItemTouchListenner {
    void onMove(int oldPosition, int newPosition);

    void swipe(int position, int direction);
}