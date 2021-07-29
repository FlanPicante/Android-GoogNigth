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
            IdOrder=extras.getInt("orden");

        }
        PrintLyric();

        txtprueba.setText(String.valueOf(IdOrder));
    }


    @Override
    public void onClick(View v) {
        Intent inicio2=new Intent(this,SegundoActiviy.class);
        inicio2.putExtra("id",IdMain);
        inicio2.putExtra("orden",IdOrder);
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

}