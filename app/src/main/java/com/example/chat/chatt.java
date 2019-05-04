package com.example.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

public class chatt extends AppCompatActivity {

    EditText msg;
    ListView lv;
    String una;
    String una2;
    String pas;
    final  String server_url_msg="http://192.168.1.4/select.php";
    final  String server_url_stmsg="http://192.168.1.4/insert.php";
    ArrayList<String> arrl=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatt);
        msg=findViewById(R.id.msgid);
        lv=findViewById(R.id.livid);
        una=getIntent().getStringExtra("una");
        una2=getIntent().getStringExtra("una2");
        pas=getIntent().getStringExtra("pas");

        try {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url_msg, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            if ((una.equals(jsonObject.getString("name")) && una2.equals(jsonObject.getString("to_w")))
                                    || una2.equals(jsonObject.getString("name"))&& una.equals(jsonObject.getString("to_w"))){
                                arrl.add(jsonObject.getString("message"));

                            }
                        }

                        ArrayAdapter<String> arrad=new ArrayAdapter<String>(chatt.this,android.R.layout.simple_list_item_1
                                ,android.R.id.text1,arrl);
                        lv.setAdapter(arrad);
                    }catch (Exception e){
                        Toast.makeText(chatt.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        return;
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(chatt.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

            MySingleTon.getInstance(this).addToRequestQueue(stringRequest);

        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void snd(View v){
        if (msg.getText().toString().equals("")){
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {

            StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url_stmsg, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(chatt.this,error.getMessage(),Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map=new HashMap<>();
                    map.put("name",una);
                    map.put("password",pas);
                    map.put("to_w",una2);
                    map.put("message",una+": "+msg.getText().toString());

                    return map;
                }
            };

            MySingleTon.getInstance(this).addToRequestQueue(stringRequest);
            mt(v);

        }
    }
    private void mt(View v){
        Intent intent= getIntent();
        finish();
        startActivity(intent);
    }
}
