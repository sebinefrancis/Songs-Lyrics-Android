package com.example.sebinefrancis.addsonglyrics;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sebine Francis on 28/08/2017.
 */

public class SongCategoryList extends AppCompatActivity {


    TextView typeSong;
    private List<NotesBuilder> notesList = new ArrayList<>();
    private List<NotesBuilder> allSongsList = new ArrayList<>();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;
    private EditText searchField;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("selected");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        notesRecycler = (RecyclerView) findViewById(R.id.notes);
        try {
            setTitle(name);
            //getSupportActionBar().setTitle(name);
        }catch(Exception e){

        }
        prepareNotes(name);
        nAdapter = new NotesAdapter(notesList,getApplicationContext());
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(notesRecycler.getContext(),
                mLayoutManager.getOrientation());
        notesRecycler.addItemDecoration(dividerItemDecoration);

        /*searchField = (EditText) findViewById(R.id.search);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // filter your list from your input
                String text = searchField.getText().toString().toLowerCase(Locale.getDefault());
                nAdapter.filter(text);
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });*/
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setLayoutParams(new androidx.appcompat.widget.Toolbar.LayoutParams(Gravity.RIGHT));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nAdapter.filter(newText);
                return true;
            }
        });

    }


    public TextView title, content;
    private void setClickActions(){
        title = (TextView) findViewById(R.id.title);
       // content = (TextView) findViewById(R.id.content);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "opening this one", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String filename= String.valueOf(((TextView)view).getText());
                MainActivity t = new MainActivity();
                t.Open(filename);
                Intent myIntent = new Intent(SongCategoryList.this, NoteSelect.class);
                SongCategoryList.this.startActivity(myIntent);

                /*Intent myIntent = new Intent(this, NoteSelect.class);
                MainActivity.this.startActivity(myIntent);*/
            }
        });
    }
    private void prepareNotes(String name) {
        File directory;
        directory = getFilesDir();
        File fileDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+name);
        File[] files = fileDirectory.listFiles();
        Arrays.sort(files, new Comparator<File>(){
            @Override
            public int compare(File file1, File file2){
                return file1.getName().compareToIgnoreCase(file2.getName());
            }
        });
        String theFile;
        String showTitle;
        for (int f = 0; f < files.length; f++) {

            theFile = files[f].getName();
            showTitle = theFile.substring(0,theFile.length()-4);
            //theFile = "Note" + f + ".txt";
            NotesBuilder note = new NotesBuilder(showTitle,""/*Open(theFile)*/);
            notesList.add(note);
            //allSongsList.add(note);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(SongCategoryList.this, NoteSelect.class);
            SongCategoryList.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Save() {
        try {
            File directory;
            directory = getFilesDir();
            File[] files = directory.listFiles();
            String fileName = "Note"+String.valueOf(files.length+1)+".txt";
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(typeSong.getText().toString());
            out.close();
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void createSamples(Songs song) {
        try {

            /*String fileName = "Note"+String.valueOf(i)+".txt";
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write("This is file : "+i);
            out.close();*/
            String title=song.title;
            String lyrics = song.lyrics;
            String fileName = title+".txt";
            if(!FileExists(fileName)) {
                OutputStreamWriter out =
                        new OutputStreamWriter(openFileOutput(fileName, 0));
                out.write(lyrics);
                out.close();
            }

            // Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public boolean FileExists(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public String Open(String fileName) {
        String content = "";
        if (FileExists(fileName)) {
            try {
                InputStream in = openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    } in .close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    public void  deleteData(){
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        for(int i=0;i<files.length;i++){
            files[i].delete();
        }
    }
}
