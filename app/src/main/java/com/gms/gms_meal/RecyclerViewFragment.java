package com.gms.gms_meal;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.gms.gms_meal.Dev.MealItemData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    ArrayList<MealItemData> mealItemDataArrayList = new ArrayList<MealItemData>();
     int pos;

//    RecyclerViewFragment newInstance(int position) {
//        pos = position;
//
//        return new RecyclerViewFragment();
//    }
    RecyclerViewFragment(int position){
        pos = position;
    }
    RecyclerViewFragment getFrag(){
        return this;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        for (int i = 0; i < 10; ++i) {
//            mealItemDataArrayList.add(new MealItemData("1025","zs"));
            new GetMeal().execute(i);
            Log.e("position","/"+pos);
        }

        mAdapter = new RecyclerViewMaterialAdapter(new TestRecyclerViewAdapter(mealItemDataArrayList));
        mRecyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }


    public class GetMeal extends AsyncTask<Integer, Void, String[]> {


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @SuppressWarnings("deprecation")
        @SuppressLint("SimpleDateFormat")
        @Override
        protected String[] doInBackground(Integer... AddDay) {
            // String date, day, lunchText, dinnerText;
            String[] info = new String[4];
            URL url = null; // URL변수선언
            HttpURLConnection urlConnection = null; // URL연결요청 변수 선언
            BufferedInputStream buf = null;// 버퍼에 입력할 변수
            try {// 시도함



                Date now = new Date();

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                now.setDate(now.getDate() + AddDay[0]);
                // String http =
                // "http://www.gms.hs.kr/lunch.view?date=20140901";
                String http = "http://www.gms.hs.kr/lunch.view?date=" + format.format(now);

                format = new SimpleDateFormat("yyyy/MM/dd");
                info[0] = format.format(now);
                // Date.setText(format.format(now));

                url = new URL(http);// ULR변수에 요청할 "오늘 자  급식" ULR
                urlConnection = (HttpURLConnection) url.openConnection();// URL요청

                buf = new BufferedInputStream(urlConnection.getInputStream());// 버퍼에
                // ULR삽입

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "euc-kr")); // 한글 인코딩으로 버퍼를
                // 읽음

                String line = null; // 문자열변수 line 선언 뒤, null로 비움.
                String Lunch_page = ""; // null대신 line이 순서대로 들어갈 것이므로, 공백처리.
                String Dinner_page = "";

                int Week;
                Calendar Cal_Info = Calendar.getInstance();
                Week = Cal_Info.get(Calendar.DAY_OF_WEEK) + AddDay[0];



                if (6 < Week)
                    Week %= 7;

                switch (Week) {
                    case 0:
                        // Day.setText("토요일");
                        info[1] = "토요일";
                        break;
                    case 1:
                        // Day.setText("일요일");
                        info[1] = "일요일";
                        break;
                    case 2:
                        // Day.setText("월요일");
                        info[1] = "월요일";
                        break;
                    case 3:
                        // Day.setText("화요일");
                        info[1] = "화요일";
                        break;
                    case 4:
                        // Day.setText("수요일");
                        info[1] = "수요일";
                        break;
                    case 5:
                        // Day.setText("목요일");
                        info[1] = "목요일";
                        break;
                    case 6:
                        // Day.setText("금요일");
                        info[1] = "금요일";
                        break;

                }

                switch (Week) {
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        while ((line = bufreader.readLine()) != null) { // 요청한 ULR
                            // 소스(html)라인이 비어있지
                            // 않을 경우.
                            Lunch_page += line; // "page" 문자열 변수에 "line" 문자열 변수를
                            // 순서대로
                            // 입력.
                            Dinner_page += line;
                        }
                        info[2] = Get_Lunch(Lunch_page);
                        info[3] = Get_Dinner(Dinner_page);
                        break;

                    case 0:
                    case 1:

                        // Lunch_Text.setText("오늘의 급식은 휴일인 관계로 없습니다.");
                        info[2] = "점심정보가 없습니다.";
                        // Dinner_Text.setText("오늘의 급식은 휴일인 관계로 없습니다.");
                        info[3] = "석식정보가 없습니다.";
                        break;
                }

            } catch (Exception e) {// 에러발생시 실행.
                Log.e("Meal", "succes " + e);
                // Lunch_Text.setText("데이터 연결을 확인해 주십시오.");// 에러이벤트와 함께 들어오는 메세지
                // 출력.
                info[2] = "데이터 연결을 확인해 주십시오.";
                // Dinner_Text.setText("데이터 연결을 확인해 주십시오.");
                info[3] = "데이터 연결을 확인해 주십시오.";

            }finally {
                urlConnection.disconnect();
            }

            for (int i = 0; i < info.length; i++) {
                Log.e("get", "//" + info[i]);
            }
            return info;
        }

        protected void onPostExecute(String[] Info) {
            if (pos == 0) {
                mealItemDataArrayList.add(new MealItemData(Info[0],Info[1], Info[2]));
            } else if(pos == 1){
                mealItemDataArrayList.add(new MealItemData(Info[0],Info[1], Info[3]));
            }
            mAdapter.notifyDataSetChanged();


        }


        private String Get_Lunch(String Lunch_page_Alpha) {

            try {

                int Lunch_Start, Lunch_End;

                Lunch_Start = Lunch_page_Alpha.indexOf("morning");
                Lunch_End = Lunch_page_Alpha.indexOf("calorie");

                Lunch_page_Alpha = Lunch_page_Alpha.substring(Lunch_Start, Lunch_End);

                Lunch_page_Alpha = Lunch_page_Alpha.replaceAll("[^가-힝 <br <]", "");
                Lunch_page_Alpha = Lunch_page_Alpha.replaceAll("[<br]", " ");
                Lunch_page_Alpha = Lunch_page_Alpha.replaceAll("중식", "");

                Lunch_page_Alpha = Lunch_page_Alpha.trim();

                Log.e("text", Lunch_page_Alpha);

                // Lunch_Text.setText(Lunch_page_Alpha);

            } catch (Exception e) {
                Log.e("Meal", "succes " + e);
                Lunch_page_Alpha = "급식정보가 없습니다.";
                // Lunch_Text.setText("급식정보가 없습니다.");// 에러이벤트와 함께 들어오는 메세지 출력.
            }
            return Lunch_page_Alpha;

        }

        private String Get_Dinner(String Dinner_page_Alpha) {
            try {
                int Dinner_Start, Dinner_End;
                // Lunch_Start = Lunch_page.indexOf("menuName");

                Dinner_Start = Dinner_page_Alpha.indexOf("석식");
                Dinner_End = Dinner_page_Alpha.indexOf("<!--중앙 컨텐츠 영역 끝-->");
                Log.e("DS", "" + Dinner_Start);
                Log.e("DE", "" + Dinner_End);

                Dinner_page_Alpha = Dinner_page_Alpha.substring(Dinner_Start, Dinner_End);
                Dinner_End = Dinner_page_Alpha.indexOf("에너지");
                Dinner_page_Alpha = Dinner_page_Alpha.substring(0, Dinner_End);

                Dinner_page_Alpha = Dinner_page_Alpha.replaceAll("[^가-힝 <br <]", "");
                Dinner_page_Alpha = Dinner_page_Alpha.replaceAll("[<br]", "\n");
                Dinner_page_Alpha = Dinner_page_Alpha.replaceAll("석식", "");
                Dinner_page_Alpha = Dinner_page_Alpha.replaceAll("\n", " ");


                Dinner_page_Alpha = Dinner_page_Alpha.trim();

                Log.e("text", Dinner_page_Alpha);

                // Dinner_Text.setText(Dinner_page_Alpha);

            } catch (Exception e) {
                Dinner_page_Alpha = "급식정보가 없습니다.";
                // Dinner_Text.setText("급식정보가 없습니다.");// 에러이벤트와 함께 들어오는 메세지 출력.
            }
            return Dinner_page_Alpha;
        }
    }
}
