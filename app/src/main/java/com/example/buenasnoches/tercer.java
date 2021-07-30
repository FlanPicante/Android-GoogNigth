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

public class tercer extends AppCompatActivity implements View.OnClickListener{
    Button btt3;
    TextView txtv2,txtprueba;
    String IdMain;
    int IdOrder,LargoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercer);
        initUP();
        btt3.setOnClickListener(this);
        Bundle extras=getIntent().getExtras();
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


        txtprueba.setText(String.valueOf(LargoText));
    }


    @Override
    public void onClick(View v) {
        Intent inicio2=new Intent(this,SegundoActiviy.class);
        inicio2.putExtra("id",IdMain);
        inicio2.putExtra("orden",IdOrder);
        inicio2.putExtra("largo",LargoText);
        startActivity(inicio2);
    }

    public void initUP(){
        txtv2=(TextView)findViewById(R.id.txtv_2);
        btt3=(Button)findViewById(R.id.btt3);
        txtprueba=(TextView)findViewById(R.id.txtprueba);
    }

    public void PrintLyric(){
        IdOrder++;
        AdminSQLite admin =new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery
                ("select Texto FROM textos WHERE IdCancion= "+IdMain+ " AND Orden= "+IdOrder, null);

        if (fila.moveToFirst()){
            txtv2.setText(fila.getString(0));

            db.close();
        } else {
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            db.close();
        }

    }

    public void EndNigth(){
        btt3.setVisibility(View.INVISIBLE);
        txtv2.setText("Feliz noche :D");
        AdminSQLite admin =new AdminSQLite(tercer.this);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.execSQL("UPDATE cancion SET Estado=1 WHERE Id= " +IdMain );
        db.close();
    }
}