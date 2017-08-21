package com.example.admin.bittencourt;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 09/08/2017.
 */

public class SigUpActivity extends AppCompatActivity {
    private EditText name, email, phone, user, pass;
    private Button btnCadastro;
    private String phoneInvalid;
    private String emailValidate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        phone = (EditText) findViewById(R.id.edit_phone);
        user = (EditText) findViewById(R.id.edit_user);
        pass = (EditText) findViewById(R.id.edit_pass);
        btnCadastro = (Button) findViewById(R.id.btn_cadastro);


        TextView linkLogin = (TextView) findViewById(R.id.link_login);

        linkLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SigUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        phone.addTextChangedListener(Mask.insert("(##)#####-####", phone));
        emailValidate = email.getText().toString();

        btnCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (validForm()) {
                    editor.putString("name", name.getText().toString());
                    editor.putString("email", email.getText().toString());
                    editor.putString("phone", phone.getText().toString());
                    editor.putString("user", user.getText().toString());
                    editor.putString("pass", pass.getText().toString());
                    editor.putInt("count", 1);
                    editor.commit();

                    Toast.makeText(SigUpActivity.this, "Cadastro efetuado com sucesso", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(SigUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


            }


        });


        phone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Log.e("Start", String.valueOf(start));

                if (start == 13) {
                    phoneInvalid = "S";

                } else {
                    phoneInvalid = "N";
                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


    }


    public static class Mask {

        public static String unmask(String s) {
            return s.replaceAll("[.]", "").replaceAll("[-]", "")
                    .replaceAll("[/]", "").replaceAll("[(]", "")
                    .replaceAll("[)]", "");
        }

        public static TextWatcher insert(final String mask, final EditText ediTxt) {
            return new TextWatcher() {
                boolean isUpdating;
                String old = "";

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String str = Mask.unmask(s.toString());
                    String mascara = "";
                    if (isUpdating) {
                        old = str;
                        isUpdating = false;
                        return;
                    }
                    int i = 0;
                    for (char m : mask.toCharArray()) {
                        if (m != '#' && str.length() > old.length()) {
                            mascara += m;
                            continue;
                        }
                        try {
                            mascara += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mascara);
                    ediTxt.setSelection(mascara.length());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            };
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private boolean validForm() {
        boolean isValid = true;
        emailValidate = email.getText().toString();

        if (name.getText().toString().isEmpty()) {
            name.setError("Campo Obrigatório");
            isValid = false;
        }
        if (phone.getText().toString().isEmpty()) {
            phone.setError("Campo Obrigatório");
            isValid = false;
        } else if (phoneInvalid.equals("N")) {
            Toast.makeText(SigUpActivity.this, "Telefone Inválido", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        if (emailValidate.isEmpty()) {

            email.setError("Campo Obrigatório");
            isValid = false;
        } else if (!isValidEmail(emailValidate)) {
            Toast.makeText(SigUpActivity.this, "Email Inválido", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        if (user.getText().toString().isEmpty()) {
            user.setError("Campo Obrigatório");
            isValid = false;
        }

        if (pass.getText().toString().isEmpty()) {
            pass.setError("Campo Obrigatório");
            isValid = false;
        }
        return isValid;
    }
}
