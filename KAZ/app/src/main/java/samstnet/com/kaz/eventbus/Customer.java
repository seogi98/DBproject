package samstnet.com.kaz.eventbus;

import android.app.Application;

import samstnet.com.kaz.R;

public class Customer extends Application {
    public static Item_type item[] = new Item_type[50];

    private int money;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Item_type[] getItem() {
        return item;
    }

    public void setItem(Item_type[] item) {
        this.item = item;
    }

    public void onCreate() {
        super.onCreate();
        money=10000;
        position=0;
        item[0]= new Item_type("우산","비를 피할 수 있는 우산을 씌워 줍니다",R.drawable.img1,10,false,false);
        item[1]= new Item_type("우비","비를 피할 수 있는 우비를 씌워 줍니다",R.drawable.img2,20,false,false);
        item[2]= new Item_type("선풍기","더위를 피할 수 있게 선풍기를 틉니다",R.drawable.img3,50,false,false);
        item[3]= new Item_type("난로","추위를 피할 수 있게 난로를 틉니다",R.drawable.img4,60,false,false);
        item[4]= new Item_type("마스크","미세먼지를 막을 수 있게 마스크를 씌워줍니다",R.drawable.img5,5,false,false);
        item[5]= new Item_type("공기청정기","미세먼지를 막을 수 있게 공기 청정기를 틀어줍니다",R.drawable.img6,90,false,false);
        item[6]= new Item_type("에어컨","더위를 피할 수 있게 에어컨을 틉니다",R.drawable.img6,150,false,false);

}
    private static Customer instance = null;


    public static synchronized Customer getInstance(){

        if(null == instance){

            instance = new Customer();

        }

        return instance;

    }


    @Override
    public String toString() {
        return "Customer{" +
                "item=" + item +
                '}';
    }

}