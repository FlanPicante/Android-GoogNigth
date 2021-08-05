package com.example.buenasnoches;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btt1;
    String randomst;
    int maxnum,counttex,estado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBFile dbFile= new DBFile(MainActivity.this);
        try {
            dbFile.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String DB_PATH= getDatabasePath("bye.db").toString();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);

        if(db!=null){
            //BUSACAR EL MAXIMO
            Cursor contador = db.rawQuery
                    ("select COUNT(Id) FROM cancion WHERE Estado=0", null);
            contador.moveToFirst();
            maxnum = contador.getInt(0);
            //GENERAR UN VECTOR DEL TAM DEL MAXIMO
            int[] numerosAleatorios= new int[maxnum];
            int i=0;
            //CONSULTAR LOS IDS DISPONIBLES
            Cursor idv = db.rawQuery("SELECT Id FROM cancion WHERE Estado =0",null);
                if(idv !=null){
                    idv.moveToFirst();
                    //PASAR IDS AL VECTOR

                    do {
                        numerosAleatorios[i]=idv.getInt(0);
                        i++;
                    }while(idv.moveToNext());
                }
            Random r =new Random();
            for (int y=numerosAleatorios.length;y>0;y--){
                int posicion= r.nextInt(y);
                int tmp=numerosAleatorios[y-1];
                numerosAleatorios[y-1]=numerosAleatorios[posicion];
                numerosAleatorios[posicion]=tmp;

            }
            i=0;
            do{
                Cursor fila = db.rawQuery
                        ("select Id,Estado FROM cancion WHERE Id=" + numerosAleatorios[i], null);
                fila.moveToFirst();
                    randomst=fila.getString(0);
                    estado=fila.getInt(1);
                i++;
            }while(estado==1);

            db.close();

        }
    }

    //funcion largotexto
    public void CountText(){
        String DB_PATH= getDatabasePath("bye.db").toString();
        int IdCancion= Integer.parseInt(randomst);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH,null,SQLiteDatabase.OPEN_READONLY);

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