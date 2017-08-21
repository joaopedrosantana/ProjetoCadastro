package com.example.admin.bittencourt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 04/08/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText user, pass;
    private Button btnEntrar;
    private CheckBox checkLog;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.edit_log);
        pass = (EditText) findViewById(R.id.edit_pass);
        btnEntrar = (Button) findViewById(R.id.btn_login);
        checkLog = (CheckBox) findViewById(R.id.check_log);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        TextView linkCadastro = (TextView) findViewById(R.id.link_cadastro);

        linkCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SigUpActivity.class);
                startActivity(intent);
                finish();

            }
        });
        sharedPreferences = getSharedPreferences("MyPref", 0);


        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkLog.isChecked()) {
                    if (user.getText().toString().equals("")) {
                        user.setError("Campo Obrigatório");
                    }

                    if (pass.getText().toString().equals("")) {
                        pass.setError("Campo Obrigatório");
                    }

                    if (user.getText().toString().length() > 0 && pass.getText().toString().length() > 0) {
                        String uName = null;
                        String uPassword = null;
                        if (sharedPreferences.contains("user")) {
                            uName = sharedPreferences.getString("user", "");

                        }

                        if (sharedPreferences.contains("pass")) {
                            uPassword = sharedPreferences.getString("pass", "");

                        }

                        if (user.getText().toString().equals(uName) && pass.getText().toString().equals(uPassword)) {

                            int count=pref.getInt("count",0);
                            if (count==0){
                                editor.putInt("count", 1);
                                editor.commit();
                            }
                            Log.e("result",String.valueOf(count));

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name", user.getText().toString());
                            startActivity(intent);
                            finish();

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Login ou senha inválidos", Toast.LENGTH_LONG).show();
                        }
                    }
                } else

                {

                    if (user.getText().toString().equals("")) {
                        user.setError("Campo Obrigatório");
                    }

                    if (pass.getText().toString().equals("")) {
                        pass.setError("Campo Obrigatório");
                    }

                    if (user.getText().toString().length() > 0 && pass.getText().toString().length() > 0) {
                        String uName = null;
                        String uPassword = null;
                        if (sharedPreferences.contains("user")) {
                            uName = sharedPreferences.getString("user", "");

                        }

                        if (sharedPreferences.contains("pass")) {
                            uPassword = sharedPreferences.getString("pass", "");

                        }

                        if (user.getText().toString().equals(uName) && pass.getText().toString().equals(uPassword)) {

                            editor.remove("count");
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("name", user.getText().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, "Login ou senha inválidos", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }


        });


    }
}
