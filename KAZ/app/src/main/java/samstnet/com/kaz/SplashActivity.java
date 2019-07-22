package samstnet.com.kaz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       try {
           Thread.sleep(1000);
           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(intent);
           finish();
       }catch (InterruptedException e){
           e.printStackTrace();
       }

    }
}
