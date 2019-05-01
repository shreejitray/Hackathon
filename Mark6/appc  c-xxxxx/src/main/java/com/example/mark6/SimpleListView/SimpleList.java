package com.example.mark6.SimpleListView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mark6.R;

public class SimpleList extends AppCompatActivity {

    String[] ataticArray = {"Android","IOS","Symbian"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.simple_list_view,ataticArray);

        ListView listView = (ListView) findViewById(R.id.simplelsit);
        listView.setAdapter(adapter);
    }
}
