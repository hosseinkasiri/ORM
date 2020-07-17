package com.example.orm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orm.model.DaoSession;
import com.example.orm.model.Note;
import com.example.orm.model.NoteDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_MainActivity";
    private EditText mNameEditText;
    private Button mSaveButton;
    private NoteDao noteDao;
    private RecyclerView mRecyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        DaoSession daoSession = (App.getApp()).getDaoSession();
         noteDao = daoSession.getNoteDao();
         addListeners();

         noteAdapter = new NoteAdapter(noteDao.loadAll());
         mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         mRecyclerView.setAdapter(noteAdapter);
    }
    private void findViews() {
        mNameEditText = findViewById(R.id.name_edit_text);
        mSaveButton = findViewById(R.id.save_button);
        mRecyclerView = findViewById(R.id.list_recycler_view);
    }
    private  void addListeners(){
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                Note note = new Note();
                note.setMName(name);
               long id = noteDao.insert(note);
               noteAdapter.setNotes(noteDao.loadAll());
               noteAdapter.notifyDataSetChanged();
               //Toast.makeText(MainActivity.this, String.valueOf(id),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class NoteHolder extends RecyclerView.ViewHolder{

        private TextView mNoteText , mNoteId;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            mNoteText = itemView.findViewById(R.id.note_text_text_view);
            mNoteId = itemView.findViewById(R.id.id_text_view);
        }

        public void bind(Note note){
            mNoteId.setText(note.getMId().toString());
            mNoteText.setText(note.getMName());
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder>{

        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        public void setNotes(List<Note> notes) {
            mNotes = notes;
        }

        @NonNull
        public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
           View view = layoutInflater.inflate(R.layout.list_note_item,parent,false);
           NoteHolder noteHolder = new NoteHolder(view);
            return noteHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
            holder.bind(mNotes.get(position));
        }

        @Override
        public int getItemCount() {
            return (int) noteDao.count();
        }
    }
}
