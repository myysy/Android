package com.example.assignment6;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.beans.State;
import com.example.assignment6.db.TodoContract;
import com.example.assignment6.db.TodoDbHelper;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Spinner prioritySpinner;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        prioritySpinner = findViewById(R.id.priority_spinner);
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(v -> {
            CharSequence content = editText.getText();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(NoteActivity.this,
                        "No content to add", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean succeed = saveNote2Database(content.toString().trim(), prioritySpinner.getSelectedItemPosition());
            if (succeed) {
                Toast.makeText(NoteActivity.this,
                        "Note added", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
            } else {
                Toast.makeText(NoteActivity.this,
                        "Error", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean saveNote2Database(String content, int priority) {
        // TODO 插入一条新数据，返回是否插入成功
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(TodoContract.COLUMN_NAME_DATE, date.getTime());
        values.put(TodoContract.COLUMN_NAME_STATE, State.TODO.intValue);
        values.put(TodoContract.COLUMN_NAME_CONTENT, content);
        values.put(TodoContract.COLUMN_NAME_PRIORITY, priority);

        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(TodoContract.TABLE_NAME, null, values);
        return id != -1;
    }
}
