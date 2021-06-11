package com.mmomeni.notepad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private EditText titleB;
    private EditText descB;
    TextView visibletxt;
    TextView colortxt;
    TextView sizetxt;

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        layout = findViewById(R.id.layout);

        titleB = findViewById(R.id.titleBox);
        descB = findViewById(R.id.DescBox);

        descB.setMovementMethod(new ScrollingMovementMethod());
        descB.setTextIsSelectable(true);

        Intent intent = getIntent();
        String text1 = intent.getStringExtra("title");
        String text2 = intent.getStringExtra("description");
        titleB.setText(text1);
        descB.setText(text2);

        /* NEW VARIABLES FOR SETTINGS*/
        visibletxt = (TextView)findViewById(R.id.DescBox);
        colortxt = (TextView)findViewById(R.id.DescBox);
        sizetxt = (TextView)findViewById(R.id.DescBox);

/*
        CheckBox cb = new CheckBox(this);
        //LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //cb.setLayoutParams(p);
        cb.setText("Text");
        cb.setId(-1);
        cb.setText("Text");
        cb.setEnabled(true);
        cb.setVisibility(View.VISIBLE);
        layout.addView(cb);

 */
        setupSharedPreferences();

    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void addCheckbox (View v){

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){ //this is the only code we have for menues
        //the menu we pass here is the actual menu we have made in layout
        //inflating means to build live objects
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_save:
                saveAndExit(); // This closes the current activity, returning us to the original activity
                // Toast.makeText(this, "hahaha", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(EditActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void saveAndExit (){
        Intent data = new Intent();
        data.putExtra("title", titleB.getText().toString());
        data.putExtra("description", descB.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save data");
        builder.setMessage("Do you want to save this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                saveAndExit();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*ADDING NEW SETTINGS METHODS*/

    private void setTextVisible(boolean display_text) {
        if (display_text == true) {
            visibletxt.setVisibility(View.VISIBLE);
        } else {
            visibletxt.setVisibility(View.INVISIBLE);
        }
    }

    private void changeTextColor(String pref_color_value) {
        Log.d("Notepad", pref_color_value);
        if (pref_color_value.equals("red")) {
            titleB.setTextColor(Color.RED);
            descB.setTextColor(Color.RED);
        } else if(pref_color_value.equals("green")) {
            titleB.setTextColor(Color.GREEN);
            descB.setTextColor(Color.GREEN);
        } else if(pref_color_value.equals("black")) {
            titleB.setTextColor(Color.BLACK);
            descB.setTextColor(Color.BLACK);
        } else if(pref_color_value.equals("cyan")) {
            titleB.setTextColor(Color.CYAN);
            descB.setTextColor(Color.CYAN);
        } else if(pref_color_value.equals("gray")) {
            titleB.setTextColor(Color.GRAY);
            descB.setTextColor(Color.GRAY);
        } else if(pref_color_value.equals("magenta")) {
            titleB.setTextColor(Color.MAGENTA);
            descB.setTextColor(Color.MAGENTA);
        } else {
            titleB.setTextColor(Color.BLUE);
            descB.setTextColor(Color.BLUE);
        }
    }

    private void changeTextSize(Integer i) {
        titleB.setTextSize(i);
        descB.setTextSize(i);
    }

    private void loadColorFromPreference(SharedPreferences sharedPreferences) {
        Log.d("Notepad",sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
        changeTextColor(sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
    }

    private void loadSizeFromPreference(SharedPreferences sharedPreferences) {
        Integer minSize = Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_size_key), "16.0"));
        changeTextSize(minSize);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("display_text")) {
            setTextVisible(sharedPreferences.getBoolean("display_text",true));
        }
        if (key.equals("color")) {
            loadColorFromPreference(sharedPreferences);
        }
        if (key.equals("size")) {
            loadSizeFromPreference(sharedPreferences);
        }


    }
}