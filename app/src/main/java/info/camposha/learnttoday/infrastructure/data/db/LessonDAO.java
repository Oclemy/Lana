package info.camposha.learnttoday.infrastructure.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import info.camposha.learnttoday.domain.entity.Lesson;
import io.reactivex.Flowable;

/**
 * Dao stands for Data Access Object. This is our DAO interface. It:
 * 1. Will contain our CRUD methods
 */
@Dao
public interface LessonDAO {
    //NB= Methods annotated with @Insert can return either void, long, Long, long[],
    // Long[] or List<Long>.
    @Insert
    void insert(Lesson lesson);
    //Update methods must either return void or return int (the number of updated rows).
    @Update
    void update(Lesson lesson);
    //Deletion methods must either return void or return int (the number of deleted rows).
    @Delete
    void delete(Lesson lesson);

    /**
     * method to return our lessons and order them by date.
     * NB/= The Flowable class that implements the Reactive-Streams Pattern
     * and offers factory methods, intermediate operators and the ability
     * to consume reactive dataflows.
     * @return
     */
    @Query("SELECT * FROM LessonsTB ORDER BY date")
    Flowable<List<Lesson>> selectAll();

    //NB= Deletion methods must either return void or return int (the number of deleted
    // rows).
    @Query("delete from LessonsTB")
    void deleteAll();

}
