package ar.com.uala.challenge.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ar.com.uala.challenge.db.entity.BookEntity;

@Dao
public interface BookDao {

    @Insert
    void insert(BookEntity bookEntity);

    @Query("DELETE FROM book_table")
    void deteleAll();

    @Query("SELECT * FROM book_table ORDER BY popularidad DESC")
    LiveData<List<BookEntity>> getAllOrderByPopularidad();



}
