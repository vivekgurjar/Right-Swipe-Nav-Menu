package com.example.vivek.rightswipe;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ServicesFragment extends Fragment implements PagerAdpter.OnItemClick, View.OnClickListener {

    LinearLayout dotLayout;
    ViewPager viewPager;
    PagerAdpter pagerAdpter;
    int position;
    int slideHeight,initX,containerHeight;
    Custom_List_Fragment list_fragment;

    
    boolean isSubMenuOpen,isAlive;

    TextView textMore,view1,view2,view3,view4,view5,view6,view7,view8,view9,view10,view11,view12,view13,view14,view17,view18;
    LinearLayout slideSubMenu,containerSubMenu;
    FrameLayout contentContainer;

    String serviceTag;

    public void setSearchTriggered(SearchTriggered searchTriggered) {
        this.searchTriggered = searchTriggered;
    }
    SearchTriggered searchTriggered;
    interface SearchTriggered
    {
        void searchFragTriggered(SearchFragment searchFragment);
    }

    public void setUpdateActivity(UpdateActivity updateActivity) {
        this.updateActivity = updateActivity;
    }
    UpdateActivity updateActivity;
    interface UpdateActivity
    {
        void onUpdate(String tag);
    }

  

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_services, container, false);
        // Inflate the layout for this fragment

        viewPager = view.findViewById(R.id.viewPager);
        dotLayout = view.findViewById(R.id.dotLayout);

        textMore = view.findViewById(R.id.textMore);
        slideSubMenu = view.findViewById(R.id.slideSubMenu);
        containerSubMenu = view.findViewById(R.id.containerSubMenu);
        contentContainer = view.findViewById(R.id.contentContainer);

        list_fragment = new Custom_List_Fragment();

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);
        view5 = view.findViewById(R.id.view5);
        view6 = view.findViewById(R.id.view6);
        view7 = view.findViewById(R.id.view7);
        view8 = view.findViewById(R.id.view8);
        view9 = view.findViewById(R.id.view9);
        view10 = view.findViewById(R.id.view10);
        view11= view.findViewById(R.id.view11);
        view12 = view.findViewById(R.id.view12);
        view13 = view.findViewById(R.id.view13);
        view14= view.findViewById(R.id.view14);
        view17 = view.findViewById(R.id.view17);
        view18 = view.findViewById(R.id.view18);

        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);
        view7.setOnClickListener(this);
        view8.setOnClickListener(this);
        view9.setOnClickListener(this);
        view10.setOnClickListener(this);
        view11.setOnClickListener(this);
        view12.setOnClickListener(this);
        view13.setOnClickListener(this);
        view14.setOnClickListener(this);
        view17.setOnClickListener(this);
        view18.setOnClickListener(this);

        pagerAdpter = new PagerAdpter(getActivity());
        pagerAdpter.setOnItemClick(this);
        viewPager.setAdapter(pagerAdpter);

        contentContainer.post(new Runnable() {
            @Override
            public void run() {
                containerHeight = contentContainer.getHeight();
            }
        });

        containerSubMenu.post(new Runnable() {
            @Override
            public void run() {
                slideHeight = containerSubMenu.getHeight();
                initX = (int)containerSubMenu.getX();
                textMore.setOnClickListener(ServicesFragment.this);
            }
        });



        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        isAlive = true;
        getBal();
    }

    @Override
    public void onPause() {
        super.onPause();
        isAlive = false;
    }

    public void addDots(int pos)
    {
        if (dotLayout.getChildCount()!=0)
        {
            dotLayout.removeAllViews();
        }

        TextView[] pDots = new TextView[2];
        for (int i = 0; i< pDots.length; i++)
        {
            pDots[i] = new TextView(getActivity());
            pDots[i].setBackgroundColor(Color.TRANSPARENT);
            pDots[i].setText(Html.fromHtml("&#8226;"));
            pDots[i].setTextSize(35);
            pDots[i].setTextColor(Color.parseColor("#a1ffffff"));

            dotLayout.addView(pDots[i]);
        }

        if (pDots.length>0)
        {
            pDots[pos].setTextColor(Color.WHITE);
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
            position = i;

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    //Pager Item Selected..//////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void itemClick(int item) {
        //Toast.makeText(getActivity(), "Response : "+item, Toast.LENGTH_SHORT).show();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment frag;
        switch (item)
        {
            case 1:
                if (position==0)
                {
                    Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Withdraw", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (position==0)
                {
                    Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Refer", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if (position==0)
                {
                    Toast.makeText(getActivity(), "Statement", Toast.LENGTH_SHORT).show();
                   
                }
                else
                {
                    Toast.makeText(getActivity(), "AEPS", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                if (position==0)
                {
                    Toast.makeText(getActivity(), "Gift", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(), "Cards", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimator;
        float levelScale;
        float incrHeight = containerHeight;

        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment frag;

        switch (v.getId())
        {
            case R.id.textMore:
                if (isSubMenuOpen)
                {
                    contentContainer.getLayoutParams().height = containerHeight;
                    contentContainer.setLayoutParams(contentContainer.getLayoutParams());
                    levelScale = initX;
                    textMore.setText(R.string.More);
                    textMore.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_downarrow,0,0,0);
                }
                else
                {
                    incrHeight = incrHeight+slideHeight;
                    contentContainer.getLayoutParams().height = (int)incrHeight;
                    contentContainer.setLayoutParams(contentContainer.getLayoutParams());
                    levelScale = initX+slideHeight;
                    textMore.setText(R.string.Less);
                    textMore.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_uparrow,0,0,0);
                }
                objectAnimator = ObjectAnimator.ofFloat(slideSubMenu,"translationY",levelScale);
                animatorSet.play(objectAnimator);
                objectAnimator.setDuration(300);
                objectAnimator.start();
                isSubMenuOpen = !isSubMenuOpen;
                break;

            case R.id.view1:
			
				Toast.makeText(getActivity(), "Recharge", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view2:
			
				Toast.makeText(getActivity(), "Electricity Bill Pay", Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.view3:
			
				Toast.makeText(getActivity(), "DTH Bill Pay", Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.view4:
			
				Toast.makeText(getActivity(), "Datacard Recharge", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view5:
				Toast.makeText(getActivity(), "Gas Bill Payment", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view6:
				Toast.makeText(getActivity(), "Landline Bill Payment", Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.view7:
				Toast.makeText(getActivity(), "Broadband Bill Payment", Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.view8:
				Toast.makeText(getActivity(), "Water Bill Payment", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view9:
				Toast.makeText(getActivity(), "Bus Ticket Booking", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view10:
				Toast.makeText(getActivity(), "Train Ticket Booking", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view11:
				Toast.makeText(getActivity(), "Flight Ticket Booking", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view12:
				Toast.makeText(getActivity(), "Movie Ticket Booking", Toast.LENGTH_SHORT).show();
                break;
            case R.id.view13:
				Toast.makeText(getActivity(), "Insurance Premium Pay", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view14:
				Toast.makeText(getActivity(), "Metro Card Recharge", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view17:
				Toast.makeText(getActivity(), "Credit Card", Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.view18:
				Toast.makeText(getActivity(), "Google Play Recharge", Toast.LENGTH_SHORT).show();
               
                break;
        }
    }

  

}
