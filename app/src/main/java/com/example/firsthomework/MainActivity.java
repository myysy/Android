package com.example.firsthomework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.util.Log;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "My Info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        Switch sch = findViewById(R.id.switch1);
        sch.setOnClickListener(this);
        CheckBox chA = findViewById(R.id.checkBox);
        CheckBox chB = findViewById(R.id.checkBox2);

        chA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                TextView text = findViewById(R.id.textView2);
                if (isChecked) {
                    text.setText(buttonView.getText() + "Check");
                    Log.i(TAG,buttonView.getText() + "Check");
                } else {
                    text.setText(buttonView.getText() + "UnCheck");
                    Log.i(TAG,buttonView.getText() + "UnCheck");
                }

            }
        });
        chB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                TextView text = findViewById(R.id.textView2);
                if (isChecked) {
                    text.setText(buttonView.getText() + "Check");
                    Log.i(TAG,buttonView.getText() + "Check");
                } else {
                    text.setText(buttonView.getText() + "UnCheck");
                    Log.i(TAG,buttonView.getText() + "UnCheck");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                TextView text = findViewById((R.id.textView));
                text.setText("hello");
                Log.i(TAG,"say hello");
                break;
            case R.id.switch1:
                ImageView img = findViewById((R.id.imageView));
                img.setVisibility(View.VISIBLE);
                Log.i(TAG,"show star");
                break;
            default:
                break;
        }
    }

}
