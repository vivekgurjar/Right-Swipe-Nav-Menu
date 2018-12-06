package com.example.vivek.rightswipe;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DrawerLayout drawerLayout;
    RelativeLayout menuContainer;
    CoordinatorLayout mainContainer;
    ImageView navIcon;
    FloatingActionButton fab;


    SharedPreferences preferences;

    private long mLastClickTime = 0;
    int menuArr[] = {1,0,0,0,0,0};
    int currentItem;
    boolean isMenuOpen,isConnected,isNavAlive,isNavLocked,isNavClicked,isPro;

    LinearLayout item1,item2,item3,item4,item5,item6,logout,profile;
    ImageView img1,img2,img3,img5,img4,img6;
    TextView text1,text2,text3,text4,text5,text6,headText,proName,proMail;

    TextInputEditText searchText;
    ImageView clearText;

    float revealScale = 41f,revealWidth;
    AnimatorSet animatorSet;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        mainContainer = findViewById(R.id.mainContainer);
        menuContainer = findViewById(R.id.menuContainer);
        navIcon = findViewById(R.id.navIcon);

        final Animation wobble = AnimationUtils.loadAnimation(this,R.anim.wobble);

        fab = findViewById(R.id.fab);

        preferences = getSharedPreferences("sherLock",MODE_PRIVATE);

        item1 = findViewById(R.id.item1);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        item4 = findViewById(R.id.item4);
        item5 = findViewById(R.id.item5);
        item6 = findViewById(R.id.item6);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);

        img1 = findViewById(R.id.item1img);
        img2 = findViewById(R.id.item2img);
        img3 = findViewById(R.id.item3img);
        img4 = findViewById(R.id.item4img);
        img5 = findViewById(R.id.item5img);
        img6 = findViewById(R.id.item6img);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);

        searchText = findViewById(R.id.searchText);
        clearText = findViewById(R.id.clearText);

        headText = findViewById(R.id.headText);
        proName = findViewById(R.id.proName);
        proMail = findViewById(R.id.proMail);

        proName.setText(GlobalVariable.name);
        proMail.setText(GlobalVariable.email);


        currentItem = 0;
        item1.setZ(8f);

        animatorSet = new AnimatorSet();

        mainContainer.setElevation(30f);

    
        navIcon.setOnClickListener(this);
        item1.setOnClickListener(this);
        item2.setOnClickListener(this);
        item3.setOnClickListener(this);
        item4.setOnClickListener(this);
        item5.setOnClickListener(this);
        item6.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
        clearText.setOnClickListener(this);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Brandon_reg.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

				
				servicesFragment = new ServicesFragment();
                servicesFragment.setUpdateActivity(MainActivity.this);
                servicesFragment.setSearchTriggered(MainActivity.this);
                fragmentTransaction.add(R.id.uiContainer,servicesFragment,"Services");
                fragmentTransaction.addToBackStack("Services");
                fragmentTransaction.commit();
				
				
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setAnimation(wobble);
                Snackbar.make(v,"Offers Coming Soon....",Snackbar.LENGTH_SHORT)
                        .setAction("Acton",null)
                        .show();
            }
        });


        imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0)
                {
                    if (clearText.getVisibility()==View.INVISIBLE)
                    {
                        clearText.setVisibility(View.VISIBLE);
                    }
                    searchFragment.setData(""+s);
                }
                else
                {
                    clearText.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        revealWidth = (displayMetrics.widthPixels*revealScale)/100;

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

                mainContainer.animate()
                        .x(-(v*revealWidth))
                        .scaleY(1f-(0.25f*v))
                        .scaleX(1f-(0.25f*v))
                        .setDuration(0)
                        .start();
                menuContainer.animate()
                        .alpha(v)
                        .setDuration(0)
                        .start();
            }


            @Override
            public void onDrawerOpened(@NonNull View view) {
                if (!isNavClicked)
                {
                    navIcon.setImageDrawable(getDrawable(R.drawable.ic_back));
                }
                //navIcon.setImageResource(R.drawable.back_to_menu);
                isMenuOpen = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                if (!isNavClicked)
                {
                    if (isPro)
                    {
                        navIcon.setImageDrawable(getDrawable(R.drawable.ic_back));
                    }
                    else
                    {
                        navIcon.setImageDrawable(getDrawable(R.drawable.ic_menu));
                    }

                }
                //navIcon.setImageResource(R.drawable.menu_to_back);
                isMenuOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int i) {
                isNavClicked = false;
            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else
        {
            if (getSupportFragmentManager().getBackStackEntryCount()==1)
            {
                if (SystemClock.elapsedRealtime()-mLastClickTime<1000)
                {
                    finish();
                }
                else
                {
                    Snackbar.make(findViewById(R.id.fab), "Press Again to Exit", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Action", null).setDuration(1000).show();
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
            else
            {
                if (isNavAlive)
                {
                    isMenuOpen = true;
                    item1.performClick();
                    isMenuOpen = false;
                }
                else
                {
                    super.onBackPressed();
                    if (servicesFragment.isAlive)
                    {
                        fab.show();
                        headText.setText(R.string.your_App);
                        navIcon.setImageResource(R.drawable.back_to_menu);
                        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) navIcon.getDrawable();
                        avd.start();
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        isNavLocked = false;
                    }
                    else if (servicesFragment.getBusFragment()!=null)
                    {
                        if (servicesFragment.getBusFragment().isAlive)
                        {
                            searchText.setVisibility(View.INVISIBLE);
                            clearText.setVisibility(View.INVISIBLE);
                            searchText.setText("");
                            imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                            headText.setText(R.string.Bus_Ticket_Booking);
                        }
                    }
                }
            }
        }
    }

    //Click Events fo Activity....////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        isPro = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_y, R.anim.exit_y, R.anim.pop_enter_y, R.anim.pop_exit_y);
        switch (v.getId())
        {
            case R.id.clearText:
                searchText.setText("");
                break;
            case R.id.profile:
                if (isMenuOpen)
                {
                    isPro = true;
					Toast.makeText(getActivity(), "Profile Menu", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navIcon:
                if (isConnected)
                {
                    if (!isNavLocked)
                    {
                        isNavClicked = true;
                        if (isMenuOpen)
                        {
                            navIcon.setImageDrawable(getDrawable(R.drawable.back_to_menu));
                            isMenuOpen = false;
                            drawerLayout.closeDrawer(GravityCompat.END,true);

                        }
                        else
                        {
                            navIcon.setImageDrawable(getDrawable(R.drawable.menu_to_back));
                            isMenuOpen = true;
                            drawerLayout.openDrawer(GravityCompat.END,true);
                        }

                        //ObjectAnimator pathAnimator = ObjectAnimator.ofFloat()

                        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) navIcon.getDrawable();
                        drawable.start();
                    }
                    else
                    {
                        onBackPressed();
                    }

                }
                break;
            case R.id.item1:
                if (isMenuOpen)
                {
                    isNavAlive = false;
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                    if (isChecked(0))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.your_App);

                        updateMenu(item1,img1,text1,0);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.item2:
                if (isMenuOpen)
                {
                    isNavAlive = true;
                    Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show();
                    if (isChecked(1))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.Wallet);

                        
                        updateMenu(item2,img2,text2,1);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.item3:
                if (isMenuOpen)
                {
                    isNavAlive = true;
                    Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
                    if (isChecked(2))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.Transecton_History);
                        //Code to Switch to this Item..//////////////////////////////////////////////////////////////////////////
                       
                        updateMenu(item3,img3,text3,2);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.item4:
                if (isMenuOpen)
                {
                    isNavAlive = true;
                    Toast.makeText(this, "Password", Toast.LENGTH_SHORT).show();
                    if (isChecked(3))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.Change_Password);
                        //Code to Switch to this Item..//////////////////////////////////////////////////////////////////////////

                        

                        updateMenu(item4,img4,text4,3);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.item5:
                if (isMenuOpen)
                {
                    isNavAlive = true;
                    Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                    if (isChecked(4))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.help_and_support);
                        //Code to Switch to this Item..//////////////////////////////////////////////////////////////////////////

                        

                        updateMenu(item5,img5,text5,4);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.item6:
                if (isMenuOpen)
                {
                    isNavAlive = true;
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                    if (isChecked(5))
                    {
                        drawerLayout.closeDrawer(GravityCompat.END,true);
                    }
                    else
                    {
                        headText.setText(R.string.about);
                        //Code to Switch to this Item..//////////////////////////////////////////////////////////////////////////

                        
                        updateMenu(item6,img6,text6,5);
                        navIcon.performClick();
                    }
                }
                break;
            case R.id.logout:
                if (isMenuOpen)
                {
                    //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                    final androidx.appcompat.app.AlertDialog.Builder build = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                    build.setTitle("Logout!!").setMessage("Are you sure you want to exit.");

                    build.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    build.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    androidx.appcompat.app.AlertDialog dlg = build.create();
                    dlg.show();
                }
                break;
        }
    }

    public boolean isChecked(int item)
    {
        return menuArr[item]==1;
    }

    public void updateMenu(LinearLayout layout,ImageView imageView, TextView textView, int item)
    {
        LinearLayout currentLayout = item1;
        ImageView currentImg = img1;
        TextView currentText = text1;
        switch (currentItem)
        {
            case 0:
                currentLayout = item1;
                currentImg = img1;
                currentText = text1;
                break;
            case 1:
                currentLayout = item2;
                currentImg = img2;
                currentText = text2;
                break;
            case 2:
                currentLayout = item3;
                currentImg = img3;
                currentText = text3;
                break;
            case 3:
                currentLayout = item4;
                currentImg = img4;
                currentText = text4;
                break;
            case 4:
                currentLayout = item5;
                currentImg = img5;
                currentText = text5;
                break;
            case 5:
                currentLayout = item6;
                currentImg = img6;
                currentText = text6;
                break;
        }

        currentImg.setImageResource(R.drawable.ic_menuind);
        imageView.setImageResource(R.drawable.ic_menufill);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator zUp,zDown,alphaUp,alphaDown;

        zUp = ObjectAnimator.ofFloat(layout,"translationZ",8f);
        zUp.setDuration(150);
        zDown = ObjectAnimator.ofFloat(currentLayout,"translationZ",0f);
        zDown.setDuration(150);
        alphaUp = ObjectAnimator.ofFloat(textView,"alpha",1f);
        alphaUp.setDuration(150);
        alphaDown = ObjectAnimator.ofFloat(currentText,"alpha",0.5f);
        alphaDown.setDuration(150);

        animatorSet.play(zUp).with(zDown).with(alphaUp).with(alphaDown);
        zUp.start();
        zDown.start();
        alphaUp.start();
        alphaDown.start();

        menuArr[item] = 1;
        menuArr[currentItem] = 0;
        currentItem = item;
    }

    
    //Service Fragment Update Details../////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onUpdate(String tag) {
        headText.setText(tag);
        fab.hide();
        navIcon.setImageResource(R.drawable.menu_to_back);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        isNavLocked = true;
        AnimatedVectorDrawable avd  = (AnimatedVectorDrawable) navIcon.getDrawable();
        avd.start();
    }
}
