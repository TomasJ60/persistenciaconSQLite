package co.edu.unipiloto.persistenciaconsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import co.edu.unipiloto.persistenciaconsqlite.db.DBproyectos;

public class activity_mostratProyecto extends AppCompatActivity {

    private TextView TVproyecto, TVplaneador, TVcorreo, TVtipodeproyecto;
    private Button buttonRegistro, buttonBorrar;
    private DBproyectos dbproyectos;
    private ArrayList<Proyecto> proyectos;
    private ArrayList<String> proyectoc;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_proyecto);

        // Inicializar vistas
        TVproyecto = findViewById(R.id.TVproyecto);
        TVplaneador = findViewById(R.id.TVplaneador);
        TVcorreo = findViewById(R.id.TVcorreo);
        buttonRegistro = findViewById(R.id.buttonRegistro);
        buttonBorrar = findViewById(R.id.buttonBorrar);

        listView = findViewById(R.id.listView);

        dbproyectos = new DBproyectos(activity_mostratProyecto.this);

        // Mostrar el primer proyecto en los TextView
        mostrarProyecto();

        // Mostrar la lista de proyectos
        mostrarProyectosEnListView();

        // Manejar el registro de nuevos proyectos
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_mostratProyecto.this, activity_registroproyecto.class);
                startActivity(intent);
                finish();
            }
        });

        // Manejar la eliminación de proyectos
        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verificar si hay proyectos disponibles
                if (proyectos != null && !proyectos.isEmpty()) {
                    // Obtener el último proyecto
                    Proyecto ultimoProyecto = proyectos.get(proyectos.size() - 1);

                    // Eliminar el último proyecto de la base de datos
                    boolean eliminado = dbproyectos.eliminarProyecto(ultimoProyecto.getId());

                    if (eliminado) {
                        // Remover el proyecto de la lista local
                        proyectos.remove(proyectos.size() - 1);

                        // Mostrar el proyecto anterior o mensaje de no proyectos
                        if (!proyectos.isEmpty()) {
                            Proyecto proyectoAnterior = proyectos.get(proyectos.size() - 1);
                            TVproyecto.setText(proyectoAnterior.getNproyecto());
                            TVplaneador.setText(proyectoAnterior.getNombre());
                            TVcorreo.setText(proyectoAnterior.getEmail());
                        } else {
                            // No hay más proyectos, limpiar los TextView
                            TVproyecto.setText("No hay proyectos disponibles");
                            TVplaneador.setText("");
                            TVcorreo.setText("");
                        }

                        // Actualizar el ListView
                        mostrarProyectosEnListView();
                    } else {
                        // Mostrar un mensaje de error si no se pudo eliminar
                        Toast.makeText(activity_mostratProyecto.this, "Error al eliminar el proyecto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity_mostratProyecto.this, "No hay proyectos para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void mostrarProyecto() {
        // Obtener la lista de proyectos desde la base de datos
        proyectos = dbproyectos.mostrarInfodeUnProyecto();

        // Verificar si hay proyectos disponibles
        if (!proyectos.isEmpty()) {
            Proyecto ultimoProyecto = proyectos.get(proyectos.size() - 1); // Obtener el último proyecto

            // Actualizar los TextView con los datos del último proyecto
            TVproyecto.setText(ultimoProyecto.getNproyecto());
            TVplaneador.setText(ultimoProyecto.getNombre());
            TVcorreo.setText(ultimoProyecto.getEmail());

        } else {
            // No hay proyectos, limpiar los TextView
            TVproyecto.setText("No hay proyectos disponibles");
            TVplaneador.setText("");
            TVcorreo.setText("");
        }
    }

    private void mostrarProyectosEnListView() {
        proyectoc = dbproyectos.mostrarNombresProyectos();

        // Verificar si hay proyectos disponibles
        if (proyectoc != null && !proyectoc.isEmpty()) {
            // Crear un adaptador para el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, proyectoc);

            // Establecer el adaptador en el ListView
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay proyectos disponibles", Toast.LENGTH_SHORT).show();
        }
    }

}
