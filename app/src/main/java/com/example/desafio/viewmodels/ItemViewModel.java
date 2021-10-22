package com.example.desafio.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.desafio.models.RepoResponse;
import com.example.desafio.repository.ItemRepository;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository itemRepository;
    private LiveData<RepoResponse> repoResponseLiveData;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        itemRepository = new ItemRepository();
        this.repoResponseLiveData = itemRepository.getItens("java", "stars");
    }

    public LiveData<RepoResponse> getRepoResponseLiveData() {
        return repoResponseLiveData;
    }

}
