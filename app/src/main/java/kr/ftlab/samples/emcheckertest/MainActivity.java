package kr.ftlab.samples.emcheckertest;

import kr.ftlab.lib.SmartSensor;
import kr.ftlab.lib.SmartSensorEventListener;
import kr.ftlab.lib.SmartSensorResultEM;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SmartSensorEventListener {
    private SmartSensor mMI;
    private SmartSensorResultEM mResultEM;

    private Button btnStart;
    private TextView txtResult;

    int mProcess_Status = 0;
    int Process_Stop = 0;
    int Process_Start = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // 앱이 전면에서 실행되는 동안 화면이 꺼지지 않도록 해줍니다.

        btnStart = (Button) findViewById(R.id.button_on); // 레이아웃에 정의했던 버튼을 객체로서 참조할 수 있도록 참조변수에 저장합니다.
        txtResult = (TextView) findViewById(R.id.textresult); // 레이아웃에 정의했던 텍스트뷰를 객체로서 참조할 수 있도록 참조변수에 저장합니다.

        mMI = new SmartSensor(MainActivity.this, this);
        mMI.selectDevice(SmartSensor.EM);
    }

    public void mOnClick(View v) {
        if (mProcess_Status == Process_Start) {
            stopSensing();
        }
        else {
            startSensing();
        }
    }

    public void startSensing() {
        btnStart.setText("STOP");
        mProcess_Status = Process_Start;
        mMI.start();
    }

    public void stopSensing() {
        btnStart.setText("START");
        mProcess_Status = Process_Stop;
        mMI.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mMI.registerSelfConfiguration();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mMI.quit();
        finish();
        System.exit(0);
        super.onDestroy();
    }

    @Override
    public void onMeasured() {
        String str ="";
        mResultEM = mMI.getResultEM();

        str = String.format("%1.0f", mResultEM.EM_Value);
        txtResult.setText(str);
    }

    @Override
    public void onSelfConfigurated() {
        mProcess_Status = 0;
        btnStart.setText("START");
    }
}

