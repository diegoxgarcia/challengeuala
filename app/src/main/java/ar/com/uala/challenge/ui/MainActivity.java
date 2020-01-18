package ar.com.uala.challenge.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import ar.com.uala.challenge.R;
import ar.com.uala.challenge.db.entity.BookEntity;
import ar.com.uala.challenge.retrofit.Books;
import ar.com.uala.challenge.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView booksRecyclerView;
    private RecyclerView.LayoutManager booksLayoutMgr;
    private BookRecyclerViewAdapter rvBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Listado de libros");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        booksRecyclerView = findViewById(R.id.recyclerView);
        booksLayoutMgr = new LinearLayoutManager(this);
        booksRecyclerView.setLayoutManager(booksLayoutMgr);
        mainViewModel.deleteAllBooks();
        mainViewModel.getAllRetrofitBooks().observe(this, new Observer<List<Books>>() {
            @Override
            public void onChanged(List<Books> books) {
                mainViewModel.insertBooks(books);
                getAllBooks();
            }
        });

    }
    private void getAllBooks(){
        mainViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntities) {
                rvBooksAdapter = new BookRecyclerViewAdapter(bookEntities, R.layout.book_item_recyclerview, new BookRecyclerViewAdapter.BookRecyclerViewListener() {
                    @Override
                    public void itemOnClick(View v, int position) {
                        BookEntity be = bookEntities.get(position);
                    }
                });
                booksRecyclerView.setAdapter(rvBooksAdapter);
                rvBooksAdapter.setNewBooks(bookEntities);
            }
        });
    }


}
