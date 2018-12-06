package com.example.vivek.rightswipe;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PagerAdpter extends PagerAdapter implements View.OnClickListener {
    private int arr[] = {R.drawable.ic_add_inr,R.drawable.ic_send_inr,R.drawable.ic_timeline,R.drawable.ic_send_gift};
    private String txt[] = {"Add Money","Send Money","Statement","Send Gift"};
    private int arr1[] = {R.drawable.ic_withdraw,R.drawable.ic_share,R.drawable.ic_aeps,R.drawable.ic_card};
    private String txt1[] = {"Withdraw","Refer & Earn","AEPS","Saved Cards"};

    Context context;
    LayoutInflater inflater;
    LinearLayout layout1,layout2,layout3,layout4;
    TextView textView[];
    TextView text1,text2,text3,text4;
    ImageView img1,img2,img3,img4;
    ImageView imageView[];
    FragmentActivity activity;

    //int position;


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    OnItemClick onItemClick;
    interface OnItemClick
    {
        void itemClick(int item);
    }

    public PagerAdpter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.header_pager_layout,container,false);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);

        layout1 = view.findViewById(R.id.item1);
        layout2 = view.findViewById(R.id.item2);
        layout3 = view.findViewById(R.id.item3);
        layout4 = view.findViewById(R.id.item4);

        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);

        textView = new TextView[4];
        textView[0] = text1;
        textView[1] = text2;
        textView[2] = text3;
        textView[3] = text4;

        imageView = new ImageView[4];
        imageView[0] = img1;
        imageView[1] = img2;
        imageView[2] = img3;
        imageView[3] = img4;

        if (position==0)
        {
            for(int i=0;i<arr.length;i++)
            {
                imageView[i].setImageResource(arr[i]);
                textView[i].setText(txt[i]);
            }
        }
        else
        {
            for (int i=0;i<arr1.length;i++)
            {
                imageView[i].setImageResource(arr1[i]);
                textView[i].setText(txt1[i]);
            }
        }

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);

        container.addView(view);
        return view;
    }
/*
    public void setLayout(int pos,int id)
    {

    }
*/
    @Override
    public void onClick(View v) {
        //Toast.makeText(activity, "Pos : "+position, Toast.LENGTH_SHORT).show();
        switch (v.getId())
        {
            case R.id.item1:
                onItemClick.itemClick(1);
                break;
            case R.id.item2:
                onItemClick.itemClick(2);
                break;
            case R.id.item3:
                onItemClick.itemClick(3);
                break;
            case R.id.item4:
                onItemClick.itemClick(4);
                break;
        }
    }
}
