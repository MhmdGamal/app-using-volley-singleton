package com.example.chat;

import android.content.Intent;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText un,pas;
    final  String server_url="http://192.168.1.4/select.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        un=findViewById(R.id.unid);
        pas=findViewById(R.id.psid);
    }

    public void login(View v){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                readjson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        MySingleTon.getInstance(this).addToRequestQueue(stringRequest);
    }

    private  void readjson(String r){
        try {
            JSONArray jsonArray=new JSONArray(r);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                if (un.getText().toString().equals(jsonObject.getString("name"))){
                    if (pas.getText().toString().equals(jsonObject.getString("password"))){
                        Intent intent=new Intent(MainActivity.this,usermp.class);
                        intent.putExtra("una",un.getText().toString());
                        intent.putExtra("pass",pas.getText().toString());
                        startActivity(intent);
                        return;
                    }
                    Toast.makeText(MainActivity.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            Toast.makeText(MainActivity.this,"Not Found",Toast.LENGTH_LONG).show();
            return;


        }catch (Exception e){
            return;
        }
    }

    public void signup(View v){
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
    }
}
