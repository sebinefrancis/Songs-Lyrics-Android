package com.example.sebinefrancis.addsonglyrics;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sebine Francis on 26/08/2017.
 */

public  class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>{
    private Context context;
    private List<NotesBuilder> notesList = null;
    private ArrayList<NotesBuilder> allSongsList;
    private int lastPosition = -1;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    private final Context context;
    public MyViewHolder(View view) {
        super(view);
        context = itemView.getContext();
        title = (TextView) view.findViewById(R.id.title);
      //  content = (TextView) view.findViewById(R.id.content);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename= String.valueOf(((TextView)view).getText())+".txt";
                /*Snackbar.make(view, "opening "+filename, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                final Intent intent;
                intent =  new Intent(context, NoteSelect.class);
                intent.putExtra("name",filename);
                context.startActivity(intent);
               // String filename= String.valueOf(((TextView)view).getText());
               /* MainActivity t = new MainActivity();
                t.Open(filename);*/


             /*   Intent myIntent = new Intent(this, NoteSelect.class);
                MainActivity.this.startActivity(myIntent);*/
            }
        });

    }


}


    public NotesAdapter(List<NotesBuilder> notesList,Context context) {
        this.notesList = notesList;
        this.allSongsList = new ArrayList<NotesBuilder>();
        this.allSongsList.addAll(notesList);
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotesBuilder note = notesList.get(position);
        holder.title.setText(note.getTitle());
        setAnimation(holder.itemView, position);
       // holder.content.setText(note.getContent());
    }
    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }else{
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }


    public int filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        notesList.clear();
        if (charText.length() == 0) {
            notesList.addAll(allSongsList);
        }
        else
        {
            for (NotesBuilder wp : allSongsList)
            {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    notesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
        return notesList.size();
    }
}
