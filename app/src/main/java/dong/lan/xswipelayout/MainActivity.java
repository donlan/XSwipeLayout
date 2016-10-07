package dong.lan.xswipelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    XSwipeLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = (XSwipeLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_RIGHT);
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

    }

    public void right(View v){
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_RIGHT);
    }
    public void left(View v){
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_LEFT);
    }
    public void top(View v){
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_TOP);
    }
    public void bottom(View v){
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_BOTTOM);
    }
}
