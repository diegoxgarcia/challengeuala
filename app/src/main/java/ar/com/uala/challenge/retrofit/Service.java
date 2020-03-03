package ar.com.uala.challenge.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface Service {

    @Headers("Content-Type: application/json")
    @GET("books")
    Call<List<Books>> getAllBooks();

}
