package com.example.bubt.MedScape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorDetails extends AppCompatActivity implements AdapterView.OnItemClickListener {
    RecyclerView listView;
    DoctorAdapter adapter;
    String[] title;
    String[] description;
    String[] phone;

    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();
    TextView PhoneTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        title = new String[]{"Dr. Rashimul Haque", "Dr. Raivee", "Dr. Sabbir Ahmed ", "Dr. Mohammad Rofiqul Islam", "Dr. Rama Biswas", "Dr. Tanjima Afroj", "Dr. Didar", "Dr. Tamanna Haque"};
        description = new String[]{"Head, Dept of Neuromedicine", "Medicine specialist", "Medicine specialist", "Orthopedic Surgeon", "MBBS (DMC) FCPS (Medicine) MD (Neuromedicine)", "Physiotherapist", "Medicine specialist", "Neuromedicine"};
        phone = new String[]{"01770113937", "01726959864", "01915864797", "01711185867", "01840581369", "01791160876", "01633731000", "01767222229"};
        icon = new int[]{R.drawable.rashimul, R.drawable.raivee, R.drawable.sabbir, R.drawable.morshed, R.drawable.rama, R.drawable.tanjima, R.drawable.didar, R.drawable.tamanna};

        listView = findViewById(R.id.listViewId);
        PhoneTV = findViewById(R.id.mainPhone);
        listView.setHasFixedSize(true);
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for (int i = 0; i < title.length; i++) {
            Model model = new Model(title[i], description[i], icon[i], phone[i]);
            arrayList.add(model);
        }

        //pass the result to listviewAdapter
        adapter = new DoctorAdapter(arrayList);

        //bind the adapter to the list view
        listView.setAdapter(adapter);


        adapter.setOnCallListener(new DoctorAdapter.OnCallListener() {
            @Override
            public void onCallClicked(Model model) {
                Phonecall(model);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (!s.isEmpty()) {
                    adapter.getFilter().filter(s);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_seting) {

            //do your sunctionality here

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Doctor List item clicked
    }


    public void Phonecall(Model model) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+model.getPhone()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
            return;
        }
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
