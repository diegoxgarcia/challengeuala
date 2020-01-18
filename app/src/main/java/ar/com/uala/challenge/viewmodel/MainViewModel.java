package ar.com.uala.challenge.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ar.com.uala.challenge.BookRepository;
import ar.com.uala.challenge.db.entity.BookEntity;
import ar.com.uala.challenge.retrofit.Books;

public class MainViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private LiveData<List<Books>> allRetrofitBooks;
    private LiveData<List<BookEntity>> allBooks;

    public MainViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
    }

    public LiveData<List<Books>> getAllRetrofitBooks(){
        allRetrofitBooks = bookRepository.getAllBooks();
        return allRetrofitBooks;
    }

    public LiveData<List<BookEntity>> getAllBooks(){
        allBooks = bookRepository.getAllByPopularidad();
        return allBooks;
    }

    public void insertBooks(List<Books> books){
        bookRepository.insertBooks(books);
    }

    public void deleteAllBooks(){
        bookRepository.deleteBooks();
    }
}
