package com.example.jakmalltestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //    private Button click;
    private ListView listView;
    private MyListAdapter adapter;
    private Button addButton;
    private Button refreshButton;
    private ArrayList<String> stringArrayList = new ArrayList<String>();
    private ArrayList<String> shownStringArrayList = new ArrayList<String>();
    private ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
    private String dataString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        generateListContent();
        adapter = new MyListAdapter(MainActivity.this, R.layout.list_item, integerArrayList, shownStringArrayList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Here is the Joke");
                alertDialog.setMessage(shownStringArrayList.get(i));
                alertDialog.setPositiveButton("Very Funny!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "I'm glad you like it :)", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("Meh..", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "We will try to improve", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        refreshButton = findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

    }

    private void generateListContent(){
        stringArrayList.clear();
        integerArrayList.clear();

        try {
            dataString = new FetchData(this).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String prefix = "null";
        if(dataString.startsWith(prefix)){
            dataString = dataString.substring(prefix.length(), dataString.length());
        }
        try {
            JSONObject unparsedData = new JSONObject(dataString);
            Log.d("FetchUnparsedData", unparsedData.get("type").toString());
            JSONArray unparsedDataArray = (JSONArray) unparsedData.get("value");
            Log.d("GetchUnparsedDataArray", unparsedDataArray.get(0).toString());
            for (int i = 0; i < 5; i++){
                JSONObject parsedJSONData = (JSONObject) unparsedDataArray.get(i);
                stringArrayList.add(parsedJSONData.get("joke").toString());
                if(i < 3){
                    shownStringArrayList.add(stringArrayList.get(i));
                    integerArrayList.add(i+1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void reset(){
        shownStringArrayList.clear();
        integerArrayList.clear();

        for (int i = 0; i < 3; i++){
            shownStringArrayList.add(stringArrayList.get(i));
            integerArrayList.add(i+1);
        }
        addButton.setVisibility(View.VISIBLE);
        adapter = new MyListAdapter(MainActivity.this, R.layout.list_item, integerArrayList, shownStringArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addData(){
        if(shownStringArrayList.size() < 4){
            shownStringArrayList.add(stringArrayList.get(3));
            integerArrayList.add(4);
        } else if (shownStringArrayList.size() < 5){
            shownStringArrayList.add(stringArrayList.get(4));
            integerArrayList.add(5);
            addButton.setVisibility(View.GONE);
        }
        adapter = new MyListAdapter(MainActivity.this, R.layout.list_item, integerArrayList, shownStringArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
