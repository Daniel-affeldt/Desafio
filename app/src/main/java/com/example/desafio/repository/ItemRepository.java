package com.example.desafio.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.desafio.models.RepoResponse;
import com.example.desafio.retrofit.ApiRequest;
import com.example.desafio.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {

    private ApiRequest apiRequest;

    public ItemRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<RepoResponse> getItens(String query, String sort) {
        final MutableLiveData<RepoResponse> data = new MutableLiveData<>();
        apiRequest.getRepoResponse(query, sort, 1)
                .enqueue(new Callback<RepoResponse>() {

                    @Override
                    public void onResponse(Call<RepoResponse> call, Response<RepoResponse> response) {

                        if (response.body() != null) {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<RepoResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
