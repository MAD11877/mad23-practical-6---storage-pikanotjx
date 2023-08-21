package sg.edu.np.mad.madpractical;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sg.edu.np.mad.madpractical.R;

public class MainActivity extends AppCompatActivity {

    // private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDatabaseHandler dbHandler = new UserDatabaseHandler(this, null, null, 1);

        // Gets the data passed from ListActivity
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        int id = getIntent().getIntExtra("id", 0);
        final boolean[] followed = {getIntent().getBooleanExtra("followed", false)}; // In this case, we cannot use a simple boolean because we need to modify the value of the boolean inside the event listener. Anonymous classes and lambda expressions cannot modify local variables, so we need to use a final one-element array instead.

        // Initializes a dummy user
        // user = new User("MAD " + id, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 1, false);

        // Changes the TextView values
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        Button followButton = findViewById(R.id.followButton);
        nameTextView.setText(name);
        descriptionTextView.setText(description);
        followButton.setText(followed[0] ? "Unfollow" : "Follow");
//        name.setText(user.getName());
//        description.setText(user.getDescription());

        // Creates and sets event listeners for the buttons
        followButton.setOnClickListener(v -> {
            followed[0] = !followed[0];
            followButton.setText(followed[0] ? "Unfollow" : "Follow");

            // Updates the user in the database
            dbHandler.updateUser(new User(name, description, id, followed[0]));

            // Creates a Toast indicating the user's actions
            Toast.makeText(getApplicationContext(), followed[0] ? "Followed" : "Unfollowed", Toast.LENGTH_SHORT).show();
//            user.setFollowed(!user.isFollowed());
//            followButton.setText(user.isFollowed() ? "Unfollow" : "Follow");
        });
    }
}