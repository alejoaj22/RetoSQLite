package com.edwinacubillos.firebaseexample.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edwinacubillos.firebaseexample.R;
import com.edwinacubillos.firebaseexample.list.ListActivity;
import com.edwinacubillos.firebaseexample.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SQliteActivity extends AppCompatActivity {
    EditText eID, eName, eEmail, ePhone;
    Button bCreate, bUpdate, bRead, bDelete;
    ContactosSQliteHelper contactosSQliteHelper;
    SQLiteDatabase dbContactos;
    int cont=0;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        //creo la base de dato
        contactosSQliteHelper = new ContactosSQliteHelper(this,"agendaBD",null,1);
        //hago editable la base de datos
        dbContactos = contactosSQliteHelper.getWritableDatabase();

        eID = (EditText) findViewById(R.id.eID);
        eName = (EditText) findViewById(R.id.eName);
        eEmail = (EditText) findViewById(R.id.eEmail);
        ePhone = (EditText) findViewById(R.id.ePhone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bRead = (Button) findViewById(R.id.bRead);
        bDelete = (Button) findViewById(R.id.bDelete);
    }

    public void onClick(View view) {
        int id = view.getId();
        final String uid =eID.getText().toString();
        String name =eName.getText().toString();
        final String email =eEmail.getText().toString();
        String phone =ePhone.getText().toString();

        ContentValues data = new ContentValues();
        switch(id){
            case R.id.bCreate:
                data.put("nombre",name);
                data.put("telefono",phone);
                data.put("correo",email);
                dbContactos.insert("contactos",null,data);

                break;
            case R.id.bRead:
                Cursor c = dbContactos.rawQuery("SELECT * FROM contactos WHERE nombre = '"
                        +name+"'",null);
                if(c.moveToFirst()){
                    ePhone.setText(c.getString(2));
                    eEmail.setText(c.getString(3));
                }else{
                    Toast.makeText(this,"No existe el contacto",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bUpdate:
                data.put("telefono",phone);
                data.put("correo",email);

                dbContactos.update("contactos",data,"nombre= '"+name+"'",null);
                clean();
                break;
            case R.id.bDelete:
                dbContactos.delete("contactos", "nombre = '"+name+"'",null);
                clean();
                break;
            case R.id.bList:
                Intent intent = new Intent(this,ListActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void clean(){
        eEmail.setText("");
        ePhone.setText("");
        eName.setText("");
        eID.setText("");
    }
}
