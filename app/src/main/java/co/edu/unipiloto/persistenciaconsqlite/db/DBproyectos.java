package co.edu.unipiloto.persistenciaconsqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import co.edu.unipiloto.persistenciaconsqlite.Proyecto;

public class DBproyectos extends DBHelper{

    Context context;

    public DBproyectos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long crearproyecto(String nombreproyecto, String nombre, String email, String direccion, String localidad, String tipodeproyecto) {
        long id = 0;

        // Verificar si el proyecto ya existe
        if (proyectoExistente(nombreproyecto, email)) {
            return -1; // Retornar -1 para indicar que no se pudo crear el proyecto
        }

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombreproyecto", nombreproyecto);
            values.put("nombre", nombre);
            values.put("email", email);
            values.put("direccion", direccion);
            values.put("localidad", localidad);
            values.put("tipodeproyecto", tipodeproyecto);

            id = db.insert(TABLE_PROYECTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }
        return id;
    }


    public boolean proyectoExistente(String nombreproyecto, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Consultar la base de datos para verificar si ya existe un proyecto con el mismo nombre y correo
            String query = "SELECT COUNT(*) FROM " + TABLE_PROYECTOS + " WHERE nombreproyecto = ? AND email = ?";
            cursor = db.rawQuery(query, new String[]{nombreproyecto, email});

            if (cursor.moveToFirst()) {
                // Si el conteo es mayor que cero, el proyecto ya existe
                return cursor.getInt(0) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false;
    }



    public ArrayList<Proyecto> mostrarInfodeUnProyecto() {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        Proyecto proyecto;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_PROYECTOS + " ORDER BY nombre ASC", null);

        if (cursor.moveToFirst()) {
            do {
                proyecto = new Proyecto();
                proyecto.setId(cursor.getInt(0));
                proyecto.setNproyecto(cursor.getString(1));
                proyecto.setNombre(cursor.getString(2));
                proyecto.setEmail(cursor.getString(3));
                listaProyectos.add(proyecto);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listaProyectos;
    }

    public boolean eliminarProyecto(int id) {

        boolean correcto = false;

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PROYECTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public ArrayList<String> mostrarNombresProyectos() {
        ArrayList<String> listaProyectos = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_PROYECTOS, null);

            if (cursor.moveToFirst()) {
                do {
                    // Solo agregar el nombre del proyecto a la lista
                    listaProyectos.add(cursor.getString(1)); // Segunda columna: nombre del proyecto
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return listaProyectos;
    }



}
