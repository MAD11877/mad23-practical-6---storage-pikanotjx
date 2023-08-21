package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

public class MessageGroupActivity extends AppCompatActivity {

    private void replaceFragment(Class<? extends Fragment>fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group);

        // Adds click listeners to the buttons to toggle the fragments being shown
        Button group1Button = (Button) findViewById(R.id.group1Button);
        Button group2Button = (Button) findViewById(R.id.group2Button);

        group1Button.setOnClickListener(v -> {
            replaceFragment(Group1Fragment.class);
        });
        group2Button.setOnClickListener(v -> {
            replaceFragment(Group2Fragment.class);
        });
    }
}