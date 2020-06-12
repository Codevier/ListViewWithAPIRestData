package com.example.listviewwithapirestdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewwithapirestdata.Adapter.AdaptadorUsuario;
import com.example.listviewwithapirestdata.Model.Usuario;
import com.example.listviewwithapirestdata.WebService.Asynchtask;
import com.example.listviewwithapirestdata.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  implements Asynchtask
{

    ListView lstOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstOpciones = (ListView)findViewById(R.id.lstListaUsuario);

        View header = getLayoutInflater().inflate(R.layout.ly_header, null);
        lstOpciones.addHeaderView(header);

        Map<String, String> datos = new HashMap<String, String>();
       WebService ws= new WebService("https://reqres.in/api/users",
                datos, MainActivity.this, MainActivity.this);
       ws.execute("GET");



    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario> ();

        try {

            JSONObject JSONlista =  new JSONObject(result);
            JSONArray JSONlistaUsuarios=  JSONlista.getJSONArray("data");

            lstUsuarios = Usuario.JsonObjectsBuild(JSONlistaUsuarios);

            AdaptadorUsuario adapatorUsuario = new AdaptadorUsuario(this, lstUsuarios);

            lstOpciones.setAdapter(adapatorUsuario);

        }catch (JSONException e)
        {
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
        }


    }
}