package com.example.buenasnoches;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class tercer extends AppCompatActivity implements View.OnClickListener{
    Button btt3;
    String IdMain;
    int IdOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercer);
        initUP();
        btt3.setOnClickListener(this);
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            IdMain=extras.getString("id");
            IdOrder=extras.getInt("IdORder");

        }

        PrintLyric();
    }


    @Override
    public void onClick(View v) {
        Intent siginpar=new Intent(this,SegundoActiviy.class);
        startActivity(siginpar);
    }

    public void initUP(){
        btt3=(Button)findViewById(R.id.btt3);
    }

    public void PrintLyric(){
        AdminSQLite admin =new AdminSQLite(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor fila = db.rawQuery
                ("select Texto FROM textos WHERE IdCancion= "+IdMain+ " AND Orden= ", null);

        if (fila.moveToFirst()){
            btt3.setText(fila.getString(0));

            db.close();
        } else {
            Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            db.close();
        }

    }

}