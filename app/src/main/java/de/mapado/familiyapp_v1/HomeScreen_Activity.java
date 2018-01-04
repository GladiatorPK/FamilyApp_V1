package de.mapado.familiyapp_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Patrick on 02.01.2018.
 */

public class HomeScreen_Activity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


         String [] ToolList = {
                "Taschenrechner",
                "Einkaufsliste",
                "Forum",
                "Terminkalender",
                "Währungsrechner",
                "Weihnachtswürfel",
                };

        List<String> ToolListString = new ArrayList<>(Arrays.asList(ToolList));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ToolListString);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(HomeScreen_Activity.this, "Feld " + position +  " wurde angeklickt." ,
                        Toast.LENGTH_SHORT).show();

                switch (position){
                    case 5: Shuffle_Activity_aufruf();
                    break;
                    case 4: Waehrungsrechner_Activity_aufruf();
                    break;
                  //  default: return;

                }

        }
        });

    }

    private void Shuffle_Activity_aufruf() {
        Intent Shuffle = new Intent(this, Shuffle_Activity.class);
        startActivity(Shuffle);

    }

    private void Waehrungsrechner_Activity_aufruf() {
        Intent Waehrungsrechner = new Intent(this, Waehrungsrechner_Activity.class);
        startActivity(Waehrungsrechner);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
                Toast.makeText(HomeScreen_Activity.this, "Einstellungen wurde geklickt." ,
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }

    }




