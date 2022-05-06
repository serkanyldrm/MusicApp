package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText userName,password;
    Button loginButton,signupButton;
    TextView logMessage;
    int counter = 0;
    public static ArrayList<Kullanıcılar> kullanıcılar = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       userName =  (EditText) findViewById(R.id.editText1);
       password = (EditText) findViewById(R.id.editText2);
       loginButton = (Button) findViewById(R.id.button);
       signupButton =(Button) findViewById(R.id.button2);
       logMessage = (TextView) findViewById(R.id.textView);

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                   String name = userName.getText().toString();
                   String pword = password.getText().toString();

                   Context context = getApplicationContext();
                   int duration = Toast.LENGTH_LONG;

                   if (name.equals("admin") && pword.equals("admin")) {
                       //logMessage.setText("Başarılı");
                       CharSequence text = "Login Successful";
                       Toast toast = Toast.makeText(context, text, duration);
                       toast.show();
                       changeActivitytoMusicList(view);
                   } else {
                       for (Kullanıcılar kullanıcı : kullanıcılar) {
                           if (kullanıcı.getEmail().equals(name)) {
                               if (kullanıcı.getPassword().equals(pword)) {
                                   //logMessage.setText("Başarılı")
                                   CharSequence text = "Login Succesfull";
                                   Toast toast = Toast.makeText(context, text, duration);
                                   toast.show();
                                   changeActivitytoMusicList(view);
                               } else {
                                   counter++;
                                   if (counter >= 3) {
                                       loginButton.setEnabled(false);
                                       changeActivitytoSıgnUp(view);
                                   }

                                   CharSequence text = "Wrong Password, Remaining Tries: " + (3 - counter);
                                   Toast toast = Toast.makeText(context, text, duration);
                                   toast.show();
                               }
                           }else{
                               CharSequence text = "Username Do Not Exist, Remaining Tries: " + (3 - counter);
                               Toast toast = Toast.makeText(context, text, duration);
                               toast.show();
                               counter++;
                               if (counter >= 3) {
                                   loginButton.setEnabled(false);
                                   changeActivitytoSıgnUp(view);
                               }
                           }
                       }
                   }
           }
           public void changeActivitytoMusicList (View view){
               Intent intent = new Intent(MainActivity.this, MusicList.class);
               startActivity(intent);
           }

       });

       signupButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {
               changeActivitytoSıgnUp(view);
           }

       });
    }
    public void changeActivitytoSıgnUp(View view){
        Intent intent = new Intent(MainActivity.this,SignUpActicity.class);
        startActivity(intent);
    }
public void checkUser(String email,String password){
        if(kullanıcılar.contains(email)){
            for (Kullanıcılar kullanıcı : kullanıcılar
                 ) {
                if(kullanıcı.getEmail() == email && kullanıcı.getPassword() == password){

                }

            }
        }
}



}