package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    DaoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new DaoContacto( this);
        lista = dao.verTodos();
        adapter = new Adaptador(this ,lista, dao);
        ListView list = (ListView)findViewById(R.id.lista);

        Button agregar = (Button)findViewById(R.id.agregar);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //metodo para entrar a los items
                //dialogo para ver vista previa del registro ,vista.xml
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialogo de agregar ,dialogo.xml

                //creamos un dialogo
                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo Registro");     //creamo titulo nuevo registro
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();                         //para que se visualice

                final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre); //enlazamos con nuestra vista dialogo
                final EditText telefono = (EditText)dialogo.findViewById(R.id.telefono); //enlazamos con nuestra vista dialogo
                final EditText email = (EditText)dialogo.findViewById(R.id.email); //enlazamos con nuestra vista dialogo
                final EditText edad = (EditText)dialogo.findViewById(R.id.edad); //enlazamos con nuestra vista dialogo

                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);//agregamos los botones
                guardar.setText("Agregar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);//agregamos los botones


                guardar.setOnClickListener(new View.OnClickListener() {         //hacemos nuestros eventos para nuestros boton guardar
                    @Override
                    public void onClick(View view) {

                        try{
                            c = new Contacto(nombre.getText().toString(),
                                    telefono.getText().toString(),
                                    email.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()) );

                            dao.insertar(c); //pasar al objeto dao
                            lista=dao.verTodos();

                            adapter.notifyDataSetChanged();//actualizar la lista
                            dialogo.dismiss();  //disminuir el dialogo
                        }catch(Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
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


    }
}
