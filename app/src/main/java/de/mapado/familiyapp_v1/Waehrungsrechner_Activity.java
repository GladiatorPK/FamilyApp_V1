package de.mapado.familiyapp_v1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Patrick on 04.01.2018.
 */

public class Waehrungsrechner_Activity extends AppCompatActivity {
    String [] WaehrungenAktualisiert;
    ArrayAdapter<String> waehrungsadapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waehrungsrechner);

        String [] WaehrungenAktualisiert = {
                "USD: 1.2045",
                "JPY: 136.45",
                "BGN: 1.9558",
                "CZK: 25.594",
                "DKK: 7.4459",
                "GBP: 0.88883",
                "HUF: 308.77",
                "PLN: 4.1554",
                "RON: 4.6351",
                "SEK: 9.8318",
                "CHF: 1.1757",
                "NOK: 9.7418",
                "HRK: 7.4350",
                "RUB: 68.7724",
                "TRY: 4.5127",
                "AUD: 1.5361",
                "BRL: 3.9057",
                "CAD: 1.5068",
                "CNY: 7.8151",
                "HKD: 9.4188",
                "IDR: 16164.39",
                "ILS: 4.1402",
                "INR: 76.3290",
                "KRW: 1281.20",
                "MXN: 23.3267",
                "MYR: 4.8180",
                "NZD: 1.6837",
                "PHP: 60.065",
                "SGD: 1.5993",
                "THB: 38.773",
                "ZAR: 14.8886"
        };

      // Erzeugen einer Instanz von HoleDatenTask
       HoleDatenTask holeDatenTask = new HoleDatenTask();
        holeDatenTask.execute();

    Spinner spinner1 = (Spinner) findViewById(R.id.spinner_waehrungsrechner1);
    Spinner spinner2 = (Spinner) findViewById(R.id.spinner_waehrungsrechner2);
    ArrayAdapter<String> waehrungsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, WaehrungenAktualisiert);
    spinner1.setAdapter(waehrungsadapter);
    spinner2.setAdapter(waehrungsadapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_waehrungsrechner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_daten_aktualisieren) {

            HoleDatenTask holeDatenTask = new HoleDatenTask();
            holeDatenTask.execute();


            Spinner spinner1 = (Spinner) findViewById(R.id.spinner_waehrungsrechner1);
            Spinner spinner2 = (Spinner) findViewById(R.id.spinner_waehrungsrechner2);
            ArrayAdapter<String> waehrungsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, WaehrungenAktualisiert);
            spinner1.setAdapter(waehrungsadapter);
            spinner2.setAdapter(waehrungsadapter);



            Toast.makeText(this, "Aktualisieren wurde geklickt.",
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }




    // Innere Klasse HoleDatenTask führt den asynchronen Task auf eigenem Arbeitsthread aus
    public class HoleDatenTask extends AsyncTask<String, Integer, String[]> {

        private final String LOG_TAG = HoleDatenTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... strings) {

            String anfrageString = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml?28e492ad50b7ae676ecadd7550df94cc";

            Log.v(LOG_TAG, "Anfrage-String: " + anfrageString);

            // Die URL-Verbindung und der BufferedReader, werden im finally-Block geschlossen
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            // In diesen String speichern wir die Währungsinformationen im XML-Format
            String waehrungsdatenXmlString = "";

            try {
                URL url = new URL(anfrageString);

                // Aufbau der Verbindung zur european central bank
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                if (inputStream == null) { // Keinen Währungsdaten-Stream erhalten, daher Abbruch
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    waehrungsdatenXmlString += line + "\n";
                }
                if (waehrungsdatenXmlString.length() == 0) { // Keine Währungsdaten ausgelesen, Abbruch
                    return null;
                }
                Log.v(LOG_TAG, "Währungsdaten XML-String: " + waehrungsdatenXmlString);
                publishProgress(1,1);

            } catch (IOException e) { // Beim Holen der Daten trat ein Fehler auf, daher Abbruch
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            // Hier parsen wir die XML Aktiendaten

            return leseXmlWaehrungsdatenAus(waehrungsdatenXmlString);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            // Auf dem Bildschirm geben wir eine Statusmeldung aus, immer wenn
            // publishProgress(int...) in doInBackground(String...) aufgerufen wird
            Toast.makeText(getApplicationContext(), values[0] + " von " + values[1] + " geladen",
                    Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(String[] strings) {

            // Wir löschen den Inhalt des ArrayAdapters und fügen den neuen Inhalt ein
            // Der neue Inhalt ist der Rückgabewert von doInBackground(String...) also
            // der StringArray gefüllt mit Beispieldaten
           if (strings != null) {
                WaehrungenAktualisiert = strings;
            }
           // Hintergrundberechnungen sind jetzt beendet, darüber informieren wir den Benutzer
            Toast.makeText(getApplicationContext(), "Währungsdaten vollständig geladen!",
                    Toast.LENGTH_SHORT).show();
 //           mSwipeRefreshLayout.setRefreshing(false);
      }

        public String[] leseXmlWaehrungsdatenAus(String xmlString) {



           Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlString));
                doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                Log.e(LOG_TAG,"Error: " + e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e(LOG_TAG,"Error: " + e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e(LOG_TAG,"Error: " + e.getMessage());
                return null;
            }

            Element xmlWaehrungsdaten = doc.getDocumentElement();
            NodeList waehrungsListe = xmlWaehrungsdaten.getElementsByTagName("Cube");


            int anzahlWaehrungen = waehrungsListe.getLength();

            String[] ausgabeArray = new String[anzahlWaehrungen];
            ausgabeArray [0] = "Letzte Aktualisierung: " + waehrungsListe.item(1).getAttributes().item(0).getNodeValue();
           Log.v(LOG_TAG,"XML Output:" + ausgabeArray[0]);

            for( int i=2; i<anzahlWaehrungen; i++ ) {
                ausgabeArray [i - 1] = waehrungsListe.item(i).getAttributes().item(0).getNodeValue() + " " + waehrungsListe.item(i).getAttributes().item(1).getNodeValue();
                Log.v(LOG_TAG,"XML Output:" + ausgabeArray[i-1]);
            }
            return ausgabeArray;


    }

    }

}
