package th.ac.su.cp.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import th.ac.su.cp.quizgame.model.WordItem;

public class WordListActivity extends AppCompatActivity {
    public static WordItem[] items = {new WordItem(R.drawable.cat, "CAT"),
            new WordItem(R.drawable.dog, "DOG"),
            new WordItem(R.drawable.dolphin, "DOLPHIN"),
            new WordItem(R.drawable.koala, "KOALA"),
            new WordItem(R.drawable.lion, "LION"),
            new WordItem(R.drawable.penguin, "PENGUIN"),
            new WordItem(R.drawable.pig, "PIG"),
            new WordItem(R.drawable.rabbit, "RABBIT"),
            new WordItem(R.drawable.tiger, "TIGER"),
            new WordItem(R.drawable.owl,"OWL")
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        List<WordItem> wordList= Arrays.asList(items);
        MyAdapter adapter = new MyAdapter(WordListActivity.this,wordList);
        LinearLayoutManager lm = new LinearLayoutManager(WordListActivity.this);
        RecyclerView rv = findViewById(R.id.word_list_recycler_view);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        final Context mContext;
        final List<WordItem> mWordList;
        //1 item only


        public MyAdapter(Context context, List<WordItem> wordList) {

            this.mContext = context;
            this.mWordList = wordList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //inflation layout and store in object and create MyViewHolder from View (layout)
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_word, parent, false);
            MyViewHolder vh = new MyViewHolder(mContext,v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.imageView.setImageResource(mWordList.get(position).imageResId);
            holder.wordTextView.setText(mWordList.get(position).word);
        }

        @Override
        public int getItemCount() {
           return mWordList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            View rootView;
            ImageView imageView;
            TextView wordTextView;
            WordItem item;

            public MyViewHolder( final Context context,@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                imageView = itemView.findViewById(R.id.imageView);
                wordTextView = itemView.findViewById(R.id.word_text_view);

                rootView.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, WordDetailsActivity.class);
                        intent.putExtra("word",item.word);
                        intent.putExtra("image",item.imageResId);

                        //String itemJSon = new Gson().toJson(item);
                        //intent.putExtra("item",itemJSon);
                        context.startActivity(intent);
                    }
                });

            }

        }
    }
}
