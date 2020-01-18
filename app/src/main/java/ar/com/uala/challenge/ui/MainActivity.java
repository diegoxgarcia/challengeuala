package ar.com.uala.challenge.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ar.com.uala.challenge.R;
import ar.com.uala.challenge.constants.Constants;
import ar.com.uala.challenge.db.entity.BookEntity;
import ar.com.uala.challenge.retrofit.Books;
import ar.com.uala.challenge.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView booksRecyclerView;
    private RecyclerView.LayoutManager booksLayoutMgr;
    private BookRecyclerViewAdapter rvBooksAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_asc:
                mainViewModel.getAllBooksAsc().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;
            case R.id.menu_desc:
                mainViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            case R.id.menu_disp:
                mainViewModel.getAllAvailableBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            case R.id.menu_nodisp:
                mainViewModel.getAllNoAvailableBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            case R.id.menu_all:
                mainViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            case R.id.menu_linear:
                booksLayoutMgr = new LinearLayoutManager(this);
                booksRecyclerView.setLayoutManager(booksLayoutMgr);

                mainViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter = new BookRecyclerViewAdapter(bookEntities, R.layout.book_item_recyclerview, new BookRecyclerViewAdapter.BookRecyclerViewListener() {
                            @Override
                            public void itemOnClick(View v, int position) {
                                BookEntity be = bookEntities.get(position);
                                goToDetailBookActivity(be.getId());
                            }
                        });
                        booksRecyclerView.setAdapter(rvBooksAdapter);
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            case R.id.menu_grid:
                booksLayoutMgr = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                booksRecyclerView.setLayoutManager(booksLayoutMgr);

                mainViewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
                    @Override
                    public void onChanged(List<BookEntity> bookEntities) {
                        rvBooksAdapter = new BookRecyclerViewAdapter(bookEntities, R.layout.book_item_recyclerview, new BookRecyclerViewAdapter.BookRecyclerViewListener() {
                            @Override
                            public void itemOnClick(View v, int position) {
                                BookEntity be = bookEntities.get(position);
                                goToDetailBookActivity(be.getId());
                            }
                        });
                        booksRecyclerView.setAdapter(rvBooksAdapter);
                        rvBooksAdapter.setNewBooks(bookEntities);
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }@Override
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
                        goToDetailBookActivity(be.getId());
                    }
                });
                booksRecyclerView.setAdapter(rvBooksAdapter);
                rvBooksAdapter.setNewBooks(bookEntities);
            }
        });
    }

    private void goToDetailBookActivity(int idBook){
        Intent intent = new Intent(MainActivity.this, DetailBookActivity.class);
        intent.putExtra("idBook", idBook);
        startActivityForResult(intent, Constants.DETAIL_RESULT_ACTIVITY_REQUEST_CODE);
    }


}
