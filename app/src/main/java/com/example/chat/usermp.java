package com.example.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class usermp extends AppCompatActivity {

    EditText usn;
    ListView lv;
    ArrayList<String>arrl;
    ArrayList<String>arrlnom;
    ArrayAdapter<String> arrad;
    String una;
    String pas;
    final  String server_url="http://192.168.1.4/select.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermp);
        usn=findViewById(R.id.usnid);
        lv=findViewById(R.id.lvid);
        una=getIntent().getStringExtra("una");
        pas=getIntent().getStringExtra("pass");
        arrl=new ArrayList<String>();
        arrlnom=new ArrayList<String>();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    String nom="";
                    int p;
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        nom=jsonObject.getString("name");
                        p=0;
                        for (String o:arrlnom){
                            if (nom.equals(o)|| nom.equals(una)){
                                 p=1;
                            }
                        }
                        if (p==1){
                            continue;
                        }
                        String m="";
                        if (una.equals(jsonObject.getString("to_w"))){
                            m="Message";
                        }
                        arrl.add(nom+": "+m+"\n");
                        arrlnom.add(nom);
                    }
                    arrad=new ArrayAdapter<String>(usermp.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrl);
                    lv.setAdapter(arrad);

                }catch (Exception e){
                    return;
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(usermp.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
        MySingleTon.getInstance(this).addToRequestQueue(stringRequest);
    }



    public void msg(View v){
        try {
            if (usn.getText().toString().equals("")){
                Toast.makeText(this,"Empty",Toast.LENGTH_LONG).show();
            }
            else {

                for (String i:arrlnom){
                    if (usn.getText().toString().equals(i)){
                        Intent intent=new Intent(usermp.this,chatt.class);
                        intent.putExtra("una",una);
                        intent.putExtra("una2",usn.getText().toString());
                        intent.putExtra("pas",pas);
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(usermp.this,"Not Found",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}
