package com.example.myapplication;

import static com.example.myapplication.MainActivity.kullanıcılar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class SignUpActicity extends AppCompatActivity {
    EditText nameSign,surnameSign,emailSign,phoneSign,passwordSign, passwordSignAgain;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acticity);

        nameSign = (EditText) findViewById(R.id.editText_signup_name);
        surnameSign = (EditText) findViewById(R.id.editText_signup_surname);
        emailSign = (EditText) findViewById(R.id.editText_signup_email);
        phoneSign = (EditText) findViewById(R.id.editText_signup_phone);
        signUpButton = (Button) findViewById(R.id.button_signup);
        passwordSign = (EditText) findViewById(R.id.editText_signUp_password);
        passwordSignAgain = (EditText) findViewById(R.id.editText_signUp_passwordAgain);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean existFlag = false;
                String signUpName = nameSign.getText().toString();
                String signUpSurname = surnameSign.getText().toString();
                String signUpEmail = emailSign.getText().toString();
                String signUpPhone = phoneSign.getText().toString();
                String signUpPassword = passwordSign.getText().toString();
                String signUpPasswordAgain = passwordSignAgain.getText().toString();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                for (Kullanıcılar kullanıcı : kullanıcılar) {
                    if (kullanıcı.getEmail().equals(signUpEmail)) {
                        existFlag = true;
                        CharSequence text = "Email Is Already Registered";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                if(existFlag == false){
                    if(signUpPassword.equals(signUpPasswordAgain)){
                        String text = "İsim Soyisim: "+signUpName+" "+signUpSurname+"\nTelefon Numarası: "+signUpPhone;
                        String subject ="Doğrulama maili";

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{signUpEmail});
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intent.putExtra(Intent.EXTRA_TEXT, text);

                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                        newUser(signUpName,signUpSurname,signUpEmail,signUpPhone,signUpPassword);
                    }else{
                        CharSequence text = "Passwords Do Not Match";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    //changeActivitytoMainActivity(view);
                }
            }

        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(SignUpActicity.this, MainActivity.class);
        startActivity(intent);
    }

    public void newUser(String name, String surname, String email, String phone, String password){
        Kullanıcılar user = new Kullanıcılar();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        kullanıcılar.add(user);
    }
}