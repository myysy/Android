package com.example.assignment6.ui;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment6.NoteOperator;
import com.example.assignment6.R;
import com.example.assignment6.beans.Note;
import com.example.assignment6.beans.Priority;
import com.example.assignment6.beans.State;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private final NoteListAdapter adapter;
    private final NoteOperator operator;

    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;

    public NoteViewHolder(NoteListAdapter adapter, @NonNull View itemView, NoteOperator operator) {
        super(itemView);
        this.adapter = adapter;
        this.operator = operator;

        checkBox = itemView.findViewById(R.id.checkbox);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
    }

    public void bind(final Note note) {
        this.updateBackground(note.getPriority());

        contentText.setText(note.getContent());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));

        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(note.getState() == State.DONE);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.updateContentText(isChecked);
            note.setState(isChecked ? State.DONE : State.TODO);
            operator.updateNote(note);
        });
        deleteBtn.setOnClickListener(v -> {
            this.adapter.delete(note);
            operator.deleteNote(note);
        });

        this.updateContentText(note.getState() == State.DONE);
    }
    
    private void updateBackground(Priority priority) {
        switch (priority) {
            case LOW:
                itemView.setBackgroundColor(Color.TRANSPARENT);
                break;
            case MEDIUM:
                itemView.setBackgroundColor(Color.GREEN);
                break;
            case HIGH:
                itemView.setBackgroundColor(Color.RED);
                break;
        }
    }

    private void updateContentText(Boolean isChecked) {
        if (isChecked) {
            contentText.setTextColor(Color.GRAY);
            contentText.setPaintFlags(contentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            contentText.setTextColor(Color.BLACK);
            contentText.setPaintFlags(contentText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
