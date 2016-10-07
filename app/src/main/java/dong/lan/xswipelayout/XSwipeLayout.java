package dong.lan.xswipelayout;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 梁桂栋 on 16-10-5 ： 下午8:02.
 * Email:       760625325@qqcom
 * GitHub:      github.com/donlan
 * description: code
 */

public class XSwipeLayout extends FrameLayout {

    private static final String TAG = "XSwipeLayout";

    //以下四个静态常亮是判别菜单view放置在内容view的方向的标示
    public static final int FLAG_RIGHT = 1;
    public static final int FLAG_LEFT = 2;
    public static final int FLAG_TOP = 4;
    public static final int FLAG_BOTTOM = 8;


    private int swipeFlag = FLAG_RIGHT;     //默认菜单view是在内容view的右边


    private ViewDragHelper viewDragHelper;
    private View content;   //装载内容的View
    private View menu;      //装载菜单的View
    private int divinerX;   //菜单view完全可见时，内容view与菜单view在X轴分割处的x坐标
    private int divinerY;   //菜单view完全可见时，内容view与菜单view在Y轴分割处的y坐标
    private int cWidth;     //内容的宽
    private int cHeight;    //内容的高

    private int mWidth;     //菜单的宽
    private int mHeight;    //菜单的高

    private int cLeft;      //内容view的右上角x坐标
    private int cTop;       //内容view的右上角y坐标
    private int mLeft;      //菜单view的右上角x坐标
    private int mTop;       //菜单view的右上角y坐标
    private float percent;  //菜单view可见部分占菜单总大小的百分比

    public XSwipeLayout(Context context) {
        this(context, null);
    }

