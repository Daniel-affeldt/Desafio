package com.example.desafio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.desafio.R;
import com.example.desafio.models.Item;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private Context context;
    ArrayList<Item> itemArrayList;

    public ItemListAdapter(Context context, ArrayList<Item> itemArrayList) {
        this.context = context;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item_repositorio, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder viewHolder, int i) {

        Item item = itemArrayList.get(i);

        viewHolder.tvNomeRepo.setText(item.getFullName());
        viewHolder.tvDescricao.setText(" " + item.getDescription());
        viewHolder.tvNome.setText(item.getOwner().getLogin());
        viewHolder.tvNomeCompleto.setText("site: " + item.getOwner().getOrganizationsUrl());
        viewHolder.tvForkCount.setText(item.getForksCount().toString());
        viewHolder.tvStarCount.setText(item.getStargazersCount().toString());


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(item.getOwner().getAvatarUrl()).override(200, 200) // resizing
                .centerCrop().apply(options)
                .into(viewHolder.imgViewOwner);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewOwner;
        private final TextView tvNomeRepo;
        private final TextView tvDescricao;
        private final TextView tvStarCount;
        private final TextView tvNome;
        private final TextView tvNomeCompleto;
        private final TextView tvForkCount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgViewOwner = (ImageView) itemView.findViewById(R.id.imageViewOwner);
            tvNomeRepo = (TextView) itemView.findViewById(R.id.textViewNomeRepo);
            tvDescricao = (TextView) itemView.findViewById(R.id.textViewdescricao);
            tvStarCount = (TextView) itemView.findViewById(R.id.textViewStarCount);
            tvNome = (TextView) itemView.findViewById(R.id.textViewnome);
            tvNomeCompleto = (TextView) itemView.findViewById(R.id.textViewnomecompleto);
            tvForkCount = (TextView) itemView.findViewById(R.id.textViewForkCount);
        }
    }
}



