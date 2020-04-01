package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        TextView text = findViewById(R.id.index);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bundle content = extras.getBundle("content");
            if (content != null) {
                String index = content.getString("index");
                text.setText("The index of the selected item is " + index);
            }
        }
    }
}
