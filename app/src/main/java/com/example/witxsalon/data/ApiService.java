package com.example.witxsalon.data;

import com.example.witxsalon.ProductReview;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // Define the endpoint for fetching product reviews
    @GET("https://jsonplaceholder.typicode.com/users\n")
    Call<List<ProductReview>> getProductReviews();
}