package com.example.agenda;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DaoContacto  {

    SQLiteDatabase agenda;
    ArrayList<Contacto> lista=new ArrayList<Contacto>();
    Contacto c;
    Context ct;
    String nombreDB = "BDContactos";
    String tabla = "create table if not  exists contacto(id integer primary key autoincrement, nombre text, telefono text, email text, edad integer)";

    public DaoContacto(Context c) {
        this.ct = c;
        agenda = c.openOrCreateDatabase(nombreDB, Context.MODE_PRIVATE, null);
        agenda.execSQL(tabla);
    }

    //insertamos los datos en la base de datos
    public boolean insertar(Contacto c) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre" ,c.getNombre());
        contenedor.put("telefono" ,c.getTelefono());
        contenedor.put("email" ,c.getEmail());
        contenedor.put("edad" ,c.getEdad());

        return (agenda.insert("contacto", null, contenedor)) > 0;
    }

    public boolean editar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre" ,c.getNombre());
        contenedor.put("telefono" ,c.getTelefono());
        contenedor.put("email" ,c.getEmail());
        contenedor.put("edad" ,c.getEdad());

        return (agenda.update("contacto", contenedor,"id=" +c.getId(), null)) > 0;
    }

    public boolean eliminar(int id) {

        return (agenda.delete("contacto", "id="+id, null)) >0;
    }

    public ArrayList<Contacto> verTodos() {
        lista.clear();
        Cursor cursor = agenda.rawQuery("select *from contacto ", null);
        if(cursor!=null && cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) ));
            }while (cursor.moveToNext());


        }

        return lista;
    }

    public Contacto verUno(int posicion){
        Cursor cursor = agenda.rawQuery("select *from contacto ", null);
        cursor.moveToPosition(posicion);
        c = new Contacto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4) ) ;

        return c;
    }
}
