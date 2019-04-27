package com.example.mark6.CustomList;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mark6.R;

public class OnSwipeListner implements View.OnTouchListener {
    int action_down_x;
    int action_up_x;
    boolean isSwipe = false;
    int difference;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action_down_x = (int) event.getX();
                isSwipe=false;  //until now
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isSwipe)
                {
                    action_up_x = (int) event.getX();
                    difference = action_down_x - action_up_x;
                    if(Math.abs(difference)>50)
                    {
                        ListView list = (ListView)v;
                        View view = list.getChildAt(list.pointToPosition((int)event.getX(),(int)event.getY()));
                        TextView textView = view.findViewById(R.id.name);
                        System.out.println("Name is "+textView.getText());
                        //swipe left or right
                        if(difference>0){
                            //swipe left

                        }
                        else{
                            //swipe right
                        }
                        isSwipe=true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                action_down_x = 0;
                action_up_x = 0;
                difference = 0;
                break;
        }
        return false;
    }
}
