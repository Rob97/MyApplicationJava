package com.example.sohrabseera.myapplicationjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    private Button nameBtn;
    private TextView formatTxt, contentTxt;
    private String scanContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameBtn = (Button)findViewById(R.id.name_button);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);
        nameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId()==R.id.name_button) {
                try {
                    Log.e("Entered Click", "Entered Click");
                    getItemName();
                } catch (Exception e) {
                    System.out.println("Exception occurred on click");
                }
            }


    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


public void getItemName()throws IOException {
    try {
        Log.e("Entered Scan", "Entered Scan");
        //Debugging
        URL url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc=674785680773");
      //  URL url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc=" + scanContent);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.e("Test", "Test");
        try {
            Log.e("Test1", "Test1");
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                Log.e("Returned Scan", "Returned Scan");
                readStream(in);
            }catch(Exception e){
                Log.e("failed", "failed");
            }
        } finally {
            urlConnection.disconnect();
        }
    }catch(Exception e) {
        System.out.println("Exception occurred");
    }
}
    private String readStream(InputStream is) {
        try {
            Log.e("Entered output", "Entered Output");
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            Log.e("Entered Error on Output", "Entered Error on Output");
            return "Errorrrr";
        }
    }





//    public class MainClass
//    {
//        public static void main(String[] args)
//        {
//            String user_key = "only_for_dev_or_pro";
//
//            RestClient client = new RestClient("https://api.upcitemdb.com/prod/v1/");
//            // lookup request with GET
//            RestRequest request = new RestRequest("lookup", Method.GET);
//            request.AddHeader("key_type", "3scale");
//            request.AddHeader("user_key", user_key);
//
//            request.AddQueryParameter("upc", "674785680773");
//            IRestResponse response = client.Execute(request);
//            System.out.println("response: " + response.Content);
//            // parsing json
////C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java unless the Java 10 inferred typing option is selected:
//            var obj = JsonConvert.DeserializeObject(response.Content);
//            System.out.println("offset: " + obj["offset"]);
//        }
//    }


}
/*
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Button.OnClickListener mScan = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}
*/