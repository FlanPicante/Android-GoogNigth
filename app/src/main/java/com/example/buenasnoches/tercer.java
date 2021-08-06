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
    TextView txtv2;
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
    }

    public void PrintLyric(){
        String DB_PATH= getDatabasePath("bye.db").toString();
        IdOrder++;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH,null,SQLiteDatabase.OPEN_READONLY);

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
        String DB_PATH= getDatabasePath("bye.db").toString();
        btt3.setVisibility(View.INVISIBLE);
        txtv2.setText("Feliz noche :D");
        SQLiteDatabase db =SQLiteDatabase.openDatabase(DB_PATH,null,SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("UPDATE cancion SET Estado=1 WHERE Id= " +IdMain );
        db.close();
    }
}