    public XSwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new Callback());
    }


    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return viewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        cWidth = content.getWidth();
        cHeight = content.getHeight();
        mHeight = menu.getHeight();
        mWidth = menu.getWidth();
        if (swipeFlag == FLAG_RIGHT) {
            divinerX = cWidth - mWidth;
            cLeft = 0;
            mLeft = cWidth;
        } else if (swipeFlag == FLAG_LEFT) {
            divinerX = mWidth;
            cLeft = 0;
            mLeft = -mWidth;
        } else if (swipeFlag == FLAG_TOP) {
            divinerY = mHeight;
            cTop = 0;
            mTop = -mHeight;
        } else if (swipeFlag == FLAG_BOTTOM) {
            divinerY = cHeight - mHeight;
            cTop = 0;
            mTop = cHeight;
        }
        close();
    }

    //返回包装主体内容的view（其实是一个VewGroup）
    public View getContent() {
        return content;
    }

    //返回包装menu的view（其实是一个VewGroup）
    public View getMenu() {
        return menu;
    }

    //设置滑动方向（也就是menu放置的方向）
    public void setSwipeFlag(int swipeFlag) {
        if (swipeFlag != FLAG_RIGHT && swipeFlag != FLAG_LEFT && swipeFlag != FLAG_TOP && swipeFlag != FLAG_BOTTOM)
            throw new RuntimeException("the swipe flag only can be one of FLAG_RIGHT,FLAG_LEFT,FLAG_TOP,FLAG_BOTTOM");
        this.swipeFlag = swipeFlag;
        requestLayout();
    }

    //菜单view打开（完全可见）
    public void open() {
        if (swipeFlag == FLAG_RIGHT) {
            content.layout(divinerX - cWidth, 0, cWidth - mWidth, cHeight);
            menu.layout(divinerX, 0, divinerX + mWidth, mHeight);
        } else if (swipeFlag == FLAG_LEFT) {
            content.layout(mWidth, 0, cWidth + mWidth, cHeight);
            menu.layout(0, 0, mWidth, mHeight);
        } else if (swipeFlag == FLAG_TOP) {
            content.layout(0, divinerY, cWidth, mHeight + cHeight);
            menu.layout(0, 0, mWidth, mHeight);
        } else if (swipeFlag == FLAG_BOTTOM) {
            content.layout(0, -mHeight, cWidth, cHeight - mHeight);
            menu.layout(0, divinerY, mWidth, cHeight);
        }
        if (swipeListener != null)
            swipeListener.onOpen();
    }

    //菜单view关闭（完全不可见）
    public void close() {
        if (swipeFlag == FLAG_RIGHT) {
            content.layout(0, 0, cWidth, cHeight);
            menu.layout(cWidth, 0, cWidth + mWidth, mHeight);
        } else if (swipeFlag == FLAG_LEFT) {
            content.layout(0, 0, cWidth, cHeight);
            menu.layout(-mWidth, 0, 0, mHeight);
        } else if (swipeFlag == FLAG_TOP) {
            content.layout(0, 0, cWidth, cHeight);
            menu.layout(0, -mHeight, mWidth, 0);
        } else if (swipeFlag == FLAG_BOTTOM) {
            content.layout(0, 0, cWidth, cHeight);
            menu.layout(0, cHeight, mWidth, cHeight + mHeight);
        }
        if (swipeListener != null)
            swipeListener.onClose();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        content = getChildAt(1);
        menu = getChildAt(0);
        if (swipeListener != null)
            swipeListener.onClose();
    }


    class Callback extends ViewDragHelper.Callback {

        /**
         * 当用户的输入指示他们要使用pointerId指示的指针捕获给定的子视图时调用。
         * 如果允许用户使用指示的指针拖动给定视图，则回调应返回true。
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 返回拖动的view在水平方向的位置信息（left是该view的左上角x坐标，dx是偏移量）
         * 返回值会传递到onViewPositionChanged（）
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (swipeFlag == FLAG_RIGHT) {
                if (child == menu) {
                    if (left <= divinerX)
                        return divinerX;
                    else if (left >= cWidth)
                        return cWidth;
                } else if (child == content) {
                    if (left >= 0)
                        return 0;
                    else if (-left >= mWidth)
                        return -mWidth;
                }
                return left;
            } else if (swipeFlag == FLAG_LEFT) {
                if (child == menu) {
                    if (left >= 0)
                        return 0;
                    else if (left <= -mWidth)
                        return -mWidth;
                } else if (child == content) {
                    if (left <= 0)
                        return 0;
                    else if (left >= mWidth)
                        return mWidth;
                }
                return left;
            }
            return 0;
        }

        /**
         * 返回拖动的view在竖直方向的位置信息（top是该view的左上角y坐标,dy是偏移量）
         * 返回值会传递到onViewPositionChanged（）
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (swipeFlag == FLAG_TOP) {
                if (child == menu) {
                    if (top >= 0)
                        return 0;
                    else if (top <= -mHeight)
                        return -mHeight;
                } else if (child == content) {
                    if (top <= 0)
                        return 0;
                    else if (top >= mHeight)
                        return mHeight;
                }
                return top;
            } else if (swipeFlag == FLAG_BOTTOM) {
                if (child == menu) {
                    if (top >= cHeight)
                        return cHeight;
                    else if (top <= divinerY)
                        return divinerY;
                } else if (child == content) {
                    if (top >= 0)
                        return 0;
                    else if (top <= -mHeight)
                        return -mHeight;
                }
                return top;
            }
            return 0;
        }


        /**
         * 当一个view的拖动事件结束的时候（也就是手指松开屏幕的时候）的回调
         *
         * @param releasedChild 拖动释放的view
         * @param xvel 拖动释放时该view在x轴的拖动速率(指针离开屏幕时在X轴的速度，以像素/秒为单位。)
         * @param yvel 拖动释放时该view在y轴的拖动速率(指针离开屏幕时在Y轴的速度，以像素/秒为单位。)
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == content && percent > 0.3) {
                open();
                return;
            } else if (releasedChild == content && (percent < 0.7)) {
                close();
                return;
            } else if (releasedChild == menu && percent > 0.7) {
                open();
                return;
            } else if (releasedChild == menu && percent < 0.7) {
                close();
                return;
            }
            if (swipeFlag == FLAG_RIGHT) {
                if (xvel > 0) {
                    close();
                } else if (xvel < 0) {
                    open();
                }
            } else if (swipeFlag == FLAG_LEFT) {
                if (xvel > 0) {
                    open();
                } else if (xvel < 0) {
                    close();
                }
            } else if (swipeFlag == FLAG_TOP) {
                if (yvel > 0) {
                    close();
                } else if (yvel < 0) {
                    open();
                }
            } else if (swipeFlag == FLAG_BOTTOM) {
                if (yvel > 0) {
                    open();
                } else if (yvel < 0) {
                    close();
                }
            }
        }

        /**
         * 返回可拖动子视图的水平运动范围（以像素为单位）的大小。
         * 对于不能水平移动的视图，此方法应返回0。
         *
         * @param child 该布局下的view，可以根据具体每个view返回不一样的值
         * @return 水平运动范围以像素为单位
         */
        @Override
        public int getViewHorizontalDragRange(View child) {
            return cWidth;
        }

        /**
         * 返回可拖动子视图的竖直运动范围（以像素为单位）的大小。
         * 对于不能竖直移动的视图，此方法应返回0。
         *
         * @param child 该布局下的view，可以根据具体每个view返回不一样的值
         * @return 竖直运动范围以像素为单位
         */
        @Override
        public int getViewVerticalDragRange(View child) {
            return cHeight;
        }

        /**
         * 当捕捉到一个view的位置改变后的回调方法
         *
         * @param changedView 位置改变的view
         * @param left 当前位置改变的view的左上角x坐标
         * @param top 当前位置改变的view的左上角y坐标
         * @param dx 与上次拖动导致位置改变的x轴偏移量
         * @param dy 与上次拖动导致位置改变的x轴偏移量
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            percent = 0.0f;
            if (swipeFlag == FLAG_RIGHT) {
                if (changedView == content) {
                    cLeft = left;
                    mLeft = cWidth + left;
                } else if (changedView == menu) {
                    mLeft = left;
                    cLeft = mLeft - cWidth;
                }
                content.layout(cLeft, 0, cLeft + cWidth, cHeight);
                menu.layout(mLeft, 0, mLeft + mWidth, mHeight);
                percent = (cWidth - mLeft) * 1.0f / mWidth;
            } else if (swipeFlag == FLAG_LEFT) {
                if (changedView == content) {
                    cLeft = left;
                    mLeft = -mWidth + left;
                } else if (changedView == menu) {
                    mLeft = left;
                    cLeft = left + mWidth;
                }
                content.layout(cLeft, 0, cLeft + cWidth, cHeight);
                menu.layout(mLeft, 0, mLeft + mWidth, mHeight);
                percent = (cLeft) * 1.0f / mWidth;
            } else if (swipeFlag == FLAG_TOP) {
                if (changedView == content) {
                    cTop = top;
                    mTop = cTop - mHeight;
                } else if (changedView == menu) {
                    mTop = top;
                    cTop = mTop + mHeight;
                }
                content.layout(0, cTop, cWidth, cTop + cHeight);
                menu.layout(0, mTop, mWidth, mTop + mHeight);
                percent = (cTop) * 1.0f / mHeight;
            } else if (swipeFlag == FLAG_BOTTOM) {
                if (changedView == content) {
                    cTop = top;
                    mTop = cTop + cHeight;
                } else if (changedView == menu) {
                    mTop = top;
                    cTop = -(cHeight - mTop);
                }
                content.layout(0, cTop, cWidth, cTop + cHeight);
                menu.layout(0, mTop, mWidth, mTop + mHeight);
                percent = (cHeight - mTop) * 1.0f / mHeight;
            }
            if (swipeListener != null)
                swipeListener.onSwipe(percent);
        }
    }

    private OnSwipeListener swipeListener = null;

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        swipeListener = onSwipeListener;
    }

    public void removeSwipeListener() {
        swipeListener = null;
    }

    /**
     * 菜单view关闭，打开，拖动事件的对外回调接口
     */
    public interface OnSwipeListener {
        void onOpen();

        void onClose();

        void onSwipe(float percent);
    }
}
