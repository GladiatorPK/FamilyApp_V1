package de.mapado.familiyapp_v1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Patrick on 03.01.2018.
 */

public class Shuffle_Activity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle);

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
            Toast.makeText(this, "Einstellungen wurde geklickt." ,
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void shuffle_onClick(View view) {

            new CountDownTimer(1800, 300) {

                public void onTick(long millisUntilFinished) {

                    int zahl = (int) ((Math.random()) * 6 + 1);
                    switch (zahl) {
                        case 1:
                            ImageView img = (ImageView) findViewById(R.id.Image_Shuffle);
                            img.setImageResource(R.drawable.shuffle1);
                            break;
                        case 2:
                            ImageView img1 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img1.setImageResource(R.drawable.shuffle2);
                            break;
                        case 3:
                            ImageView img2 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img2.setImageResource(R.drawable.shuffle3);
                            break;
                        case 4:
                            ImageView img3 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img3.setImageResource(R.drawable.shuffle4);
                            break;
                        case 5:
                            ImageView img4 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img4.setImageResource(R.drawable.shuffle5);
                            break;
                        case 6:
                            ImageView img5 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img5.setImageResource(R.drawable.shuffle6);
                            break;
                        default:
                            ImageView img6 = (ImageView) findViewById(R.id.Image_Shuffle);
                            img6.setImageResource(R.drawable.shuffle1);

                    }
                }

                public void onFinish() {
                 //   Toast.makeText(HomeScreen_Activity.this, "Einstellungen wurde geklickt." ,
                  //          Toast.LENGTH_SHORT).show();
                }
            }.start();

    }

}

