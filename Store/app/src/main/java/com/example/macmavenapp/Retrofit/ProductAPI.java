package com.example.macmavenapp.Retrofit;

import com.example.macmavenapp.Model.Product;
import com.example.macmavenapp.Model.Specifications;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPI {

    RetrofitService retrofitService = new RetrofitService();
    ProductAPI productApi = retrofitService.getRetrofit().create(ProductAPI.class);
    @GET("/newproduct")
    Call<List<Product>> getNewProduct();

    @GET("bestsellers")
    Call<List<Product>> getBestSellers();
//    @FormUrlEncoded
    @GET("/search")
    Call<List<Product>> search(@Query("searchContent") String searchContent);
    @GET("/colors")
    Call<List<String>> getColor(@Query("productName") String productName, @Query("capacity") String capacity);
    @GET("/capacities")
    Call<List<String>> getCapacities(@Query("productName") String productName, @Query("color") String color);
    @GET("/filter-products")
    Call<Product> filterProducts(@Query("productName") String productName,
                                       @Query("color") String color,
                                       @Query("capacity") String capacity);
    @GET("/specifications")
    Call<Specifications> getSpecifications(@Query("product_id") int product_id);

}
