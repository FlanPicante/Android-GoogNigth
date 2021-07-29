package com.example.buenasnoches;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SegundoActiviy extends AppCompatActivity implements View.OnClickListener {
    Button btt2;
    TextView frase_vw;
    String IdMain;
    int IdOrder=0,LargoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo_activiy);
        intUP();

        btt2.setOnClickListener(this);
        Bundle extras= getIntent().getExtras();
        if(extras!=null){
            IdMain=extras.getString("id");
            IdOrder=extras.getInt("orden");
            LargoText=extras.getInt("largo");

        }
        if(IdOrder<LargoText){
            PrintLyric();
        }else {
            EndNigth();
        }

    }

    public void PrintLyric(){
            IdOrder++;
            AdminSQLite admin =new AdminSQLite(SegundoActiviy.this);
            SQLiteDatabase db = admin.getWritableDatabase();

            Cursor fila = db.rawQuery
                    ("select Texto FROM textos WHERE IdCancion= "+IdMain+ " AND Orden= "+IdOrder, null);

            if (fila.moveToFirst()){
                frase_vw.setText(fila.getString(0));

                db.close();
            } else {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
                db.close();
            }

    }



    public void intUP(){
        frase_vw=(TextView)findViewById(R.id.txtv_1);
        btt2=(Button)findViewById(R.id.btt2);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btt2){
            Toast.makeText(SegundoActiviy.this,"Si funciona boton",Toast.LENGTH_SHORT).show();
            Intent inicio3= new Intent(this,tercer.class);
            inicio3.putExtra("id",IdMain);
            inicio3.putExtra("orden",IdOrder);
            inicio3.putExtra("largo",LargoText);
            startActivity(inicio3);
        }
    }

    public void EndNigth(){
        frase_vw.setText("Feliz noche :D");
    }
}