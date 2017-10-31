package com.edwinacubillos.firebaseexample.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.edwinacubillos.firebaseexample.R;
import com.edwinacubillos.firebaseexample.list.ListActivity;
import com.edwinacubillos.firebaseexample.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText eID, eName, eEmail, ePhone;
    Button bCreate, bUpdate, bRead, bDelete;
    int cont=0;
    User user;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);

        eID = (EditText) findViewById(R.id.eID);
        eName = (EditText) findViewById(R.id.eName);
        eEmail = (EditText) findViewById(R.id.eEmail);
        ePhone = (EditText) findViewById(R.id.ePhone);
        bCreate = (Button) findViewById(R.id.bCreate);
        bUpdate = (Button) findViewById(R.id.bUpdate);
        bRead = (Button) findViewById(R.id.bRead);
        bDelete = (Button) findViewById(R.id.bDelete);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios");

        users = new ArrayList<User>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    users.add(userSnapshot.getValue(User.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        myRef.setValue("Hello, World!");
    }

    public void onClick(View view) {
        int id = view.getId();
        final String uid =eID.getText().toString();
        String name =eName.getText().toString();
        final String email =eEmail.getText().toString();
        String phone =ePhone.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usuarios").child("user"+cont);

        switch(id){
            case R.id.bCreate:
                user= new User("user"+cont, name, email, phone);
                myRef.setValue(user);
                cont++;
            break;
            case R.id.bRead:
                myRef = database.getReference("usuarios");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("user"+uid).exists()){
                            user = dataSnapshot.child("user"+uid).getValue(User.class);
                            eName.setText(user.getName());
                            eEmail.setText(user.getEmail());
                            ePhone.setText(user.getPhone());

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.bUpdate:
                myRef = database.getReference("usuarios").child("user"+uid);

                Map<String,Object> newData = new HashMap<>();
                newData.put("name",name);
                newData.put("email",email);
                newData.put("phone",phone);
                myRef.updateChildren(newData);

                break;
            case R.id.bDelete:
                myRef = database.getReference("usuarios").child("user"+uid);
                myRef.removeValue();
                break;
            case R.id.bList:
                Intent intent = new Intent (this, ListActivity.class);
                startActivity(intent);
                break;
        }
    }



}
