package com.example.practicawstokensis233;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebService.Asynchtask;
import WebService.WebService;

public class MainActivityListaProducto extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = this.getIntent().getExtras();
        String token=bundle.getString("Token");

        TextView txt=(TextView) findViewById(R.id.txtinfo);
        txt.setText(bundle.getString("Token").toString());
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("fuente","1");
        WebService ws= new WebService("https://api.uealecpeterson.net/public/productos/search",
                datos, MainActivityListaProducto.this, MainActivityListaProducto.this);
        ws.execute("POST","Authorization","Bearer "+token);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txt=(TextView) findViewById(R.id.txtinfo);
        txt.setText(result);
        String lstProductos="";
        JSONObject objecto = new JSONObject(result);
        JSONArray JSONlista = objecto.getJSONArray("productos");
        for(int i = 0; i< JSONlista.length(); i++){
            JSONObject producto= JSONlista.getJSONObject(i);
            lstProductos = lstProductos + "\n" +
                    producto.getString("id").toString() +" " +
                    producto.getString("descripcion");
        }
        txt.setText("Respuesta WS Lista de Bancos" + lstProductos);
    }
}