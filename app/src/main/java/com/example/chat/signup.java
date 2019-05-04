package com.example.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    EditText un,pas;
    final  String server_url="http://192.168.1.4/insert.php";
    final String server_url_nm="http://192.168.1.4/select.php";
    ArrayList<String>arrlnom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        un=findViewById(R.id.unid);
        pas=findViewById(R.id.psid);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url_nm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        arrlnom.add(jsonObject.getString("name"));
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(signup.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        MySingleTon.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void inse(View v){
        if (un.getText().toString().equals("")|| pas.getText().toString().equals("")){
            Toast.makeText(this,"Empty",Toast.LENGTH_LONG).show();
        }else {
            for (String i:arrlnom){
                if (un.getText().toString().equals(i)){
                    Toast.makeText(signup.this,"Existed",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(signup.this,"Signed Up",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(signup.this,usermp.class);
                    intent.putExtra("una",un.getText().toString());
                    intent.putExtra("pass",pas.getText().toString());
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(signup.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("name",un.getText().toString());
                    map.put("password",pas.getText().toString());
                    return map;
                }
            };

            MySingleTon.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}
