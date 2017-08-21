package com.example.admin.bittencourt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by admin on 08/08/2017.
 */

public class MainActivity extends AppCompatActivity {


    private TextView nameUser, emailUser, phoneUser;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        nameUser=(TextView)findViewById(R.id.name);
        emailUser=(TextView)findViewById(R.id.email);
        phoneUser=(TextView)findViewById(R.id.phone);
        btnSair=(Button)findViewById(R.id.btn_exit);

        String name= pref.getString("name", null);
        String email= pref.getString("email", null);
        String phone= pref.getString("phone",null);

        if (name!=null){
            nameUser.setText("Nome: "+name);
            emailUser.setText("Email: "+email);
            phoneUser.setText("Telefone: "+phone);
        }else{
           String user= getIntent().getExtras().getString("name");
            nameUser.setText(user);

        }



        btnSair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editor.clear();
                editor.commit();

                Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();

            }
        });


    }


}
