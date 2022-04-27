package com.example.projet_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText txtNom;
    private EditText txtAge;
    private TextView txtInfo;
    private Button btnInsert,btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNom=(EditText) findViewById(R.id.txtNom);
        txtAge=(EditText) findViewById(R.id.txtAge);
        txtInfo=(TextView) findViewById(R.id.txtInfo);
        btnInsert=(Button) findViewById(R.id.btnInsert);
        btnSelect=(Button) findViewById(R.id.btnSelect);



        SQLiteDatabase myDatabase =
                this.openOrCreateDatabase("Utilisateurs",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS utilisateurs " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nom VARCHAR,age INT(3))");

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Action à effectuer en appuyant sur le bouton INSERER
                String nom=txtNom.getText().toString().trim();
                int age=Integer.valueOf(txtAge.getText().toString());

                myDatabase.execSQL("INSERT INTO utilisateurs (nom,age)" +
                        " VALUES ('" + nom + "'," + age + ")");

                txtNom.setText("");
                txtAge.setText("");
            }
        });


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Action à effectuer en appuyant sur le bouton AFFICHER
                Cursor c = myDatabase.rawQuery("SELECT * FROM utilisateurs",null);
                int nameIndex=c.getColumnIndex("nom");
                int ageIndex=c.getColumnIndex("age");
                String resultat= "" ;


                if(c != null && c.getCount() > 0)
                {
                    c.moveToFirst();
                    do {
                        resultat += c.getString(nameIndex) + " - "
                                + String.valueOf(c.getInt(ageIndex)) + " \n";
                    }while (c.moveToNext());

                }
                txtInfo.setText(resultat);
            }
        });
    }
}