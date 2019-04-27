package com.example.mark6.CustomList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mark6.R;

import java.util.ArrayList;
import java.util.List;

public class CustomList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);
        createData();
    }

    private void createData() {
        ArrayList<DataModel> dataList = new ArrayList<>();
        dataList.add(new DataModel("Gaurika","28","5"));
        dataList.add(new DataModel("Shreejit","31","6"));
        dataList.add(new DataModel("Subrata","55","25"));
        dataList.add(new DataModel("Abhijit","25","2"));
        dataList.add(new DataModel("Girija ","65","28"));

        CustomAdapter adapter = new CustomAdapter(dataList,getApplicationContext());

        ListView listView = (ListView)findViewById(R.id.customList);
        listView.setAdapter(adapter);
    }
}
