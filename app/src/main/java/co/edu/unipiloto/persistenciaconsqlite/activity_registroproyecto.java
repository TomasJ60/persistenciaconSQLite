package co.edu.unipiloto.persistenciaconsqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.edu.unipiloto.persistenciaconsqlite.db.DBHelper;
import co.edu.unipiloto.persistenciaconsqlite.db.DBproyectos;

public class activity_registroproyecto extends AppCompatActivity {


    TextView txtnombreproyecto, txtnombre, txtemail, txtdireccion, textExit;
    Spinner userTypeSpinner;
    RadioGroup radioGroupTipoProyecto;
    RadioButton radioGub, radioPriv, radioMix;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registroproyecto);

        txtnombreproyecto = findViewById(R.id.nameProyect);
        txtnombre = findViewById(R.id.namePlaneador);
        txtemail = findViewById(R.id.emailInput);
        txtdireccion = findViewById(R.id.addressInput);

        userTypeSpinner = findViewById(R.id.userTypeSpinner);

        textExit = findViewById(R.id.textExit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.localidades_proyectos,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        radioGroupTipoProyecto = findViewById(R.id.genderGroup);
        radioGub = findViewById(R.id.radioGub);
        radioPriv = findViewById(R.id.radioPriv);
        radioMix = findViewById(R.id.radioMix);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tipoUsuario = userTypeSpinner.getSelectedItem().toString();

                int selectedRadioId = radioGroupTipoProyecto.getCheckedRadioButtonId();
                String tipoProyecto = "";
                if (selectedRadioId == R.id.radioGub) {
                    tipoProyecto = "Gobierno";
                } else if (selectedRadioId == R.id.radioPriv) {
                    tipoProyecto = "Privado";
                } else if (selectedRadioId == R.id.radioMix) {
                    tipoProyecto = "Mixto";
                }



                DBproyectos dBproyectos = new DBproyectos(activity_registroproyecto.this);
                long id = dBproyectos.crearproyecto(txtnombreproyecto.getText().toString(),txtnombre.getText().toString(),txtemail.getText().toString(),txtdireccion.getText().toString(), tipoUsuario, tipoProyecto);

                if (id == -1){
                    Toast.makeText(activity_registroproyecto.this, "Error al guardar registro, ya se generado un proyecto con ese usuario", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(activity_registroproyecto.this, "Registro guardado", Toast.LENGTH_LONG).show();
                    limpiar();
                }

            }
        });

        textExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_registroproyecto.this, activity_mostratProyecto.class);
                startActivity(intent);
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void limpiar(){
        txtnombreproyecto.setText("");
        txtnombre.setText("");
        txtemail.setText("");
        txtdireccion.setText("");
    }


}

/*
button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(activity_registroproyecto.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if (db != null){
                    Toast.makeText(activity_registroproyecto.this, "Base de datos creada", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(activity_registroproyecto.this, "Error al crear base de datos", Toast.LENGTH_LONG).show();
                }
            }
        });
 */