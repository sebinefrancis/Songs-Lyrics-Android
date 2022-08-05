package com.example.sebinefrancis.addsonglyrics;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NoteSelect extends AppCompatActivity {

    private List<NotesBuilder> notesList = new ArrayList<>();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;
    TextView typeSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        try {
            setTitle(name.substring(0,name.length()-4));
        }catch(Exception e){
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
               // Save();
            }
        });*/

        typeSong = (TextView)findViewById(R.id.typeSong);
      //  typeSong.setMovementMethod(new ScrollingMovementMethod());
        String[] splitted = Open(name);
        typeSong.setText(splitted[0]);
        typeSong.setTextIsSelectable(true);
        typeSong.setTextIsSelectable(true);
        typeSong.setFocusable(true);
        typeSong.setFocusableInTouchMode(true);

        Button linkbtn = (Button) findViewById(R.id.linkbtn);
       if(splitted.length>1 && splitted[1]!=null){
           splitted[1]=splitted[1].replaceAll("\n","");
        }
        if(splitted.length>1 && splitted[1]!=null && !splitted[1].isEmpty()) {
            final String link = splitted[1];
            linkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    goToUrl(link);
                    // Save();
                }
            });
        }else{
            linkbtn.setVisibility(View.GONE);
        }

        Button copybtn = (Button) findViewById(R.id.copybtn);
        copybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Copied to clipboard", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, typeSong.getText());
                clipboard.setPrimaryClip(clip);
            }
        });


        Button sharebtn = (Button) findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = typeSong.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });


    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
    private void prepareNotes() {
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        String theFile;
        for (int f = 1; f <= files.length; f++) {
            theFile = "Note" + f + ".txt";
            String[] splitted = Open(theFile);
            NotesBuilder note = new NotesBuilder(theFile, splitted[0]);
            notesList.add(note);
        }

    }

    public String[] Open(String fileName) {
        String content = "";
        String[] splitted= null;
        try {

            File fileDirectory = new File(getFilesDir().getAbsolutePath()+File.separator+"ALL"+File.separator+fileName);
            FileInputStream in = new FileInputStream(fileDirectory);
            //InputStream in = openFileInput(fin);
            if ( in != null) {
                InputStreamReader tmp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                } in .close();

                content = buf.toString();
                 splitted=content.split("~%~");

            }
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

        return splitted;
    }
}
