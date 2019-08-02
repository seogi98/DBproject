package samstnet.com.kaz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import samstnet.com.kaz.eventbus.BusProvider;
import samstnet.com.kaz.eventbus.WeatherEvent;

public class DailyWeather extends Fragment {
    WeatherEvent weatherinfo = null;
    ArrayList<String> wtstate = new ArrayList<>();
    ArrayList<String> wtstatePresent = new ArrayList<>();
    ArrayList<String> tempor = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();
    ArrayList<String> timeStr = new ArrayList<>();
    //int img[]=new int[15];
    ArrayList<Integer> img=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    SingerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.dailyweather,container,false);
        ListView listView=rootView.findViewById(R.id.list);
        adapter=new SingerAdapter();

        if( ((MainActivity)getActivity()).getWeatherInfo() != null){
            weatherinfo = new WeatherEvent(((MainActivity)getActivity()).getWeatherInfo());
            wtstate.addAll(weatherinfo.getWstate());
            tempor.addAll(weatherinfo.getTempor());
            time.addAll(weatherinfo.getTime());


            for(int i=0;i<wtstate.size();i++){
                Log.d("i size",String.valueOf(i));
                if(wtstate.get(i)=="sun"){
                    //img[i]=R.drawable.sunny;
                    img.add(R.drawable.sunny);
                }
                else if(wtstate.get(i)=="fewcloud"){
                    //img[i]=R.drawable.cloudy;
                    img.add(R.drawable.cloudy);
                }
                else if(wtstate.get(i)=="manycloud"){
                    //img[i]=R.drawable.cloudy2;
                    img.add(R.drawable.cloudy2);
                }
                else if(wtstate.get(i)=="rain"){
                    //img[i]=R.drawable.rainy;
                    img.add(R.drawable.rainy);
                }
                else if(wtstate.get(i)=="snow"){
                    // img[i]=R.drawable.snowy;
                    img.add(R.drawable.snowy);
                }
                else{
                    // img[i]=R.drawable.xkon;
                }
            }
            //시간 스트링으로변경
            for(int i=0;i<time.size();i++){
                if(time.get(i)<10){
                    timeStr.add("0"+time.get(i));
                }else{
                    timeStr.add(time.get(i)+"");
                }
            }
            //날씨구문변경
            for(int i=0;i<wtstate.size();i++){
                if(MainActivity.wtstate.size()==0){

                }
                else if(wtstate.get(i).equals("manycloud")){
                    //AlarmTitle="흐림";
                    wtstatePresent.add("날씨가 많이 흐려요!");
                }
                else if(wtstate.get(i).equals("fewcloud")){
                    //AlarmTitle="구름";
                    wtstatePresent.add("구름이 많아요!");
                }
                else if(wtstate.get(i).equals("sun")){
                    //AlarmTitle="태양";
                    wtstatePresent.add("맑은날 이에요!");
                }
                else if(wtstate.get(i).equals("rain")){
                    //AlarmTitle="비";
                    wtstatePresent.add("비가와요!");
                }
                else if(wtstate.get(i).equals("snow")){
                    //AlarmTitle="눈";
                    wtstatePresent.add("산타다!");
                }
                else {
                    wtstatePresent.add("저는 집에 갈 수 없어요ㅜㅜ");
                }
            }
            adapter.addItem(new WeatherItem(timeStr.get(0),tempor.get(0),wtstatePresent.get(0),img.get(0)));
            adapter.addItem(new WeatherItem(timeStr.get(1),tempor.get(1),wtstatePresent.get(1),img.get(1)));
            adapter.addItem(new WeatherItem(timeStr.get(2),tempor.get(2),wtstatePresent.get(2),img.get(2)));
            adapter.addItem(new WeatherItem(timeStr.get(3),tempor.get(3),wtstatePresent.get(3),img.get(3)));
            adapter.addItem(new WeatherItem(timeStr.get(4),tempor.get(4),wtstatePresent.get(4),img.get(4)));
            adapter.addItem(new WeatherItem(timeStr.get(5),tempor.get(5),wtstatePresent.get(5),img.get(5)));
            adapter.addItem(new WeatherItem(timeStr.get(6),tempor.get(6),wtstatePresent.get(6),img.get(6)));
            adapter.addItem(new WeatherItem(timeStr.get(7),tempor.get(7),wtstatePresent.get(7),img.get(7)));
            adapter.addItem(new WeatherItem(timeStr.get(8),tempor.get(8),wtstatePresent.get(8),img.get(8)));
            adapter.addItem(new WeatherItem(timeStr.get(9),tempor.get(9),wtstatePresent.get(9),img.get(9)));
        }

        listView.setAdapter(adapter);
        return rootView;
    }
    class SingerAdapter extends BaseAdapter {
        ArrayList<WeatherItem> items=new ArrayList<WeatherItem>();
        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(WeatherItem item){
            items.add(item);
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WeatherItemView view=new WeatherItemView(getActivity().getApplicationContext());

            WeatherItem item=items.get(position);
            view.setTime(item.getTime());
            view.setTempor(item.getTempor());
            view.setWtstate(item.getWtstate());
            view.setImage(item.getResId());
            return view;
        }
    }
    @Subscribe
    public void FinishLoad(WeatherEvent mWeatherEvent) {
        weatherinfo = new WeatherEvent(mWeatherEvent);
        wtstate.addAll(mWeatherEvent.getWstate());
        tempor.addAll(mWeatherEvent.getTempor());
        time.addAll(mWeatherEvent.getTime());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


}
