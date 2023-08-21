package sg.edu.np.mad.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    // Required properties for SQLiteOpenHelper
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MADPractical.db";
    public static final String TABLE_USERS = "users";

    // The name of all the columns in the table
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FOLLOWED = "followed";

    public UserDatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creates the table when the database is first initialized
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT,"
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FOLLOWED + " INTEGER"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Initializes the database with data into the users table
        final Object[][] data = {
                {"John Doe", "Software engineer from Seattle who loves hiking and photography", 1, true},
                {"Jane Smith", "Graphic designer from New York with a passion for painting and fashion", 2, false},
                {"Bob Johnson", "Freelance writer from Chicago who enjoys traveling and trying new foods", 3, true},
                {"Alice Williams", "Marketing manager from San Francisco with an interest in yoga and meditation", 4, false},
                {"David Brown", "Data analyst from Boston who likes playing basketball and reading books", 5, true},
                {"Emily Davis", "HR specialist from Austin who enjoys listening to music and going to concerts", 6, false},
                {"Michael Miller", "Sales representative from Miami with a love for fishing and boating", 7, true},
                {"Sarah Wilson", "Project manager from Denver who likes skiing and snowboarding in the winter", 8, false},
                {"Chris Taylor", "Web developer from Portland with a passion for cycling and rock climbing", 9, true},
                {"Laura Anderson", "Accountant from Atlanta who enjoys cooking and trying new recipes", 10, false},
                {"James Thomas", "Lawyer from Washington D.C. who likes playing golf and watching movies", 11, true},
                {"Elizabeth Jackson", "Teacher from Nashville who loves gardening and bird watching", 12, false},
                {"William White", "Architect from Los Angeles with an interest in history and architecture", 13, true},
                {"Sophia Harris", "Nurse from Philadelphia who enjoys volunteering and helping others", 14, false},
                {"Richard Martin", "Chef from New Orleans with a passion for creating new dishes and flavors", 15, true},
                {"Ava Thompson", "Photographer from Las Vegas who loves capturing moments and memories", 16, false},
                {"Joseph Garcia", "Musician from Memphis with a love for playing guitar and writing songs", 17, true},
                {"Lily Martinez", "Artist from San Diego who enjoys painting landscapes and seascapes", 18, false},
                {"George Robinson", "Journalist from Dallas with an interest in politics and current events", 19, true},
                {"Olivia Clark", "Fashion designer from Phoenix with a passion for creating unique and stylish clothing", 20, false}
        };

        for (Object[] user : data) {
            User newUser = new User((String) user[0], (String) user[1], (int) user[2], (boolean) user[3]);
            addUser(newUser);
        }
    }

    // Runs when the database version is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Deletes the table and creates a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user) {
        // Creates a ContentValues object to store the values to be inserted
        // ContentValues is similar to a HashMap and is used to store key-value pairs to be inserted into the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);
        values.put(COLUMN_DESC, user.description);
        values.put(COLUMN_ID, user.id);
        values.put(COLUMN_FOLLOWED, user.followed);

        // Gets the database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Inserts the values into the database and closes the database connection
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void updateUser(User user) {
        // Creates a ContentValues object to store the values to be updated
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.name);
        values.put(COLUMN_DESC, user.description);
        values.put(COLUMN_ID, user.id);
        values.put(COLUMN_FOLLOWED, user.followed);

        // Gets the database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Updates the values in the database and closes the database connection
        db.update(TABLE_USERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.id)});
        db.close();
    }

    public ArrayList<User> getUsers() {
        // Creates an ArrayList to store the users
        ArrayList<User> users = new ArrayList<>();

        // Creates a query to get all the users from the database
        String query = "SELECT * FROM " + TABLE_USERS;

        // Gets the database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        // Executes the query and stores the results in a Cursor object
        // A Cursor object is an object that points to a single row of the query results
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Creates a new User object with the data from the current row
                User user = new User(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3) == 1);

                // Adds the user to the ArrayList
                users.add(user);
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Closes the database connection and returns the ArrayList
        db.close();

        return users;
    }
}