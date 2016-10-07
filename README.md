# XSwipeLayout
一个支持上下左右四个方向的类侧滑菜单布局

### 以下是效果图
![](/gif/demo.gif)



### 如何使用
1. 你只需要一个 [XSwipeLayout.java](app/src/main/java/dong/lan/xswipelayout/MainActivity.java)
2. 布局中使用，XSwipeLayout内部只能装两个view(一个是内容页，一个是菜单页，并且主内容总是放在最后，因为XSwipeLayout继承自FrameLayout)
```xml
<dong.lan.xswipelayout.XSwipeLayout
        android:id="@+id/swipe_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorPrimary">
            <!-- 这是你的菜单页面-->
        </RelativeLayout>

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/colorAccent">
            <!-- 这是你的主内容页面。切记这总是放在最后的-->
        </RelativeLayout>
    </dong.lan.xswipelayout.XSwipeLayout>
```

3. 代码中

```java
        swipeLayout = (XSwipeLayout) findViewById(R.id.swipe_layout);
        //设置滑动方向，不设置默认是FLAG_RIGHT,也就是菜单view在右边
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_RIGHT); 
        //滑动回调，这不是必须的，如果需要根据滑动返回的百分比设置动画，这或许会很有用
        swipeLayout.setOnSwipeListener(new XSwipeLayout.OnSwipeListener() {
            @Override
            public void onOpen() {
                swipeLayout.getMenu().setAlpha(1);
            }

            @Override
            public void onClose() {
                swipeLayout.getMenu().setAlpha(0);
            }

            @Override
            public void onSwipe(float percent) {
                swipeLayout.getMenu().setAlpha(percent);
            }
        });
```

4. enjoy～
