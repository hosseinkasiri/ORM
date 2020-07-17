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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orm.model.DaoSession;
import com.example.orm.model.Note;
import com.example.orm.model.NoteDao;
import com.example.orm.model.User;
import com.example.orm.model.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_MainActivity";
    private EditText mNameEditText , mUsernameEditText, mUserIdEditText;
    private Button mSaveButton , mAddUserButton;
    private RadioButton mAdminRB , mGuestRb , mNormalRb;
    private NoteDao mNoteDao;
    private UserDao mUserDao;
    private RecyclerView mRecyclerView;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        DaoSession daoSession = (App.getApp()).getDaoSession();
        mNoteDao = daoSession.getNoteDao();
        mUserDao= daoSession.getUserDao();
        onClickListeners();
        noteAdapter = new NoteAdapter(mNoteDao.loadAll());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(noteAdapter);
    }

    private void onClickListeners() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString();
                Note note = new Note();
                note.setMName(name);
                note.setUserId(Long.valueOf(mUserIdEditText.getText().toString()));
                long id = mNoteDao.insert(note);
                noteAdapter.setNotes(mNoteDao.loadAll());
                noteAdapter.notifyDataSetChanged();
            }
        });

        mAddUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> users = mUserDao.queryBuilder()
                        .where(UserDao.Properties.Username.eq(mUsernameEditText.getText().toString()))
                        .list();

                if (users.size() > 0) {
                    Toast.makeText(MainActivity.this, "username is repetition", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                if (mAdminRB.isChecked())
                    user.setRole(User.Role.ADMIN);
                else if (mGuestRb.isChecked())
                    user.setRole(User.Role.GUEST);
                else
                    user.setRole(User.Role.NORMAL);
                user.setUsername(mUsernameEditText.getText().toString());
                long userId = mUserDao.insert(user);
                Log.i(TAG,"onClick user id : " + userId);
            }
        });
    }

    private void findViews() {
        mNameEditText = findViewById(R.id.name_edit_text);
        mSaveButton = findViewById(R.id.save_button);
        mRecyclerView = findViewById(R.id.list_recycler_view);
        mAddUserButton = findViewById(R.id.add_user_button);
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mUserIdEditText = findViewById(R.id.user_id_edit_text);
        mAdminRB = findViewById(R.id.admin_radio_button);
        mGuestRb = findViewById(R.id.guest_redio_button);
        mNormalRb = findViewById(R.id.normal_radio_button);
    }

    private class NoteHolder extends RecyclerView.ViewHolder{
        private TextView mNoteText , mNoteId , mUserText;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            mNoteText = itemView.findViewById(R.id.note_text_text_view);
            mNoteId = itemView.findViewById(R.id.id_text_view);
            mUserText = itemView.findViewById(R.id.user_text_View);
        }
        public void bind(Note note){
            mNoteId.setText(note.getMId().toString());
            mNoteText.setText(note.getMName());
            mUserText.setText(note.getUser().getUsername() + "(" + note.getUser().getRole() + ")");
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
            return (int) mNoteDao.count();
        }
    }
}
