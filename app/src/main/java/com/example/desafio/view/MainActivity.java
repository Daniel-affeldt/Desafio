package com.example.desafio.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.desafio.R;
import com.example.desafio.adapters.ItemListAdapter;
import com.example.desafio.models.Item;
import com.example.desafio.models.RepoResponse;
import com.example.desafio.viewmodels.ItemViewModel;

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRepo;
    private LinearLayoutManager layoutManager;
    private ItemListAdapter adapter;
    private ArrayList<Item> itemArrayListAdapter = new ArrayList<>();
    private ArrayList<Item> itemArrayListRecebido = new ArrayList<>();

    ItemViewModel itemViewModel;
    Spinner spinnerOrdem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialization();
        SetupSearchView();
        getRepo();


    }


    private void initialization() {


        recyclerViewRepo = (RecyclerView) findViewById(R.id.recyclerViewRepo);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewRepo.setLayoutManager(layoutManager);
        recyclerViewRepo.setHasFixedSize(true);

        // adapter
        adapter = new ItemListAdapter(MainActivity.this, itemArrayListAdapter);
        recyclerViewRepo.setAdapter(adapter);


        //spinner
        spinnerOrdem = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapterspiner = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapterspiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerOrdem.setAdapter(adapterspiner);
        
        // View Model
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        //botão ordenar
        Button button = findViewById(R.id.buttonordenar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ordenarRecyclerView();
            }
        });


    }

    //em toda mudança de estado faz a busca no repositorio
    private void getRepo() {
        itemViewModel.getRepoResponseLiveData().observe(this, new Observer<RepoResponse>() {
            @Override
            public void onChanged(RepoResponse repoResponse) {
                if (repoResponse != null) {

                    List<Item> items = repoResponse.getItems();
                    //pega 2 lista do repositório para poder fazer as operações de filtro

                    itemArrayListRecebido.addAll(items);
                    itemArrayListAdapter.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Item> resultList = new ArrayList<Item>();

                for (Item item : itemArrayListRecebido) {
                    boolean ContainFullnameOrLogin = item.getName().contains(newText) || item.getOwner().getLogin().contains(newText);
                    if (ContainFullnameOrLogin) {
                        resultList.add(item);
                    }
                }

                itemArrayListAdapter.clear();
                itemArrayListAdapter.addAll(resultList);
                adapter.notifyDataSetChanged();
                return false;
            }
        });


    }

    //busca conforme o selecionado no spinner
    public void ordenarRecyclerView() {

        if (spinnerOrdem.getSelectedItem().equals("Nome")) {

            List<Item> itemsOrgName = itemArrayListAdapter.stream().sorted(Comparator.comparing(Item::getName)).collect(Collectors.toList());
            itemArrayListAdapter.clear();
            itemArrayListAdapter.addAll(itemsOrgName);
            adapter.notifyDataSetChanged();

        }

        if (spinnerOrdem.getSelectedItem().equals("Stars")) {

            List<Item> itemsOrgStars = itemArrayListAdapter.stream().sorted(Comparator.comparing(Item::getStargazersCount)).collect(Collectors.toList());
            itemArrayListAdapter.clear();
            itemArrayListAdapter.addAll(itemsOrgStars);
            adapter.notifyDataSetChanged();

        }

    }

}