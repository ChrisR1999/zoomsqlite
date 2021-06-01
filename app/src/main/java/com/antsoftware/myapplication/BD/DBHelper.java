package com.antsoftware.myapplication.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.antsoftware.myapplication.BD.Modelos.EmpleadosModelo;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tienda.db";

    public static final String EMPLEADOS_TABLA = "empleados";
    public static final String EMPLEADOS_ID = "id";
    public static final String EMPLEADOS_NOMBRE = "nombre";
    public static final String EMPLEADOS_TELEFONO = "telefono";

    public static final String SENTENCIASQL_EMPLEADOS = "CREATE TABLE "
            + EMPLEADOS_TABLA + "(" + EMPLEADOS_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMPLEADOS_NOMBRE + " TEXT,"
            + EMPLEADOS_TELEFONO + " TEXT );";

    public static final String INVENTARIO_TABLA = "inventario";
    public static final String INVENTARIO_ID = "id";
    public static final String INVENTARIO_NOMBRE = "nombre";
    public static final String INVENTARIO_CANTIDAD = "cantidad";

    public static final String SENTENCIASQLACTUALIZACION_EMPLEADOS = "DROP TABLE IF EXISTS " + EMPLEADOS_TABLA;

    public static final String SENTENCIASQL_INVENTARIO = "CREATE TABLE "
            + INVENTARIO_TABLA + "(" + INVENTARIO_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + INVENTARIO_NOMBRE + " TEXT,"
            + INVENTARIO_CANTIDAD + " INTEGER );";

    public static final String SENTENCIASQLACTUALIZACION_INVENTARIO = "DROP TABLE IF EXISTS " + INVENTARIO_TABLA;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(SENTENCIASQL_EMPLEADOS);
        bd.execSQL(SENTENCIASQL_INVENTARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionVieja, int vesionNueva) {
        bd.execSQL(SENTENCIASQLACTUALIZACION_EMPLEADOS);
        bd.execSQL(SENTENCIASQLACTUALIZACION_INVENTARIO);
        onCreate(bd);
    }

    public void insertarEmpleado(EmpleadosModelo empleado){
        SQLiteDatabase bd = this.getWritableDatabase();
        String queryInsertar = "INSERT INTO " + EMPLEADOS_TABLA + " values( "
                + empleado.getId() + ", " + empleado.getNombre() + ", "
                + empleado.getTelefono() + " )";
        bd.execSQL(queryInsertar);
    }

    public void buscarEmpleado(String nombre){
        SQLiteDatabase bd = this.getReadableDatabase();
        String queryBuscar = "SELECT * FROM " + EMPLEADOS_TABLA + " WHERE nombre = " + nombre + " ";
        bd.execSQL(queryBuscar);
    }

    public void actualizarEmpleado(String nombre){
        SQLiteDatabase bd = this.getWritableDatabase();
        String queryUpdate = "UPDATE " + EMPLEADOS_TABLA + " SET " + EMPLEADOS_TELEFONO +
                " WHERE nombre = " + nombre + " ";
        bd.execSQL(queryUpdate);
    }

    public void borrarEmpleado(String nombre){
        SQLiteDatabase bd = this.getWritableDatabase();
        String queryBorrar = "DELETE FROM " + EMPLEADOS_TABLA + " WHERE nombre = " + nombre + " ";
        bd.execSQL(queryBorrar);
    }

    public ArrayList<EmpleadosModelo> obtenerTodosEmpleados(){
        SQLiteDatabase bd = this.getReadableDatabase();
        ArrayList<EmpleadosModelo> empleados = new ArrayList<>();
        Cursor cursor = bd.rawQuery("SELECT * FROM " + EMPLEADOS_TABLA + " ", null);
        if(cursor.moveToFirst()){
          while(!cursor.isAfterLast()){
              empleados.add( new EmpleadosModelo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                      )
              );
          }
        }
        cursor.close();
        return empleados;
    }


}
