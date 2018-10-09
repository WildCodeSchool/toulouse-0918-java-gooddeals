package fr.wildcodeschool.gooddeals;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private EditText name , userId, password, confirmPassword;
    private Button btnRegistration;
    private TextView btnSignIn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getUIView();

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    String user_Email = userId.getText().toString().trim();
                    String user_password = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_Email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Registration.this,"Registration was Successfull" ,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this, MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(Registration.this, "Registration failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });


    }

    protected  void getUIView()
    {
        name = (EditText)findViewById(R.id.edName);
        userId =(EditText) findViewById(R.id.edUserId);
        password =(EditText) findViewById(R.id.edPass);
        confirmPassword =(EditText) findViewById(R.id.cfPass);
        btnRegistration =(Button) findViewById(R.id.RegBtn);
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);


    }

    protected boolean validate()
    {
        boolean result = false;


        if(name.getText().toString().isEmpty() || userId.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter your details in all fields", Toast.LENGTH_SHORT).show();

        }
        else if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            Toast.makeText(this, "Passsword does'nt match with confirm password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }

        return  result;
    }


}