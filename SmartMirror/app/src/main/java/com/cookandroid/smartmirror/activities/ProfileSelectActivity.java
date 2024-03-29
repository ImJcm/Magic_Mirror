package com.cookandroid.smartmirror.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.smartmirror.Methods;
import com.cookandroid.smartmirror.R;
import com.cookandroid.smartmirror.dataClass.MyApplication;

import java.sql.Array;
import java.util.ArrayList;

public class ProfileSelectActivity extends AppCompatActivity {
    ArrayList<LinearLayout> plsList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    static int set_mode_check = 0;  //편집 or 이동모드 설정
    static int selectProfileNum = 0;    //선택한 profile 번호
    GridLayout profileGridLayout;
    ImageView setBtn, addBtn;

    int imgViewWidth;
    int imgViewHeight;

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication app = (MyApplication) getApplicationContext();
        nameList = app.getProfileNameList();
    }
    public void drawProfileList(ArrayList<String> nameList2){
        // 변수들
        imgViewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        imgViewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        setBtn = findViewById(R.id.profileSetting);
        addBtn = findViewById(R.id.profileAdd);
        profileGridLayout = findViewById(R.id.profileGrid);

        //DB에서 저장된 프로필 개수로 설정해야함
        for(int i=0; i< nameList2.size(); i++) {
            // 프로필 사진+프로필명 생성을 위한 LinLayout
            LinearLayout profile1 = new LinearLayout(this);
            LinearLayout.LayoutParams profileLinParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            profile1.setLayoutParams(profileLinParam);
            profile1.setGravity(Gravity.CENTER);
            profile1.setOrientation(LinearLayout.VERTICAL);

            // 프로필 사진(ImageView)
            ImageView profileImg = new ImageView(this);
            profileImg.setId(i+1);
            profileImg.setImageResource(R.drawable.ic_baseline_image_24);
            LinearLayout.LayoutParams imgViewParams = new LinearLayout.LayoutParams(imgViewWidth, imgViewHeight);
            profileImg.setLayoutParams(imgViewParams);
            profileImg.setOnClickListener(new OnClickListenerPutIndex(i) {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                    MyApplication app = (MyApplication) getApplicationContext();
                    app.setSelectedProfileName(nameList.get(index));
                    System.out.println(nameList.get(index)+"의 프로필이 선택되었습니다.");
                    startActivity(intent);

                }
            });

            // 프로필 명(TextView)
            TextView profileTextView = new TextView(this);
            LinearLayout.LayoutParams tViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            profileTextView.setLayoutParams(tViewParams);
            profileTextView.setText(nameList.get(i));
            profileTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Layout에 추가
            profile1.addView(profileImg);
            profile1.addView(profileTextView);

            // GridLayout에 추가.
            profileGridLayout.addView(profile1, i);

            plsList.add(profile1);
        }
        //프로필추가 클릭 시
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프로필 추가 화면으로 넘어갔다가 다시 돌아오므로 finish 불필요.
                selectProfileNum = 0;
                Intent i = new Intent(getApplicationContext(), ProfileSettingActivity.class);
                i.putExtra("mode", "add");
                startActivity(i);
            }
        });

        //프로필 선택화면에서 click event로 기능 선택
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfile();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_select);
        MyApplication app = (MyApplication) getApplicationContext();
        nameList = app.getProfileNameList();
        profileGridLayout = findViewById(R.id.profileGrid);

        // Add Coustom AppBar & Set Title Color Gradient
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tvTitle = toolbar.findViewById(R.id.toolbarTv);
        Methods methods = new Methods();
        methods.setGradient(getColor(R.color.titleStart), getColor(R.color.titleEnd), tvTitle);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        drawProfileList(nameList);

    }

    void changeProfile() {

        if(set_mode_check == 0) {
            for(int i=0;i< plsList.size();i++) {
                ImageView imageView = plsList.get(i).findViewById(i+1);
                System.out.println("profileImg 아이디 in 함수:"+imageView.getId());
                imageView.setImageResource(R.drawable.ic_baseline_image_24);
                imageView.setOnClickListener(new OnClickListenerPutIndex(i) {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
                        MyApplication app = (MyApplication) getApplicationContext();
                        app.setSelectedProfileName(nameList.get(index));
                        System.out.println(nameList.get(index)+"의 프로필이 선택되었습니다.");
                        startActivity(intent);
                    }
                });
            }
            set_mode_check = 1;
        } else {
            for(int i=0;i<plsList.size();i++) {
                ImageView imageView = plsList.get(i).findViewById(i+1);
                imageView.setImageResource(R.drawable.ic_baseline_offline_pin_24);
                imageView.setOnClickListener(new OnClickListenerPutIndex(i) {
                    @Override
                    public void onClick(View v) {
                        selectProfileNum = v.getId();
                        Intent intent = new Intent(getApplicationContext(), ProfileSettingActivity.class);
                        intent.putExtra("mode", "edit");
                        intent.putExtra("index", index);

                        MyApplication app = (MyApplication) getApplicationContext();
                        app.setSelectedProfileName(nameList.get(index));
                        startActivity(intent);

                    }
                });
            }
            set_mode_check = 0;
        }

    }
    public abstract class OnClickListenerPutIndex implements View.OnClickListener{
        protected int index;
        public OnClickListenerPutIndex(int index){
            this.index = index;
        }
    }
}