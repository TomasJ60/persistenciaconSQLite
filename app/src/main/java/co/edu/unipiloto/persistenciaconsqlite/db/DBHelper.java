package co.edu.unipiloto.persistenciaconsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "proyectos.db";
    public static final String TABLE_PROYECTOS = "t_proyectos";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PROYECTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreproyecto TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "email TEXT," +
                "direccion TEXT," +
                "localidad TEXT," +
                "tipodeproyecto TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_PROYECTOS);
        onCreate(sqLiteDatabase);
    }
}
