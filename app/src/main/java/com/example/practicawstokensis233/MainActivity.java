package com.example.practicawstokensis233;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void btnlogin(View view){
         EditText txtUsuario   = (EditText) findViewById(R.id.txtUsuario);
         EditText txtPassword = (EditText) findViewById(R.id.txtps);

         String  usuario =  txtUsuario.getText().toString();
         String password = txtPassword.getText().toString();


        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",usuario); datos.put("clave",password);
        WebService ws= new WebService("https://api.uealecpeterson.net/public/login",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("POST");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        Log.i("resultado",(result));

        //Parsear la Resp dekl Login
        //1 Detectar si hay Error
        JSONObject jObjectResult = new JSONObject(result);

        TextView txtError  = (TextView) findViewById(R.id.txtError);

        if(jObjectResult.has("error")){

            txtError.setText(jObjectResult.get("error").toString());
            txtError.setTextColor(getResources().getColor(R.color.red));
        }
        else{
            txtError.setText("Acceso Correcto");
            txtError.setTextColor(getResources().getColor(R.color.green));
            //abrir actividad 2
            Intent intent = new Intent(MainActivity.this,
                    MainActivityListaProducto.class);
            Bundle b = new Bundle();
            //b.putString("NOMBRE", Nombre);
            String Token = jObjectResult.get("access_token").toString();
            Log.i("token",Token);
            b.putString("Token", Token);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}