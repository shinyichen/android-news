package com.shinyichen.androidnews.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shinyichen.androidnews.R;
import com.shinyichen.androidnews.databinding.SavedNewsItemBinding;
import com.shinyichen.androidnews.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SaveNewsAdapter extends RecyclerView.Adapter<SaveNewsAdapter.SavedNewsViewHolder>{

  interface ItemCallback {
    void onOpenDetails(Article article);
    void onRemoveFavorite(Article article);
  }

  private ItemCallback itemCallback;

  private List<Article> articles = new ArrayList<>();

  public void setArticles(List<Article> newsList) {
    articles.clear();
    articles.addAll(newsList);
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
    return new SavedNewsViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
    Article article = articles.get(position);
    holder.authorTextView.setText(article.author);
    holder.descriptionTextView.setText(article.description);
    holder.itemView.setOnClickListener( v ->
      itemCallback.onOpenDetails(article));
    holder.favoriteIcon.setOnClickListener( v ->
      itemCallback.onRemoveFavorite(article));
  }

  @Override
  public int getItemCount() {
    return articles.size();

  }

  public void setItemCallback(ItemCallback listener) {
    itemCallback = listener;
  }


  public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {

    TextView authorTextView;
    TextView descriptionTextView;
    ImageView favoriteIcon;

    public SavedNewsViewHolder(@NonNull View itemView) {
      super(itemView);
      SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
      authorTextView = binding.savedItemAuthorContent;
      descriptionTextView = binding.savedItemDescriptionContent;
      favoriteIcon = binding.savedItemFavoriteImageView;
    }
  }



}
