package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NotesEditorActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if (noteId != -1) {
            editText.setText(NotesActivity.notes.get(noteId));
        } else {
            NotesActivity.notes.add("");
            noteId = NotesActivity.notes.size() - 1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(noteId, String.valueOf(charSequence));
                NotesActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences savedNotes = getApplicationContext().getSharedPreferences("ga.ucode.fasttools", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(NotesActivity.notes);
                savedNotes.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
