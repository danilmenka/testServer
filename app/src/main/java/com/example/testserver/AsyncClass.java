package com.example.testserver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AsyncClass extends AsyncTask<String,String,String> {
    private ProgressDialog dialog;
    String model;
    String time;
    String lang;
    Context context;
    String answerHTTP;
    private List nameValuePairs;

    AsyncClass(Context context){
        this.context = context;
    }




    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        model = getDeviceName();
        String arr[] = model.split(" ", 2);
        model = arr[0];   //the
        lang = Locale.getDefault().getLanguage();
      //  DateFormat df = new SimpleDateFormat("d m yyyy, HH:mm");
        DateFormat df = new SimpleDateFormat("yyyy-MM-d HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        Log.e("NN",date);

        List nameValuePairs = new ArrayList(3);
        nameValuePairs.add(new BasicNameValuePair("time",time));
        nameValuePairs.add(new BasicNameValuePair("language", lang));
        nameValuePairs.add(new BasicNameValuePair("model", model));


        answerHTTP = getStringPOST(nameValuePairs);

        Log.e("ANSWET",answerHTTP);

        return null;
    }
    protected void onPostExecute(String result) {


        dialog.dismiss();

    }
    public String getStringPOST( List name) {
        String answer = "ErrorPOST void ";
        try {
            HttpClient httpclient = new MyHttpClient(context);
            HttpPost htopost = new HttpPost("https://u0881449.cp.regruhosting.ru/api.php");
        /*    if(request.hashCode()!="user".hashCode())
                htopost.setHeader(new BasicHeader("X-AUTH-TOKEN",tokenString));
*/
            htopost.setEntity(new UrlEncodedFormEntity(name, "UTF-8"));
            HttpResponse response;
            response = httpclient.execute(htopost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                Log.e("ZZZ",result);
                answer = result;
                instream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}
