package ga.ucode.fasttools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    SharedPreferences savedNotes;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        savedNotes = getApplicationContext().getSharedPreferences("ga.ucode.fasttools", Context.MODE_PRIVATE);

        ListView listView = findViewById(R.id.notesListView);

        HashSet<String> set = (HashSet<String>) savedNotes.getStringSet("notes", null);

        if (set != null) {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), NotesEditorActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(NotesActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set = new HashSet<>(NotesActivity.notes);
                                savedNotes.edit().putStringSet("notes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
        gridLayout = findViewById(R.id.notesGridView);
    }

    public void addnotes (View view) {
        Intent i = new Intent(NotesActivity.this,NotesEditorActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(gridLayout,"notesCardTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(NotesActivity.this, pairs);

        startActivity(i, options.toBundle());
    }
}
