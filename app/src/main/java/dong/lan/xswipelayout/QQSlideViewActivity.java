package dong.lan.xswipelayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import dong.lan.library.SwipeListener;
import dong.lan.library.XSwipeLayout;

/**
 * Created by 梁桂栋 on 16-10-13 ： 上午12:18.
 * Email:       760625325@qq.com
 * GitHub:      github.com/donlan
 * description: XSwipeLayout
 */

public class QQSlideViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    XSwipeLayout swipeLayout;
    ImageView head;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        head = (ImageView) findViewById(R.id.head);
        swipeLayout = (XSwipeLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setSwipeFlag(XSwipeLayout.FLAG_LEFT);
        swipeLayout.setViewGap(100);
        swipeLayout.setOnSwipeListener(new SwipeListener() {
            @Override
            public void onOpen(int pos) {
                head.setAlpha(0.0f);
                //swipeLayout.getContent().setScaleX(0.8f);
                //swipeLayout.getContent().setScaleY(0.8f);
            }

            @Override
            public void onClose(int pos,boolean absClosed) {
                head.setAlpha(1.0f);
                //swipeLayout.getContent().setScaleX(1f);
                //swipeLayout.getContent().setScaleY(1f);
            }

            @Override
            public void onSwipe(float percent) {
                    head.setAlpha(1.0f - percent);
                //swipeLayout.getContent().setScaleX(1.0f - percent*0.2f);
                //swipeLayout.getContent().setScaleY(1.0f - percent*0.2f);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(new Adapter());
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swipeLayout.isOpen()){
                    swipeLayout.close();
                }else{
                    swipeLayout.open();
                }
            }
        });
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder>{

        private static final String TAG = "TAG";

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view;
            if(viewType==1){
                view = LayoutInflater.from(QQSlideViewActivity.this).inflate(R.layout.item_head,null);
            }else{
                view = LayoutInflater.from(QQSlideViewActivity.this).inflate(R.layout.item_list,null);

            }
            final Holder holder = new Holder(view);
            holder.swipe.setOnSwipeListener(new SwipeListener() {
                @Override
                public void onOpen(int pos) {
                    swipeLayout.setMyTouch(true);
                }

                @Override
                public void onClose(int pos,boolean asbClosed) {
                    if(asbClosed)
                        swipeLayout.setMyTouch(false);
                }

                @Override
                public void onSwipe(float percent) {

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.itemView.getContext(),holder.getLayoutPosition()+"",Toast.LENGTH_SHORT).show();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }


        @Override
        public int getItemViewType(int position) {
            if (position==0)
                return 1;
            else
            return 2;
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class Holder extends RecyclerView.ViewHolder{
            XSwipeLayout swipe;
            public Holder(View itemView) {
                super(itemView);
                swipe = (XSwipeLayout) itemView.findViewById(R.id.swipe_item);
            }
        }
    }
}
