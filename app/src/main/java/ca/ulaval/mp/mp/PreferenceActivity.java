package ca.ulaval.mp.mp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Gabriel_2 on 25-Apr-16.
 */
public class PreferenceActivity extends AppCompatActivity {
    private Button buttonSauvegarde;
    private Button buttonCancel;
    private EditText editTextTemperatureFroide;
    private EditText editTextTemperatureChaude;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        Bundle b = getIntent().getExtras();
        preferences = b.getParcelable("ca.ulaval.mp.mp.Preferences");

        buttonSauvegarde = (Button) findViewById(R.id.buttonSauvegarde);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        editTextTemperatureFroide = (EditText) findViewById(R.id.editTextTemperatureFroide);
        editTextTemperatureFroide.setText(preferences.getTemperatureFroid());
        editTextTemperatureChaude = (EditText) findViewById(R.id.editTextTemperatureChaude);
        editTextTemperatureChaude.setText(preferences.getTemperatureChaud());

        buttonSauvegarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifierCoherenceTemperatures(editTextTemperatureFroide.getText().toString(), editTextTemperatureChaude.getText().toString())) {
                    preferences.setTemperatureFroid(editTextTemperatureFroide.getText().toString());
                    preferences.setTemperatureChaud(editTextTemperatureChaude.getText().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Preferences", preferences);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PreferenceActivity.this);
                    builder.setTitle("Températures incohérentes");
                    builder.setMessage("Les températures doivent êtres numériques et la température froide doit être plus petite que la chaude");

                    builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });


    }

    private boolean verifierCoherenceTemperatures(String tempFroide, String tempChaude) {
        boolean temperaturesCoherentes = false;
        if (isNumeric(tempFroide) && isNumeric(tempChaude)){
            double tempFroideDouble = Double.parseDouble(tempFroide);
            double tempChaudeDouble = Double.parseDouble(tempChaude);
            if (tempFroideDouble < tempChaudeDouble){
                temperaturesCoherentes = true;
            }
        }

        return temperaturesCoherentes;
    }

    private boolean isNumeric(String str){
        try{
            double d = Double.parseDouble(str);
        }
        catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
}

