package chapter.android.aweme.ss.com.homework;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {

    private static final String TAG = "yangshunyao";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countviews);
        View root = findViewById(R.id.root);
        int viewCount = getAllChildViewCount(root);
        Log.d(TAG, String.valueOf(viewCount));
        TextView showCount = findViewById(R.id.tv_center);
        showCount.setText(String.valueOf(viewCount));
    }

    public int getAllChildViewCount(View root) {
        int num = 0;
        if (null == root) {
            return 0;
        }
        if (root instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) root).getChildCount(); i++) {
                View view = ((ViewGroup) root).getChildAt(i);
                if (view instanceof ViewGroup) {
                    num += getAllChildViewCount(view);
                } else {
                    num++;
                }
            }
        }

        return num;
    }
}
