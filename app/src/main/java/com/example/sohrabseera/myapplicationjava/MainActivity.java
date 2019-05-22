package com.example.sohrabseera.myapplicationjava;




import com.microsoft.windowsazure.mobileservices.*;
//import com.microsoft.windowsazure.mobileservices.table;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    private Button nameBtn;
    private TextView formatTxt, contentTxt;
    private String scanContent;
    private MobileServiceClient mClient;

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
        try {
            mClient = new MobileServiceClient(
                    "https://allergychecker.azurewebsites.net",
                    this
            );
           final TodoItem item = new TodoItem();
            item.Text = "Awesome item";
            mClient.getTable("baby food ingredients").top(1);
           // JSONObject json1 = new JSONObject());
          // Log.e("hello", .toString()) ;
//            mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<item>() {
//                public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
//                    if (exception == null) {
//                        // Insert succeeded
//                    } else {
//                        // Insert failed
//                    }
//                }
//            });
        }catch(Exception e){
            System.out.print(e.toString());
        }
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

    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                //Your code goes here
                URL url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc=674785680773");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                Log.e("Returned Scan", "Returned Scan");
                JSONObject json = new JSONObject(readStream(in));

                List<String> list = new ArrayList<String>();
                JSONArray array = json.getJSONArray("items");
                for(int i = 0 ; i < array.length() ; i++){
                    list.add(array.getJSONObject(i).getString("title"));
                }


                Log.e("JSON:", list.get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });



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
                thread.start();
            }catch(Exception e){
                Log.e("failed", e.toString());
            }
        } finally {
            urlConnection.disconnect();
        }
    }catch(Exception e) {

        Log.e("ERROR", "Exception occurred:" + e.toString());
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
            Log.e("Exited output", "Exited Output");
            return bo.toString();
        } catch (IOException e) {
            Log.e("Entered Error on Output", "Entered Error on Output: " + e.toString());
            return "Errorrrr";
        }

//        private void SqlQuery(){
//            client = new AsyncDocumentClient.Builder()
//                    .withServiceEndpoint(YOUR_COSMOS_DB_ENDPOINT)
//                    .withMasterKeyOrResourceToken(YOUR_COSMOS_DB_MASTER_KEY)
//                    .withConnectionPolicy(ConnectionPolicy.GetDefault())
//                    .withConsistencyLevel(ConsistencyLevel.Eventual)
//                    .build();
//        }


//        InputStream inputStream = connection.getInputStream();
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder builder = new StringBuilder();
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            builder.append(line + "\n");
//        }
//
//        try {
//            JSONArray jsonArray = new JSONArray(builder.toString());
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject json = jsonArray.getJSONObject(i);
//
//                if (!json.get("object").equals(null)) {
//                    JSONArray objectJsonArray = json.getJSONArray("object");
//                    for (int i = 0; i < objectJsonArray.length(); i++) {
//                        JSONObject json = objectJsonArray.getJSONObject(i);
//                    }
//                }
//            }
//        }catch(Exception e){
//
//        }

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