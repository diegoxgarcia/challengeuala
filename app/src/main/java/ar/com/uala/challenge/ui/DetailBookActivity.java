package ar.com.uala.challenge.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.uala.challenge.R;
import ar.com.uala.challenge.db.entity.BookEntity;
import ar.com.uala.challenge.viewmodel.MainViewModel;

public class DetailBookActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        setTitle("Detalle del Libro");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getBookById(getIntent().getIntExtra("idBook", 0)).observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntities) {
                ImageView iv = findViewById(R.id.ivImage);
                    TextView tvName = findViewById(R.id.tvBookName);
                    TextView tvAuthor = findViewById(R.id.tvAuthorName);
                    TextView tvEstado = findViewById(R.id.tvEstado);

                    BookEntity be = bookEntities.get(0);
                    tvName.setText(be.getNombre());
                    tvAuthor.setText(be.getAutor());
                    tvEstado.setText(be.isDisponibilidad() ? "Disponible" : "No Disponible");

                    if(!"".equals(be.getImagen())){
                        Picasso.get().load(be.getImagen()).resize(200,200).centerCrop().into(iv);
                    }else{
                        iv.setImageResource(R.drawable.uala);
                    }

            }
        });


    }
}
