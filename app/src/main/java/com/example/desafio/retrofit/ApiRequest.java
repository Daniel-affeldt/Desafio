package com.example.desafio.retrofit;

import com.example.desafio.models.RepoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


//https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1


public interface ApiRequest {
    @GET("repositories")
    Call<RepoResponse> getRepoResponse(
            @Query("q") String query,
            @Query("sort") String apiKey,
            @Query("page") int page

    );
}
