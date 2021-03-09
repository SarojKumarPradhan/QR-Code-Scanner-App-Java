package com.example.qrcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


//implementing onclicklistener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //View Objects
    Button buttonScan;
    Button button2;
    TextView textViewName;
    TextView textViewAddress;
    TextView textViewData;
    TextView textViewTest;

    //qr code scanner object
    IntentIntegrator qrScan;

    //SharedPreferences code for access internal storage
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String NameID = "nameID";

    public static String qrData ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This code for screen Portrait mode only.....................
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);
        button2 = (Button) findViewById(R.id.button2);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewData = (TextView)findViewById(R.id.textViewdata);
        textViewTest = (TextView)findViewById(R.id.textViewTest);
//********************************************************************
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String nameIdget = sharedPreferences.getString(NameID,null);
        textViewTest.setText(nameIdget);
        if (nameIdget != null)
        {
            Intent intent = new Intent(MainActivity.this,HomePage.class);
//                intent.putExtra("qrdata",nameIdget);
                startActivity(intent);
        }

//************************************************************************
        //initializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);


//        button2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,HomePage.class);
//                intent.putExtra("qrdata",nameIdget);
//                startActivity(intent);
//            }
//        });
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result != null){
            //if qrcode has nothing in it
            if (result.getContents() == null){
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_SHORT).show();
            }
            else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textViews for Specific
                    textViewName.setText(obj.getString("name"));
                    //setting values to textViews for All String
                    textViewAddress.setText(String.valueOf(result.getContents()));

                    String str1 = textViewAddress.getText().toString();
                    textViewTest.setText(String.valueOf(str1));


                }catch (JSONException e){
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();


                    //in this case you can display whatever data is available on the qrcode.....................
                    textViewData.setText(String.valueOf(result.getContents()));

                    // This code is for get Secret data form qrcode random Data ..............................
                    String str = result.getContents();
                    //String qrData = "";
                    qrData = "";
                    for (int i = str.indexOf("$")+1;i<str.indexOf("%");i++)
                    {
                        qrData += str.charAt(i);
                    }
                    //Print particular data of the random strings.....

                    if (qrData.isEmpty()){
                        return;
                    }else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(NameID,qrData);
                        editor.putString("displayUserID",qrData);
                        editor.apply();
                        textViewTest.setText(String.valueOf(qrData));
                        Intent intent = new Intent(getApplicationContext(),HomePage.class);
                        startActivity(intent);
                    }


                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {
        qrScan.setCameraId(0);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setPrompt("Scan a QR Code");
        qrScan.setOrientationLocked(false);
        //initiating the qr code scan
        qrScan.initiateScan();

    }

}

//***************************************
//Bar Code Text = {"name":"Saroj Kumar Pradhan","address":"India"}
//***************************************