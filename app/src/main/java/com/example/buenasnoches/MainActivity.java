package com.example.buenasnoches;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btt1;
    String randomst;
    int maxnum,counttex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intUpc();
        btt1.setOnClickListener(this);

    }


    public void onClick(View v) {
        int id=v.getId();
        if(id == R.id.btt1){
            GetRandomId();
            CountText();
            Intent iniciob= new Intent(this,SegundoActiviy.class);
            iniciob.putExtra("id",randomst);
            iniciob.putExtra("largo",counttex);
            startActivity(iniciob);

        }

    }

    public void GetRandomId(){
        AdminSQLite admin =new AdminSQLite(MainActivity.this);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(db!=null){
            //BUSACAR EL MAXIMO
            Cursor contador = db.rawQuery
                    ("select MAX(Id) FROM cancion ", null);

            if (contador.moveToFirst()){
                maxnum = Integer.parseInt(contador.getString(0));
            } else {
                Toast.makeText(this,"Error al encontrar el maximo", Toast.LENGTH_SHORT).show();
            }
            //GENERAR NUMERO ALEATORIO CON EL MAXIMO

            int numrandom_int = (int)(Math.random() * maxnum + 1);
            String numrandom = String.valueOf(numrandom_int);

            //validacion NO ESTA LISTA AUN

            Cursor fila = db.rawQuery
                    ("select Id FROM cancion WHERE Id=" + numrandom, null);
            if (fila.moveToFirst()) {
                randomst=fila.getString(0);

            } else {
                Toast.makeText(this, "Error al econtrar el id", Toast.LENGTH_SHORT).show();
            }

            db.close();

        }
    }

    //funcion largotexto
    public void CountText(){
        int IdCancion= Integer.parseInt(randomst);
        AdminSQLite admin =new AdminSQLite(MainActivity.this);
        SQLiteDatabase db = admin.getWritableDatabase();
        if(db!=null){
            Cursor count= db.rawQuery("SELECT COUNT(Id) FROM textos WHERE IdCancion = "+IdCancion,null);
            if(count.moveToFirst()){
                counttex= Integer.parseInt(count.getString(0));
            }else {
                Toast.makeText(this, "Error al econtrar el id", Toast.LENGTH_SHORT).show();
            }

            db.close();
        }

    }

    //CONECTAR INTGRAFIC A COD
    public void intUpc(){
        btt1=(Button)findViewById(R.id.btt1);

    }

}