package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText email,password1,password2;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        auth=FirebaseAuth.getInstance();
        register=findViewById(R.id.register);
        email=findViewById(R.id.email);
        password1=findViewById(R.id.password1);
        password2=findViewById(R.id.password2);

        
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {
        String userEmail = email.getText().toString();
        String userPassword1 = password1.getText().toString();
        String userPassword2 = password2.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email vacio!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(userPassword1)){
            Toast.makeText(this,"Contraseña vacia!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword2)){
            Toast.makeText(this,"Confirme la contraseña!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!TextUtils.equals(userPassword1,userPassword2)){
            Toast.makeText(this,"Las contraseñas no coinciden!",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Registro completado con exito!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Error en el registro!"+task.getException() ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
