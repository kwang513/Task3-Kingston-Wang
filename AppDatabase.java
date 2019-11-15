package android.example.infs3634_task_3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.example.infs3634_task_3.Model.Cat;

import androidx.room.Room;


/**
 * Refer to: https://developer.android.com/training/data-storage/room/index.html
 * The only difference here is that we've implemented a getInstance method.
 * This means that we're not building a new database each time we want to access the database.
 * We build the instance the first time, and then every following call will reuse the instance.
 * <p>
 * Example Usage:
 * <p>
 * AppDatabase db = AppDatabase.getInstance(context)
 * Book book = db.bookDao().findBookByIsbn()
 * List<Book> books = db.bookDao().getAllBooks()
 * ... etc (whatever methods you defined in your Dao).
 */
@Database(entities = {Cat.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CatDao catDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "CatDb")
                    .allowMainThreadQueries()   // <== IMPORTANT TO NOTE:
                    //     This is NOT correct to do in a completed app.
                    //     Next week we will fix it, but for now this
                    //     line is necessary for the app to work.
                    //     This line will basically allow the database
                    //     queries to freeze the app.
                    .build();
        }
        return instance;
    }
}
