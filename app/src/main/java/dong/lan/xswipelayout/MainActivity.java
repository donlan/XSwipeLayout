package dong.lan.xswipelayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import dong.lan.library.SwipeListener;
import dong.lan.library.XSwipeLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    XSwipeLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeLayout = (XSwipeLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_RIGHT);
        swipeLayout.setViewGap(100);
        swipeLayout.setOnSwipeListener(new SwipeListener() {
            @Override
            public void onOpen(int pos) {
                swipeLayout.getMenu().setAlpha(1);
            }

            @Override
            public void onClose(int pos,boolean absClosed) {
                swipeLayout.getMenu().setAlpha(0);
            }

            @Override
            public void onSwipe(float percent) {
                swipeLayout.getMenu().setAlpha(percent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.active_QQMenu:
                startActivity(new Intent(MainActivity.this,QQSlideViewActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
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
