package com.example.mark8.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.mark8.DTO.MainContext;
import com.example.mark8.DTO.Product;
import com.example.mark8.MainActivity;
import com.example.mark8.R;

public class SearchViewListener implements View.OnTouchListener {
    int action_down_x;
    int action_up_x;
    boolean isSwipe = false;
    int difference;
    private MainContext mainContext;
    private MainActivity context;

    public SearchViewListener(MainActivity context, MainContext mainContext){
        this.mainContext=mainContext;
        this.context = context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        RecyclerView list = (RecyclerView) v;
        View view = list.findChildViewUnder(event.getX(),event.getY());
        if(view==null){
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action_down_x = (int) event.getX();
                isSwipe=false;  //until now
                view.animate()
                        .translationY(-100)
                        .setDuration(200)
                        .setListener(null);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isSwipe)
                {
                    action_up_x = (int) event.getX();
                    difference = action_down_x - action_up_x;
                    if(Math.abs(difference)>50)
                    {
                        CardCustomAdapter.CustomHolder customHolder = (CardCustomAdapter.CustomHolder) list.findContainingViewHolder(view);
                        Product product = customHolder.product;
                        //swipe left or right
                        if(difference>50){
                            //add to save list
                            view.animate()
                                    .translationXBy(-1*difference)
                                    .setDuration(200);
                            mainContext.addtoSavedList(product);
                            context.getSavedView().findViewById(R.id.qrimage).setVisibility(View.GONE);
                            Toast toast = Toast.makeText(context, "Item "+product.getName()+" saved to list", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                            toast.show();
                        }
                        else if(difference < -50){
                            view.animate()
                                    .translationXBy(-1*difference)
                                    .setDuration(200);
                            //add to cartt
                            mainContext.addToCart(product);
                            Toast toast = Toast.makeText(context, "Item "+product.getName()+" added to cart", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                            toast.show();
                        }
                        isSwipe=true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                view.animate()
                        .translationY(0)
                        .translationX(0)
                        .setDuration(200)
                        .setListener(null);
                action_down_x = 0;
                action_up_x = 0;
                difference = 0;
                break;
        }
        return false;
    }

    public MainContext getMainContext() {
        return mainContext;
    }

    public void setMainContext(MainContext mainContext) {
        this.mainContext = mainContext;
    }
}
