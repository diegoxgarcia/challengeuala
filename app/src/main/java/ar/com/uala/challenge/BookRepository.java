package ar.com.uala.challenge;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ar.com.uala.challenge.constants.Constants;
import ar.com.uala.challenge.db.BookRoomDatabase;
import ar.com.uala.challenge.db.dao.BookDao;
import ar.com.uala.challenge.db.entity.BookEntity;
import ar.com.uala.challenge.retrofit.Books;
import ar.com.uala.challenge.retrofit.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private BookDao bookDao;
    private LiveData<List<BookEntity>> books;

    public BookRepository(Application application) {
        BookRoomDatabase db = BookRoomDatabase.getDatabase(application);
        bookDao = db.bookDao();
        books = bookDao.getAllOrderByPopularidad();
    }


    // Repository for Room

    public LiveData<List<BookEntity>> getAllByPopularidad(){
        return books;
    };


    public void insertBooks(List<Books> books){
        new InsertBooksTask(bookDao).execute(books);
    }


    public void deleteBooks(){
        new DeleteAllBooksTask(bookDao).execute();
    }




    // AsyncTask for Books
    // Insert

    private static class InsertBooksTask extends AsyncTask<List<Books>, Void, Void>{
        private BookDao bookDao;

        public InsertBooksTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }


        @Override
        protected Void doInBackground(List<Books>... lists) {
            List<Books> listBooks = lists[0];

            for (Books book: listBooks) {
                BookEntity be = new BookEntity(book.getId(), book.getNombre(), book.getAutor(), book.isDisponibilidad(),
                        book.getPopularidad(), book.getImagen());
                bookDao.insert(be);
            }

            return null;
        }
    }

    private static class DeleteAllBooksTask extends AsyncTask<Void, Void, Void>{

        private BookDao bookDao;

        public DeleteAllBooksTask(BookDao bookDao) {
            this.bookDao = bookDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookDao.deteleAll();
            return null;
        }
    }



    // Repository for Retrofit

    // Init Retrofit
    private Service initRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.UALA_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        return service;
    }

    public LiveData<List<Books>> getAllBooks(){
        MutableLiveData<List<Books>> responseBooks = new MutableLiveData<>();

        Service service = initRetrofit();

        Call<List<Books>> booksCall = service.getAllBooks();
        booksCall.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                responseBooks.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {

            }
        });

        return responseBooks;
    }

}
