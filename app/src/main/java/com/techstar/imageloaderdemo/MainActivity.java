package com.techstar.imageloaderdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.techstar.imageloaderdemo.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onImageList(View view){
        Intent intent = new Intent(this, ImageListActivity.class);
        intent.putExtra(Constants.IMAGES, Constants.images);
        startActivity(intent);
    }

    public void onImageGrid(View view){
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(Constants.IMAGES, Constants.images);
        startActivity(intent);
    }

    public void onClearMemory(View view) {
        ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
        Toast.makeText(this, "清除内存缓存成功", Toast.LENGTH_SHORT).show();
    }

    public void onClearDisk(View view) {
        ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
        Toast.makeText(this, "清除本地缓存成功", Toast.LENGTH_SHORT).show();
    }

}
