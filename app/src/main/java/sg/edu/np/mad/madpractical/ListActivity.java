package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    UserDatabaseHandler dbHandler = new UserDatabaseHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Gets the list of users from the database
        ArrayList<User> userList = dbHandler.getUsers();

        // Creates the recycler view and sets the adapter
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listRecyclerView);
        PeopleAdapter peopleAdapter = new PeopleAdapter(userList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(peopleAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Adds an event listener to messageGroupButton to start MessageActivity
        Button messageGroupButton = (Button) findViewById(R.id.messageGroupButton);
        messageGroupButton.setOnClickListener(v -> {
            Intent messageActivity = new Intent(this, MessageGroupActivity.class);
            startActivity(messageActivity);
        });
    }
}