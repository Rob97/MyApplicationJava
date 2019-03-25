//package com.example.sohrabseera.myapplicationjava;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import com.microsoft.windowsazure.mobileservices.*;
//
//
//public class DietaryRestrictionsSelection extends AppCompatActivity {
//
//    private MobileServiceClient mClient;
//
//    public boolean glutenCB = false;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dietary_restrictions_selection);
//        try {
//            mClient = new MobileServiceClient(
//                    "https://allergychecker.azurewebsites.net",
//                    this
//            );
//        }catch {
//
//        }
//
//    }
//
//
//    TodoItem item = new TodoItem();
//    item.Text = "Awesome item";
//mClient.getTable(TodoItem.class).insert(item, new TableOperationCallback<item>() {
//        public void onCompleted(TodoItem entity, Exception exception, ServiceFilterResponse response) {
//            if (exception == null) {
//                // Insert succeeded
//            } else {
//                // Insert failed
//            }
//        }
//    });
//
//    public void onClick(View v){
//        if(v.getId()==R.id.glutenCB){
//                glutenCB = true;
//
//        }
//    }
//
//
//}


