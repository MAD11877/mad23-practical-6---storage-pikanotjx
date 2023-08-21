package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Add variables for relevant components
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        // Defines the Realtime Database instance
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        // Adds an event listener to loginButton
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.equals("") || password.equals("")) {
                Toast.makeText(Login.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticates the user
            ref.child("users").get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Checks if the username exists
                    if (task.getResult().hasChild(username)) {
                        // Checks if the password is correct
                        if (task.getResult().child(username).child("password").getValue().equals(password)) {
                            Toast.makeText(Login.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                            Intent listActivity = new Intent(Login.this, ListActivity.class);
                            startActivity(listActivity);
                        }
                        else {
                            Toast.makeText(Login.this, "The password you entered is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Login.this, "The username you entered does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}