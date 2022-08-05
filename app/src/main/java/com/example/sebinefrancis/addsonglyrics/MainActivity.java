package com.example.sebinefrancis.addsonglyrics;

import static android.widget.Toast.LENGTH_LONG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

//    TextView typeSong;
//    private List<NotesBuilder> notesList = new ArrayList<>();
//    private NotesAdapter nAdapter;
//    private RecyclerView notesRecycler;
//    PropertyReader propread = new PropertyReader();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        String str_result;
        ArrayList<String> valueList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_category_list);
        try {
           // setContentView(R.layout.splash);
            str_result = new RetrieveFeedTask(this).execute().get();
            Log.e("sebine", str_result);
        } catch (InterruptedException e) {
            Log.e("sebine", e.toString());
        } catch (ExecutionException e) {
            Log.e("sebine", e.toString());
        }


        File f = getFilesDir();
        File[] files = f.listFiles();
        if (files != null) {
            for (File inFile : files) {
                if (inFile.isDirectory()) {
                    valueList.add(inFile.getName());
                }
            }
        }
        if (valueList.size() > 0){
            findViewById(R.id.nointernet).setVisibility(View.INVISIBLE);
            Collections.swap(valueList, valueList.indexOf("ALL"), 0);
        //Collections.sort(valueList.subList(1, valueList.size()));
            Collections.sort(valueList.subList(1, valueList.size()), String::compareToIgnoreCase);

        /*Collections.sort(valueList, new Comparator<String>()
        {
            int compare(String o1, String o2)
            {
                if (o1.equals("general"))
                    return -1;
                if (o2.equals("general"))
                    return 1;
                return o1.compareTo(o2);
            }
        });*/
       /* valueList.add("ALL");
        valueList.add("ACTION");
        valueList.add("FOCUS");*/
        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item1, valueList);
        final ListView lv = (ListView) findViewById(R.id.songcategoryview);
        lv.setAdapter(adapter);
            //<-- Error1
            lv.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
                Intent intent = new Intent();
                intent.setClassName(getPackageName(), getPackageName() + ".SongCategoryList");
                intent.putExtra("selected", lv.getAdapter().getItem(arg2).toString());
                startActivity(intent);
            });

           /* String configPath = getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String currVer = String.valueOf(pInfo.versionCode);
                String newVer = propread.getPropValues(configPath, "version");
                if(!currVer.equals(newVer)){
                    DialogInterface.OnClickListener updDialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    try {
                                        String configPath = getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
                                        String updateUrl= null;
                                        updateUrl = propread.getPropValues(configPath, "updateurl");
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(updateUrl));
                                        startActivity(i);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Update to latest version?").setPositiveButton("UPDATE", updDialogClickListener)
                            .setNegativeButton("LATER", updDialogClickListener).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
    }else{
            TextView tv = (TextView)findViewById(R.id.nointernet);
            tv.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "Please connect Internet for OneTime Song Data download",
                    LENGTH_LONG).show();
        }

    }

//    public TextView title, content;

//    private void setClickActions(){
//        title = (TextView) findViewById(R.id.title);
//       // content = (TextView) findViewById(R.id.content);
//        title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "opening this one", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                String filename= String.valueOf(((TextView)view).getText());
//                MainActivity t = new MainActivity();
//                t.Open(filename);
//                Intent myIntent = new Intent(MainActivity.this, NoteSelect.class);
//                MainActivity.this.startActivity(myIntent);
//            }
//        });
//    }
//    private void prepareNotes() {
//        File directory;
//        directory = getFilesDir();
//        File[] files = directory.listFiles();
//        Arrays.sort(files, new Comparator<File>(){
//            @Override
//            public int compare(File file1, File file2){
//                return file1.getName().compareTo(file2.getName());
//            }
//        });
//        String theFile;
//        String showTitle;
//        for (int f = 0; f < files.length; f++) {
//
//            theFile = files[f].getName();
//            showTitle = theFile.substring(0,theFile.length()-4);
//            //theFile = "Note" + f + ".txt";
//            NotesBuilder note = new NotesBuilder(showTitle,""/*Open(theFile)*/);
//            notesList.add(note);
//        }
//
//    }
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
            Intent myIntent = new Intent(MainActivity.this, NoteSelect.class);
            MainActivity.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void Save() {
//        try {
//            File directory;
//            directory = getFilesDir();
//            File[] files = directory.listFiles();
//            String fileName = "Note"+String.valueOf(files.length+1)+".txt";
//            OutputStreamWriter out =
//                    new OutputStreamWriter(openFileOutput(fileName, 0));
//            out.write(typeSong.getText().toString());
//            out.close();
//            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
//        } catch (Throwable t) {
//            Toast.makeText(this, "Exception: " + t.toString(), LENGTH_LONG).show();
//        }
//    }
    public void createSamples(Songs song,String[] cat) {
        try {

            /*String fileName = "Note"+String.valueOf(i)+".txt";
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write("This is file : "+i);
            out.close();*/

            String title=song.title;
            String lyrics = song.lyrics;
            String category = song.category;

            String link = song.link;
            if(link!=null && !link.isEmpty() &&!link.startsWith("http")){
                link = "http://"+link;
            }
            lyrics = lyrics+"~%~"+link;
            String fileName = title+".txt";
            String dirPath;// = getFilesDir().getAbsolutePath();
            if(category!=null) {
                String[] songCats = category.split(",");
                for (String s : cat) {
                    for (String songCat : songCats) {
                        if (songCat.trim().equalsIgnoreCase(s.trim())) {
                            dirPath = getFilesDir().getAbsolutePath() + File.separator + s;
                            createFile(dirPath, fileName, lyrics);
                        }
                    }
                }
            }
           /* if(category!=null && category.contains("FOCUS")){
                dirPath = getFilesDir().getAbsolutePath()+ File.separator + "FOCUS";
                createFile(dirPath,fileName,lyrics);
            }*/
            dirPath = getFilesDir().getAbsolutePath()+ File.separator + "ALL";
            createFile(dirPath,fileName,lyrics);
            // Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t, LENGTH_LONG).show();
        }
    }

    public void createFile(String dirPath,String fileName,String lyrics) throws IOException {

        File projDir = new File(dirPath);
        if (!projDir.exists())
            projDir.mkdirs();
        // fileName = "action"+ File.separator +fileName;
        String filepath = dirPath+ File.separator +fileName;
        if(!FileExists(filepath)) {
            //OutputStreamWriter out = new OutputStreamWriter(openFileOutput(String.valueOf(new File(filepath)), 0));
            FileOutputStream fout = new FileOutputStream (new File(filepath));
            OutputStreamWriter out = new OutputStreamWriter(fout);
            out.write(lyrics);
            out.close();
            fout.flush();
            fout.close();
        }
    }

    public boolean FileExists(String fname){
       // File file = getBaseContext().getFileStreamPath(fname);
       // File file = new File(getBaseContext().getFilesDir() + "/book1/page2.html");
        File file = new File(fname);
        return file.exists();
    }
    public boolean FileExistsInRoot(String fname){
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
                Toast.makeText(this, "Exception: " + t.toString(), LENGTH_LONG).show();
            }
        }
        return content;
    }

