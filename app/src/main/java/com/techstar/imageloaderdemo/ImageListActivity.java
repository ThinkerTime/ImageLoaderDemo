package com.techstar.imageloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.techstar.imageloaderdemo.utils.Constants;
import com.techstar.imageloaderdemo.utils.ImageLoaderUtil;

public class ImageListActivity extends AppCompatActivity {

    private ListView mImage_lv;
    private String[] imageUrls; // 图片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        mImage_lv = (ListView) findViewById(R.id.lv_image);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        imageUrls = bundle.getStringArray(Constants.IMAGES);
        mImage_lv.setAdapter(new ItemListAdapter());
        mImage_lv.setOnScrollListener(new PauseOnScrollListener(ImageLoaderUtil.getImageLoaderInstance(), true, true)); // 设置滚动时不加载图片


        mImage_lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_FLING == scrollState) {
                    Log.d("data","暂停");
                } else {
                    Log.d("data","恢复");
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }


    class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return imageUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_list, null);
                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView) convertView
                        .findViewById(R.id.iv_image);
                viewHolder.text = (TextView) convertView
                        .findViewById(R.id.tv_introduce);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.image.setTag(imageUrls[position]);

            if(viewHolder.image.getTag().equals(imageUrls[position])){
                /**
                 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
                 * DisplayImageOptions配置文件
                 */
                ImageLoaderUtil.displayImage(viewHolder.image,imageUrls[position]);
                viewHolder.text.setText("Item " + (position + 1)); // TextView设置文本
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public TextView text;
        }

    }
}
