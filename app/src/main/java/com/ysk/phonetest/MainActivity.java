package com.ysk.phonetest;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  EditText editText;
    private TextView textView1,textView2,textView3,textView4,textView5,textView6;
    private Button button;
    private String meditText;
    private PhoneBean  resultBean;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //handler具体实现方法
            if(0 == msg.what){//如果标志号对的上
                resultBean = (PhoneBean) msg.obj;//那就是我要的信息
                if(null != resultBean){
                    Log.e("=====","===="+resultBean);
                    String City = resultBean.getResult().getCity();
                    String CityCode = resultBean.getResult().getCityCode();
                    String mobileNumber = resultBean.getResult().getMobileNumber();
                    String operater=resultBean.getResult().getOperator();
                    String province=resultBean.getResult().getProvince();
                    String zigCode=resultBean.getResult().getZipCode();
                    textView1.setText("城市："+City);
                    textView2.setText("区号："+CityCode);
                    textView3.setText("号码头："+mobileNumber);
                    textView4.setText("卡属性："+operater);
                    textView5.setText("省："+province);
                    textView6.setText("邮政编码："+zigCode);

                }
            }else if(1 == msg.what){
                int num = (int)msg.obj;
                Log.e("message2中的obj",num+"");

            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.edit);
        textView1=(TextView)findViewById(R.id.text1);
        textView2=(TextView)findViewById(R.id.text2);
        textView3=(TextView)findViewById(R.id.text3);
        textView4=(TextView)findViewById(R.id.text4);
        textView5=(TextView)findViewById(R.id.text5);
        textView6=(TextView)findViewById(R.id.text6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meditText=editText.getText().toString().trim();

                //发送消息去服务端,并返回数据回来。这里是在线程中获取了数据，
                // 线程不能直接更新主界面，因此得借用handler来进行主界面得更新
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        resultBean = HttpUtil.sendPhone(meditText);
                        Log.e("11resultBean",resultBean.getResult().getCity().toString());

                        //使用handler发送消息，通知主界面进行控件的赋值（界面的更新）
                        Message message = new Message(); //消息实体类
                        message.what = 0; //实体标志设为0
                        message.obj = resultBean; //实体内容，整个resultBean打包
                        handler.sendMessage(message); //发送消息方法

                       /* Message message2 = new Message(); //消息实体类
                        message.what = 1; //实体标志
                        message.obj = 1; //实体内容
                        handler.sendMessage(message2); //发送消息方法*/
                    }
                }).start();
               // PhoneBean.ResultBean getCity=new PhoneBean.ResultBean();//新建的对象是没有值的
              /*  if(null != resultBean){
                    Log.e("=====","====");
                    String City = resultBean.getResult().getCity();
                    textView.setText(City);
                }*/

            }
        });
    }
}