//    public void  deleteData(){
//        File directory;
//        directory = getFilesDir();
//        try {
//            FileUtils.cleanDirectory(directory);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//       /* File[] files = directory.listFiles();
//        for(int i=0;i<files.length;i++){
//            files[i].delete();
//        }*/
//    }

/*    public void cleanDirectory() {
        File dir = getFilesDir();
        for (File file : dir.listFiles()) {
            if (!file.getName().equals("config.properties")) {
                //delete file
                file.delete();
            }

        }
    }*/
    public boolean deleteDirectory(File directory) {
        //File directory = getFilesDir();
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                    files[i].delete();
                } else {
                    if (!files[i].getName().equalsIgnoreCase("config.properties") &&
                            !files[i].getName().equalsIgnoreCase("songsdb.xml")) {
                        files[i].delete();
                    }
                }
            }
        }
        return true;
    }
}

class RetrieveFeedTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    List songs = new ArrayList<Songs>();
    String categories = "";

    private MainActivity myContextRef;
    private PropertyReader propread;
    /*Intent intent = myContextRef.getIntent();
    String name = intent.getStringExtra("selected");*/

    public RetrieveFeedTask(MainActivity myContextRef) {
        this.myContextRef = myContextRef;
    }
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(myContextRef, s, Toast.LENGTH_LONG).show();
        propread = new PropertyReader();
        String configPath = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
        try {
            PackageInfo pInfo = myContextRef.getPackageManager().getPackageInfo(myContextRef.getPackageName(), 0);
            String currVer = String.valueOf(pInfo.versionCode);
            String newVer = propread.getPropValues(configPath, "version");
            if(!currVer.equalsIgnoreCase(newVer)){
                DialogInterface.OnClickListener updDialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    String configPath = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
                                    String updateUrl = propread.getPropValues(configPath, "updateurl");
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(updateUrl));
                                    myContextRef.startActivity(i);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(myContextRef);
                builder.setMessage("Update to latest version?").setPositiveButton("UPDATE", updDialogClickListener)
                        .setNegativeButton("LATER", updDialogClickListener).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected String doInBackground(String... urls) {
        StringBuilder stringBuilder = new StringBuilder();
        if(isInternetAvailable()) {
            //String dbUrl = "https://drive.google.com/uc?export=download&id=0B3vwuJMPT37ncVdPd3FzSlhINzQ";
            String dbUrl =  "https://drive.google.com/uc?export=download&id=1MqkUKVMtZ3cnvCVZHtWC1JLEQe5RVUvy&confirm=t";
            boolean proceed = false;
            propread = new PropertyReader();
            boolean config = downConfig();
            boolean refresh = checkRefresh();
            String configPath = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
            Log.e("configPath", configPath);
            if(config){
                proceed = true;
            }else{
                String filePath = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
                File file = new File(filePath);
                if(file.exists()){
                    proceed = true;
                }
            }
            if(proceed){
                //String filePath = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
                try {
                    dbUrl = propread.getPropValues(configPath, "dburl");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            XmlPullParserFactory xmlFactoryObject = null;
            try {
                xmlFactoryObject = XmlPullParserFactory.newInstance();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            XmlPullParser xmlPullParser = null;
            try {
                xmlPullParser = xmlFactoryObject.newPullParser();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            InputStream is = null;
            String path = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"songsdb.xml";
            File songsdb = new File(path);
            boolean process_xml =false;
            if(refresh || !songsdb.exists()){
                stringBuilder.append("Downloading latest songs..!! ");
                URL url = null;
                try {
                    url = new URL(dbUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection http = null;
                try {
                    http = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                http.setDoInput(true);
                try {
                    http.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    is = http.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    OutputStream output = new FileOutputStream(songsdb);
                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = is.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    is.close();
                    http.disconnect();

                    process_xml = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(process_xml) {
                stringBuilder.append(". Processing downloaded latest songs..!! ");
                songsdb = new File(path);
                try {
                    is = new FileInputStream(songsdb);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    xmlPullParser.setInput(is, null);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                int eventType = 0;
                try {
                    eventType = xmlPullParser.getEventType();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        System.out.println("Start document");
                    } else if (eventType == XmlPullParser.START_TAG) {
                        System.out.println("Start tag " + xmlPullParser.getName());
                    } else if (eventType == XmlPullParser.END_TAG) {
                        System.out.println("End tag " + xmlPullParser.getName());
                    } else if (eventType == XmlPullParser.TEXT) {
                        System.out.println("Text " + xmlPullParser.getText());
                    }

                    String name = xmlPullParser.getName();

                    String version = "";
                    // Starts by looking for the entry tag
                    if (name != null && name.equals("categories")) {
                        try {
                            categories = Songs.readCategories(xmlPullParser);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        }
                    }
                    if (name != null && name.equals("song")) {
                        try {
                            songs.add(Songs.readSongs(xmlPullParser));
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        eventType = xmlPullParser.next();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //myContextRef.deleteData();
                myContextRef.deleteDirectory(myContextRef.getFilesDir());
                String[] cat = categories.split(",");
                for (int i = 0; i < songs.size(); i++) {
                    myContextRef.createSamples((Songs) songs.get(i), cat);
                }

                //downConfig();
            }else{
                stringBuilder = new StringBuilder("No new songs found..!! ");
            }
            return  stringBuilder.toString();
        }else{
            stringBuilder = new StringBuilder("No Internet connection. No check done for new songs ..! ");
            return stringBuilder.toString();
        }
    }
    String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public boolean downConfig(){

        URL url = null;
        try {
            //url = new URL("https://drive.google.com/uc?export=download&id=0B3vwuJMPT37nQUFCRkM0VHJYUlU");
            url = new URL("https://drive.google.com/uc?export=download&id=1zQ_fd0ky34K7WYPNlQT3utVPe-GXAQJY&confirm=t");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        http.setDoInput(true);
        try {
            http.connect();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        InputStream is = null;
        try {
            is = http.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
//        try{
//            is = new BufferedInputStream(url.openStream(), 8192);
//        } catch (IOException e){
//            e.printStackTrace();
//            return false;
//        }

        try {
            String path = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
            File oldConfig = new File(path);
            String path_new = path;
            if(oldConfig.exists()){
                path_new = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"configNew.properties";
            }
            File newConfig = new File(path_new);
            OutputStream output = new FileOutputStream(newConfig);
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = is.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            is.close();
            http.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

//        // This won't require any authorization
//        String path = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
//        InputStream in = null;
//        try {
//            //in = new URL("https://drive.google.com/uc?export=download&id=0B3vwuJMPT37nQUFCRkM0VHJYUlU").openStream();
//            in = new URL("https://drive.google.com/uc?export=download&id=1zQ_fd0ky34K7WYPNlQT3utVPe-GXAQJY").openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return true;
    }

    public boolean checkRefresh()  {
        boolean refresh = false;
        propread = new PropertyReader();
        String path = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"config.properties";
        String path_new = myContextRef.getFilesDir().getAbsolutePath()+ File.separator+"configNew.properties";
        File newConfig = new File(path_new);
        if(newConfig.exists()){
            try {
                String songs_count_new = propread.getPropValues(path_new, "refresh_songs");
                String songs_count_old = propread.getPropValues(path, "refresh_songs");
                if(!(songs_count_new.equalsIgnoreCase(songs_count_old))){
                    refresh = true;
                }
                File oldConfig = new File(path);
                if (oldConfig.exists()) {
                    if (oldConfig.delete()) {
                        System.out.println("file Deleted :" + path);
                        newConfig.renameTo(oldConfig);
                    } else {
                        System.out.println("file not Deleted :" + path);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                refresh = true;
            }
        } else {
            refresh = true;
        }

        return refresh;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name
            System.out.println(ipAddr);
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }


    }
}
