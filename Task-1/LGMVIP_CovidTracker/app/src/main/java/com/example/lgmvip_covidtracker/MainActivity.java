package com.example.lgmvip_covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Hashtable<String, List<Model>> dataDict =new Hashtable<>();
    private Hashtable<String, List<Model>> dataDictChart =new Hashtable<>();
    private TestingAdapter testingAdapter;
    private Spinner stateSelectionSpin;
    private RecyclerView recyclerView;
    private ViewPager2 viewPager2;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        recyclerView=(RecyclerView) findViewById(R.id.TestingRV);

        viewPager2 = (ViewPager2) findViewById(R.id.ChartRV);


        stateSelectionSpin = (Spinner)findViewById(R.id.StateSelectView);
        stateSelectionSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String item = (String)parent.getItemAtPosition(pos);
                ShowData(item);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fetchStateData();

    }


    private void fetchStateData()
    {
        String url ="https://data.covid19india.org/state_district_wise.json";
        StringRequest request= new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response.toString());

                Iterator<String> iterStates = jsonObject.keys();
                List<CharSequence> StateList = new ArrayList<>();
                iterStates.next();

                while(iterStates.hasNext())
                {
                    int totalconfirmed =0;
                    int totaldeceased =0;
                    int totalrecovered =0;
                    int totalActive =0;

                    String StateName = iterStates.next();
                    List<Model> modelList = new ArrayList<>();
                    List<Model> TotalModal = new ArrayList<>();
                    JSONObject districtJSON =jsonObject.getJSONObject(StateName).getJSONObject("districtData");
                    Iterator<String> iterDist = districtJSON.keys();

                    while(iterDist.hasNext()) {

                            String district = iterDist.next();
                            JSONObject distDataJSON = districtJSON.getJSONObject(district);

                            int active = distDataJSON.getInt("active");
                            int confirmed = distDataJSON.getInt("confirmed");
                            int deceased = distDataJSON.getInt("deceased");
                            int recovered = distDataJSON.getInt("recovered");

                            totalActive += active;
                            totalconfirmed += confirmed;
                            totaldeceased += deceased;
                            totalrecovered += recovered;

                            Log.i("Confirmed", String.valueOf(totalconfirmed));
                            Log.i("Deceased", String.valueOf(totaldeceased));
                            Log.i("Recovered", String.valueOf(totalrecovered));
                            Log.i("Active", String.valueOf(totalActive));
                            modelList.add(new Model(StateName, district, active, confirmed, deceased, recovered));

                    }
                        TotalModal.add(new Model(StateName, "", totalActive, totalconfirmed, totaldeceased, totalrecovered));
                        dataDictChart.put(StateName,TotalModal);

                    dataDict.put(StateName,modelList);
                    StateList.add(StateName);
                }

                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, StateList);
                stateSelectionSpin.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            error.printStackTrace();
            //Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(request);
    }

    void ShowData(String selection)
    {
        testingAdapter = new TestingAdapter(dataDict.get(selection));
        recyclerView.setAdapter(testingAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        ChartAdapter chartAdapter =new ChartAdapter(dataDictChart.get(selection));
        viewPager2.setAdapter(chartAdapter);
        viewPager2.setClipToPadding(false);
    }

}