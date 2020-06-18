package info.camposha.learnttoday.infrastructure.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import info.camposha.learnttoday.domain.entity.Lesson;

/**
 * Our abstract MyRoomDB class. It will extend the RoomDatabase class,
 * which is the base class for room databases.
 *
 * If you change anything in the Lesson class, then you will have to increment
 * the version since the generated table will need to be migrated using by the
 * Room compiler.
 */
@Database(entities = {Lesson.class}, version = 2, exportSchema = false)
public abstract class MyRoomDB extends RoomDatabase {

    private static MyRoomDB myRoomDB;

    public abstract LessonDAO lessonDAO();

    /**
     *This factory method will instantiate for us our MyRoomDB class
     * @param c - A Context Object
     * @return
     */
    public static MyRoomDB getInstance(Context c) {
        if (myRoomDB == null) {
            myRoomDB = Room.databaseBuilder(c, MyRoomDB.class,
                    "MyRoomDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myRoomDB;
    }
}
//end