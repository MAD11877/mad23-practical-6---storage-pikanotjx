package sg.edu.np.mad.madpractical;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {

    ArrayList<User> data;

    public PeopleAdapter(ArrayList<User> input) {
        data = input;
    }

    // This method is called by the adapter to create a view holder.
    // The adapter will inflate the item layout and pass it to the view holder.
    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item;

        if (viewType == 1)
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.alt_people_list_item, parent, false);
        else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_list_item, parent, false);
        }

        return new PersonViewHolder(item);
    }

    // This method is called by the adapter to bind the data to the view holder.
    // The adapter will iterate through the data array and call this method for each item.
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        User user = data.get(position);
        holder.nameTextView.setText(user.name);
        holder.descriptionTextView.setText(user.description);

        // Adds an event listener to the image view that shows an alert when tapped
        holder.imageView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Profile");
            builder.setMessage(user.name);
            builder.setPositiveButton("View", (dialog, id) -> {
                // Creates an Intent to MainActivity
                Intent mainActivity = new Intent(v.getContext(), MainActivity.class);

                // Generates a random integer and passes it to MainActivity
                // int random = (int) (Math.random() * 1000000000);
                // mainActivity.putExtra("id", random);

                // Gets the user's name, description, and follow status and passes it to MainActivity
                mainActivity.putExtra("name", user.name);
                mainActivity.putExtra("description", user.description);
                mainActivity.putExtra("id", user.id);
                mainActivity.putExtra("followed", user.followed);

                v.getContext().startActivity(mainActivity);
            });
            builder.setNegativeButton("Close", (dialog, id) -> {

            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    // Used to determine the view type of the item at the specified position. This is useful if
    // there are different ways to display the data depending on the position.
    @Override
    public int getItemViewType(int position) {
        // Checks if a name includes a case-insensitive g (7th letter)
        return data.get(position).name.toLowerCase().contains("g") ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView imageView;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.listNameTextView);
            descriptionTextView = itemView.findViewById(R.id.listDescriptionTextView);
            imageView = itemView.findViewById(R.id.listImageView);
        }
    }
}