package com.example.agenda;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<Contacto> lista;
    DaoContacto dao;
    Contacto c;
    Activity a;
    int id=0;


    public Adaptador( Activity a, ArrayList<Contacto> lista, DaoContacto dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public int getCount() {

        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c= lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c= lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v==null){

            LayoutInflater li=(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.item,null);
        }
        c=lista.get(posicion);
        TextView nombre = (TextView)v.findViewById(R.id.t_nombre);
        TextView telefono = (TextView)v.findViewById(R.id.t_telefono);
        TextView email = (TextView)v.findViewById(R.id.t_email);
        TextView edad = (TextView)v.findViewById(R.id.t_edad);

        Button editar = (Button)v.findViewById(R.id.editar);
        Button eliminar = (Button)v.findViewById(R.id.eliminar);

        nombre.setText(c.getNombre());
        telefono.setText(c.getTelefono());
        email.setText(c.getEmail());
        edad.setText(" "+c.getEdad());
        editar.setTag(posicion);
        eliminar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogo de editar , va a invocar a dialogo.xml

                int pos=Integer.pview.getTag();
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar Registro");     //creamo titulo nuevo registro
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();                         //para que se visualice

                final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre); //enlazamos con nuestra vista dialogo
                final EditText telefono = (EditText)dialogo.findViewById(R.id.telefono); //enlazamos con nuestra vista dialogo
                final EditText email = (EditText)dialogo.findViewById(R.id.email); //enlazamos con nuestra vista dialogo
                final EditText edad = (EditText)dialogo.findViewById(R.id.edad); //enlazamos con nuestra vista dialogo

                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);//agregamos los botones
                guardar.setText("Guardar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);//agregamos los botones

                c=lista.get(pos);
                setId(c.getId());

                nombre.setText(c.getNombre());
                telefono.setText(c.getTelefono());
                email.setText(c.getEmail());
                edad.setText(" "+c.getEdad)

                guardar.setOnClickListener(new View.OnClickListener() {         //hacemos nuestros eventos para nuestros boton guardar
                    @Override
                    public void onClick(View view) {

                        try{
                            c = new Contacto(getId(),nombre.getText().toString(),
                                    telefono.getText().toString(),
                                    email.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()) );

                            dao.editar(c); //pasar al objeto dao
                            lista=dao.verTodos();
                            notifyDataSetChanged();

                            //adapter.notifyDataSetChanged();//actualizar la lista
                            dialogo.dismiss();  //disminuir el dialogo
                        }catch(Exception e){
                            Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                cancelar.setOnClickListener(new View.OnClickListener() {     //hacemos nuestros eventos para nuestros boton cancelar
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();

                    }
                });

            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogo para confirmar si borrara o no

                int pos=Integer.parseInt(view.getTag().toString());
                c=lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del=new AlertDialog.Builder(a);
                del.setMessage("Deseas eliminar el Registro?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)   {
                                dao.elimiar(getId());
                                lista=dao.verTodos();
                                notifyDataSetChanged();
                            }
                        }
                );
                del.setNegativeButton("NO", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                ) ;
                del.show();
            }
        });

        return v;
    }

}
