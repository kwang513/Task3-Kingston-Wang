package android.example.infs3634_task_3.database;

import android.example.infs3634_task_3.Model.Cat;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface CatDao {
    // Data Access Object for cats.
    // Here I define all my methods that you are allowed to use to access the books table in the
    // database.
    //
    // The methods are annotated with @Query("SQL code"), essentially saying whenever this method
    // is called, run this SQL query and return the results.

    // The onConflictStrategy determines how to handle when duplicate primary keys are inserted.
    // Without this, your app will only run once, and crash every time after because it keeps trying
    // to insert already existing primary keys.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCats(List<Cat> cats);

    //Week 9 Tutorial
    @Delete
    void delete(Cat entity);

    @Query("SELECT * FROM cat ORDER BY name ")
    List<Cat> getAllCatsSorted();

    @Query("SELECT * FROM cat WHERE id = :cat_id")
    Cat findCatById(String cat_id);


}
