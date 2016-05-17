package cn.demonk.dynamic;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_create:
            CreateClass cc = new CreateClass(this.getApplication());
                        cc.create(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"dynamic");
//            cc.create(MainActivity.this.getFilesDir() + File.separator + "dynamic");

            break;

        case R.id.btn_load:
            break;
        }

    }
}
