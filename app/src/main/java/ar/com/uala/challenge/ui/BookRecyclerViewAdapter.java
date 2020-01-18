package ar.com.uala.challenge.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import ar.com.uala.challenge.R;
import ar.com.uala.challenge.db.entity.BookEntity;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder>{

    private List<BookEntity> books;
    private int layout;
    private static BookRecyclerViewListener onClickListener;

    public BookRecyclerViewAdapter(List<BookEntity> books, int layout, BookRecyclerViewListener listener) {
        this.books = books;
        this.layout = layout;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(books.get(position));
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setNewBooks(List<BookEntity> newBooks){
        this.books = newBooks;
        notifyDataSetChanged();
    }

    public interface BookRecyclerViewListener{
        void itemOnClick(View v, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImageBook;
        private TextView tvBookName;
        private TextView tvAuthorName;
        private TextView tvPop;
        private TextView tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageBook = itemView.findViewById(R.id.ivBookImage);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvAuthorName = itemView.findViewById(R.id.tvAuthorName);
            tvPop = itemView.findViewById(R.id.tvPop);
            tvEstado = itemView.findViewById(R.id.tvEstado);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemOnClick(view, getAdapterPosition());
                }
            });
        }

        private void bind(final BookEntity be){
            this.tvBookName.setText(be.getNombre());
            this.tvAuthorName.setText(be.getAutor());
            if(be.getPopularidad()!=0)
            this.tvPop.setText("" + be.getPopularidad());
            this.tvEstado.setText(be.isDisponibilidad() ? "Disponible" : "No Disponible");

            if(!"".equals(be.getImagen())){
                Picasso.get().load(be.getImagen()).resize(200,200).centerCrop().into(this.ivImageBook);
            }else{
                this.ivImageBook.setImageResource(R.drawable.uala);
            }

        }
    }
}
