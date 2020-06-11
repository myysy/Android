package com.example.assignment6;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.beans.Note;
import com.example.assignment6.beans.Priority;
import com.example.assignment6.beans.State;
import com.example.assignment6.db.TodoContract;
import com.example.assignment6.db.TodoDbHelper;
import com.example.assignment6.operation.activity.DatabaseActivity;
import com.example.assignment6.operation.activity.DebugActivity;
import com.example.assignment6.operation.activity.SettingActivity;
import com.example.assignment6.ui.NoteListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD));

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            notesAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans
        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] select = {
                TodoContract.COLUMN_NAME_ID,
                TodoContract.COLUMN_NAME_DATE,
                TodoContract.COLUMN_NAME_STATE,
                TodoContract.COLUMN_NAME_CONTENT,
                TodoContract.COLUMN_NAME_PRIORITY,
        };
        try (Cursor cursor = db.query(
                TodoContract.TABLE_NAME,
                select,
                null,
                null,
                null,
                null,
                null)) {
            List<Note> notes = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                Note note = new Note(cursor.getLong(0));
                note.setDate(new Date(cursor.getLong(1)));
                note.setState(State.from(cursor.getInt(2)));
                note.setContent(cursor.getString(3));
                note.setPriority(Priority.from(cursor.getInt(4)));
                notes.add(note);
            }
            notes.sort((o1, o2) -> Comparator.<Integer>naturalOrder().reversed().compare(o1.getPriority().intValue, o2.getPriority().intValue));
            return notes;
        }
    }

    private void deleteNote(Note note) {
        // TODO 删除数据
        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = TodoContract.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {String.valueOf(note.id)};
        db.delete(TodoContract.TABLE_NAME, where, whereArgs);
    }

    private void updateNode(Note note) {
        // 更新数据
        ContentValues values = new ContentValues();
        values.put(TodoContract.COLUMN_NAME_DATE, note.getDate().getTime());
        values.put(TodoContract.COLUMN_NAME_STATE, note.getState().intValue);
        values.put(TodoContract.COLUMN_NAME_CONTENT, note.getContent());
        values.put(TodoContract.COLUMN_NAME_PRIORITY, note.getPriority().intValue);

        TodoDbHelper dbHelper = new TodoDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String where = TodoContract.COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {String.valueOf(note.id)};
        db.update(TodoContract.TABLE_NAME, values, where, whereArgs);
    }

}
