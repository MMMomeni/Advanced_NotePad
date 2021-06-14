package com.mmomeni.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public static final List<Note> noteList = new ArrayList<>();
    public static final List<Note> newList = new ArrayList<>();
    private RecyclerView recyclerView;
    private final String TITLEBAR = "Note_Pads";

    private EditText edittext;
    //private Button searchButton;


    //private final SearchEngine searchEngine = new SearchEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittext =findViewById(R.id.edittext);


        SoundPlayer.getInstance().setupSound(this, "tap", R.raw.tap_sound, false);

        loadFile();

        setTitle(TITLEBAR + " (" + noteList.size() + ")");

/*
        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> doFilter());

 */
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                doFilter(s.toString());
            }
        });



    }


    public void doFilter(String s) {
        if (s.equals("")){
            newList.clear();
            updateRecycler(null);
            return;
        }
        newList.clear();
        for (Note n : noteList) {
            if (n.getTitle().toLowerCase().contains(s.toLowerCase())) {
                newList.add(n);
            }
        }
        //Collections.sort(newList);

        updateRecycler(newList);
    }


    private void clearSearch() {
        //toFindText.setText("");
    }

    private void updateTitleNoteCount() {

        setTitle(TITLEBAR + " (" + noteList.size() + ")");
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        SoundPlayer.getInstance().start("tap");
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent = new Intent(this, EditActivity.class);
                startActivityForResult(intent, 1);

                return true;
            case R.id.menu_about:
                Intent a = new Intent(this, AboutActivity.class);
                startActivity(a);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                String text1 = data.getStringExtra("title");
                String text2 = data.getStringExtra("description");

                if (text1.isEmpty()) {
                    Toast.makeText(this, "Empty title returned", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();
                    noteList.add(0, new Note(text1, text2));
                    //updateRecycler();
                    recyclerView = findViewById(R.id.recycler);
                    NoteAdapter vh = new NoteAdapter(noteList, this);
                    recyclerView.setAdapter(vh);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    updateTitleNoteCount();
                }

                }
            }
    }

    public void updateRecycler(List<Note> newList){
        if (newList != null){
            recyclerView = findViewById(R.id.recycler);
            NoteAdapter vh = new NoteAdapter(newList, this);
            recyclerView.setAdapter(vh);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            updateTitleNoteCount();
            return;
        }

        recyclerView = findViewById(R.id.recycler);
        NoteAdapter vh = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(vh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTitleNoteCount();
    }

    @Override
    public void onClick(View v) {
        /*
        if (v.equals("clearButton")){
            toFindText.setText("");
        }

         */

        SoundPlayer.getInstance().start("tap");

        if (!newList.isEmpty()){
            int pos = recyclerView.getChildLayoutPosition(v);
            Note m = newList.get(pos);
            String text1 = m.getTitle();
            String text2 = m.getDesc();
            noteList.remove(pos);
            //Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
            Intent data = new Intent(this, EditActivity.class); // this is explicit intent
            data.putExtra("title", text1);
            data.putExtra("description", text2);
            startActivityForResult(data, 1);
        }

        else {
            int pos = recyclerView.getChildLayoutPosition(v);
            Note m = noteList.get(pos);
            String text1 = m.getTitle();
            String text2 = m.getDesc();
            noteList.remove(pos);
            //Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
            Intent data = new Intent(this, EditActivity.class); // this is explicit intent
            data.putExtra("title", text1);
            data.putExtra("description", text2);
            startActivityForResult(data, 1);
        }



        //EditActivity mn = new EditActivity();
       // mn(text1,text2);

    }

    @Override
    public boolean onLongClick(View v) {

        SoundPlayer.getInstance().start("tap");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Do you want to delete this data?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int pos = recyclerView.getChildLayoutPosition(v);
                noteList.remove(pos);
                //updateRecycler();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        //Toast.makeText(v.getContext(), "LONG " + m.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onPause() {
        saveFile();
        super.onPause();
    }

    @Override
    protected void onResume() {

        //updateRecycler();
        recyclerView = findViewById(R.id.recycler);
        NoteAdapter vh = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(vh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateTitleNoteCount();

        super.onResume();
    }

    public void saveFile() {
       // Log.d(TAG, "saveFile: ");

        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            writer.setIndent("  ");
            writer.beginArray();
            for (Note n : noteList) {
                writer.beginObject();
                writer.name("title").value(n.getTitle());
                writer.name("description").value(n.getDesc());
                writer.name("date").value(convertDateToString(n.getLastDate()));
                writer.endObject();
            }
            writer.endArray();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Log.d(TAG, "writeJSONData: " + e.getMessage());
        }


    }

    private void loadFile(){
        try {
            FileInputStream fis = getApplicationContext().openFileInput(getString(R.string.file_name));

            byte[] data = new byte[fis.available()];
            int loaded = fis.read(data);
            fis.close();
            String json = new String(data);
            //Create JSON arrah form string file content
            JSONArray noteArr = new JSONArray(json);
            for (int i = 0; i < noteArr.length(); i++){
                JSONObject nObj = noteArr.getJSONObject(i);

                // Access note data fields
                String title = nObj.getString("title");
                String text = nObj.getString("description");
                String dateMS = nObj.getString("date");

                //create Note and add to ArrayList
                Note n = new Note(title, text);
                n.setLastDate(convertStringToDate(dateMS));
                noteList.add(n);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private Date convertStringToDate(String timeStamp){
        //Log.d(TAG, "convertStringToDate: ");
        try{
            if(timeStamp != null){
                SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
                sdf.applyPattern("EEE MMM d, HH:mm a");
                return sdf.parse(timeStamp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String convertDateToString(Date timeStamp){
        //Log.d(TAG, "convertDateToString: ");
        try{
            if(timeStamp != null){
                SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
                sdf.applyPattern("EEE MMM d, HH:mm a");
                return sdf.format(timeStamp);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}



