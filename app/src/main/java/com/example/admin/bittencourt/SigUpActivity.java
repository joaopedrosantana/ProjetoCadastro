package com.example.admin.bittencourt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by admin on 09/08/2017.
 */

public class SigUpActivity extends AppCompatActivity {
    private EditText name, email, phone, user, pass;
    private Button btnCadastro;
    private String emailValidate, spinnerValidate, phoneInvalid;
    private Spinner comboState;
    private TextView errorText;
    private ArrayAdapter adapter;
    private String valueSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        comboState = (Spinner) findViewById(R.id.state);
        errorText = (TextView) comboState.getSelectedView();

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
                    editor.putString("state", valueSpinner);
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

        Spinner comboState = (Spinner) findViewById(R.id.state);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            JSONArray jArray = obj.getJSONArray("data");
            //ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            // HashMap<String, String> m_li;
            ArrayList<String> state = new ArrayList<String>();
            state.add(0, "Selecione o Estado...");

            for (int i = 1; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                Log.e("Details-->", jObject.getString("nome"));


                state.add(i, jObject.getString("nome"));

                adapter = new ArrayAdapter(this, R.layout.spinner_text, (state.toArray()));
                adapter.setDropDownViewResource(R.layout.spinner_box);
                comboState.setAdapter(adapter);

                // m_li = new HashMap<String, String>();
                //m_li.get("nome");
                // m_li.get("uf");

                //formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        comboState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerValidate = String.valueOf(position);
                valueSpinner = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        comboState = (Spinner) findViewById(R.id.state);
        errorText = (TextView) comboState.getSelectedView();
        Log.e("validation", spinnerValidate);
        if (spinnerValidate.equals("0")) {
            errorText.setError("Campo Obrigatório");
            isValid = false;
        }
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

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("states.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
