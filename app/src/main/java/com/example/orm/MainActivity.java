package com.example.orm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orm.model.DaoSession;
import com.example.orm.model.Note;
import com.example.orm.model.NoteDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_MainActivity";
    private EditText mNameEditText;
    private Button mSaveButton;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        DaoSession daoSession = (App.getApp()).getDaoSession();
         noteDao = daoSession.getNoteDao();
         addListeners();
    }
    private void findViews() {
        mNameEditText = findViewById(R.id.name_edit_text);
        mSaveButton = findViewById(R.id.save_button);
    }
    private  void addListeners(){
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                Note note = new Note();
                note.setMName(name);
               long id = noteDao.insert(note);
               //Toast.makeText(MainActivity.this, String.valueOf(id),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
