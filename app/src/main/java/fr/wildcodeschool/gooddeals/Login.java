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



public class MainActivity extends AppCompatActivity {

    private EditText userName ;
    private EditText password;
    private Button   login;
    private TextView  attempt, btnSignUp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText)findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.button_password);
        login  =(Button) findViewById(R.id.btnLogin);
        btnSignUp = (TextView) findViewById(R.id.tvSingUP);

        attempt.setText("No of attempts:" + counter);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser User = firebaseAuth.getCurrentUser();

        if (User !=null )
        {
            finish();
            startActivity(new Intent(MainActivity.this, Profil.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userName.getText().toString(), password.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registration.class));
            }
        });

    }

    private void validate(String userName , String pass)

    {

        firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Login was Sucessfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Profil.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
                    counter --;
                    attempt.setText("No of attempts:" + counter);
                    if (counter == 0)
                    {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

}