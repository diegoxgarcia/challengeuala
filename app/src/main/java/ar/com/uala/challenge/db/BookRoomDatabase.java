package ar.com.uala.challenge.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ar.com.uala.challenge.db.dao.BookDao;
import ar.com.uala.challenge.db.entity.BookEntity;

@Database(entities = {BookEntity.class}, version = 1)
public abstract class BookRoomDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    private static volatile BookRoomDatabase INSTANCE;

    public static BookRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (BookRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookRoomDatabase.class, "book_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }


}
