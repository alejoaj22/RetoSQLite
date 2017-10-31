package com.edwinacubillos.firebaseexample.list;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edwinacubillos.firebaseexample.R;
import com.edwinacubillos.firebaseexample.main.MainActivity;
import com.edwinacubillos.firebaseexample.model.User;
import com.edwinacubillos.firebaseexample.sqlite.ContactosSQliteHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<User> users;
    private RecyclerView recyclerView;
    ContactosSQliteHelper contactosSQliteHelper;
    SQLiteDatabase dbContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        users = new ArrayList<User>();

        //creo la base de dato
        contactosSQliteHelper = new ContactosSQliteHelper(this,"agendaBD",null,1);
        //hago editable la base de datos
        dbContactos = contactosSQliteHelper.getWritableDatabase();

        Cursor cursor = dbContactos.rawQuery("SELECT * FROM contactos",null);

        if(cursor.moveToFirst())
            do{
                User user = new User (String.valueOf(cursor.getInt(0)),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getString(2));
            users.add(user);
        }while(cursor.moveToNext());


        ContactosAdapter adapter = new ContactosAdapter(this,users);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }



    class Adapter extends ArrayAdapter<User>{

        public Adapter(Context context, ArrayList<User> user) {
            super(context, R.layout.list_item, user);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.list_item, null);

            User user = getItem(position);

            TextView tUid = item.findViewById(R.id.tID);
            tUid.setText(user.getUid());

            TextView tName = item.findViewById(R.id.tName);
            tName.setText(user.getName());

            TextView tEmail = item.findViewById(R.id.tEmail);
            tEmail.setText(user.getEmail());

            TextView tPhone = item.findViewById(R.id.tPhone);
            tPhone.setText(user.getPhone());

            return item;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){

            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }
}
