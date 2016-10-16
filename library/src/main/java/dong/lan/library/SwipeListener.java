package dong.lan.library;

/**
 * Created by 梁桂栋 on 16-10-15 ： 下午9:52.
 * Email:       760625325@qq.com
 * GitHub:      github.com/donlan
 * description: XSwipeLayout
 */

public interface SwipeListener {
    void onSwipe(float percent);
    void onClose(int pos,boolean absoluteClosed);
    void onOpen(int pos);
}
