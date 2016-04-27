package ca.ulaval.mp.mp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int PREFERENCE_REQUEST = 1;
    private Button buttonPrevisions;
    private Button buttonPreferences;

    private TextView textViewTemperature;
    private TextView textViewConditionGenerale;
    private TextView textViewLocationActuelle;

    private ImageView imageViewAccessoire;
    private ImageView imageViewHaut;
    private ImageView imageViewBas;
    private ImageView imageViewSoulier;

    //defines
    private final String CONDITION_ENSOLEILLE = "Ensoleillé";
    private final String CONDITION_NUAGEUX = "Nuageux";
    private final String CONDITION_PLUIE = "Pluvieux";
    private final String CONDITION_NEIGE = "Neige";

    private static final String myPreferencesKey = "MyPrefs";
    private static final String tempFroideKey = "TempFroideKey";
    private static final String tempChaudeKey = "TempChaudeKey";
    private SharedPreferences sharedPreferences;

    //informations pour générer la page changent selon les selections de l'usager
    private Location location;
    private Preferences preferences = new Preferences();
    private String temperatureActuelle = "10";
    private String conditionGenerale = CONDITION_ENSOLEILLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        buttonPrevisions = (Button) findViewById(R.id.buttonPrevisions);
        buttonPreferences = (Button) findViewById(R.id.buttonPreferences);

        //TODO text 2 speech button

        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
        textViewTemperature.setText(temperatureActuelle.concat(" °C"));

        textViewConditionGenerale = (TextView) findViewById(R.id.textViewConditionGenerale);
        textViewConditionGenerale.setText(conditionGenerale);

        textViewLocationActuelle = (TextView) findViewById(R.id.textViewLocation);

        imageViewAccessoire = (ImageView) findViewById(R.id.imageViewAccessoire);
        imageViewHaut = (ImageView) findViewById(R.id.imageViewHaut);
        imageViewBas = (ImageView) findViewById(R.id.imageViewBas);
        imageViewSoulier = (ImageView) findViewById(R.id.imageViewSoulier);

        sharedPreferences = getSharedPreferences(myPreferencesKey, Context.MODE_PRIVATE);

        buttonPrevisions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrevisionActivity.class);
                intent.putExtra("android.location.Location", location);
                startActivity(intent);

            }
        });

        buttonPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
                intent.putExtra("ca.ulaval.mp.mp.Preferences", preferences);
                startActivityForResult(intent, PREFERENCE_REQUEST);

            }
        });

        chargerElementsVisuels();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if (requestCode == PREFERENCE_REQUEST){
                Bundle b = data.getExtras();
                Preferences nouvellesPreferences = b.getParcelable("Preferences");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(tempFroideKey, nouvellesPreferences.getTemperatureFroid());
                editor.putString(tempChaudeKey, nouvellesPreferences.getTemperatureChaud());
                editor.commit();
                chargerElementsVisuels();
            }
        }
    }

    private void chargerElementsVisuels(){
        chargerPreferences();
        textViewTemperature.setText(temperatureActuelle);
        selectionnerEtChargerVetements();
    }


    private void chargerPreferences(){
        preferences.setTemperatureFroid(sharedPreferences.getString(tempFroideKey, "0"));
        preferences.setTemperatureChaud(sharedPreferences.getString(tempChaudeKey, "15"));
    }

    private void selectionnerEtChargerVetements(){
        if (estTemperatureChaude()){
            if (estEnsoleille() || estNuageux()){
                imageViewAccessoire.setImageResource(R.drawable.chapeau);
                imageViewHaut.setImageResource(R.drawable.tshirt);
                imageViewBas.setImageResource(R.drawable.shorts);
                imageViewSoulier.setImageResource(R.drawable.souliers);
            }
            else if (estPluvieux()){
                imageViewAccessoire.setImageResource(R.drawable.parapluie);
                imageViewHaut.setImageResource(R.drawable.impermeable);
                imageViewBas.setImageResource(R.drawable.shorts);
                imageViewSoulier.setImageResource(R.drawable.bottes_pluie);

            }
        }
        else if (estTemperatureModeree()){
            if (estEnsoleille() || estNuageux()) {
                imageViewAccessoire.setImageResource(R.drawable.chapeau);
                imageViewHaut.setImageResource(R.drawable.manteau_leger);
                imageViewBas.setImageResource(R.drawable.jeans);
                imageViewSoulier.setImageResource(R.drawable.souliers);
            }
            else if (estPluvieux()){
                imageViewAccessoire.setImageResource(R.drawable.parapluie);
                imageViewHaut.setImageResource(R.drawable.manteau_leger);
                imageViewBas.setImageResource(R.drawable.jeans);
                imageViewSoulier.setImageResource(R.drawable.bottes_pluie);

            }
        }
        else if (estTemperatureFroide()){
            imageViewAccessoire.setImageResource(R.drawable.tuque);
            imageViewHaut.setImageResource(R.drawable.manteau_chaud);
            imageViewBas.setImageResource(R.drawable.jeans);
            imageViewSoulier.setImageResource(R.drawable.botte_hivers);
        }
    }

    private boolean estTemperatureFroide(){
        return Double.parseDouble(temperatureActuelle) < Double.parseDouble(preferences.getTemperatureFroid());
    }

    private boolean estTemperatureModeree(){
        return Double.parseDouble(temperatureActuelle) >= Double.parseDouble(preferences.getTemperatureFroid()) && Double.parseDouble(temperatureActuelle) <= Double.parseDouble(preferences.getTemperatureChaud());
    }

    private boolean estTemperatureChaude(){
        return Double.parseDouble(temperatureActuelle) > Double.parseDouble(preferences.getTemperatureChaud());
    }

    private boolean estEnsoleille(){
        return conditionGenerale.equals(CONDITION_ENSOLEILLE);
    }

    private boolean estNuageux(){
        return conditionGenerale.equals(CONDITION_NUAGEUX);
    }

    private boolean estPluvieux(){
        return conditionGenerale.equals(CONDITION_PLUIE);
    }

    private boolean estNeigeux(){
        return conditionGenerale.equals(CONDITION_NEIGE);
    }

}
