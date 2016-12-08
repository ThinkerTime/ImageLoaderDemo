package com.techstar.imageloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.techstar.imageloaderdemo.utils.Constants;
import com.techstar.imageloaderdemo.utils.ImageLoaderUtil;

public class ImageGridActivity extends AppCompatActivity {

    private GridView mImage_gv;
    private String[] imageUrls; // 图片路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);
        mImage_gv = (GridView) findViewById(R.id.gv_image);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        imageUrls = bundle.getStringArray(Constants.IMAGES);
        mImage_gv.setAdapter(new ItemGridAdapter());
    }

    class ItemGridAdapter extends BaseAdapter {

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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.item_grid, parent, false);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_grid_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            ImageLoaderUtil.displayImage(viewHolder.image, imageUrls[position]);

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

    }

}
