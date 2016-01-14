package com.asyntask.details;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class DownloadAsyncTask extends AsyncTask<String, Void, Weather> {

	private ProgressDialog progressDialog;
	private Context mCcontext;
	private int mMethod;
	private List<NameValuePair> paramsValues;
	private ResultCallBack resultCallBack;

	// main threaad
	public DownloadAsyncTask(ResultCallBack resultCallBack,
                             MainActivity mainActivity, int method, List<NameValuePair> params) {
		mCcontext = mainActivity;
		mMethod = method;
		this.paramsValues = params;
		 this.resultCallBack= resultCallBack;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(mCcontext);
		progressDialog.setMessage("Loading");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}

	// background thread
	@Override
	protected Weather doInBackground(String... params) {
		// TODO Auto-generated method stub
		// get the response from server
		ServiceHandler service = new ServiceHandler();
		String resopsne = service.makeConnection(params[0], mMethod, paramsValues);
		Weather weather = new Weather();

		try {
			JSONObject jsonObject = new JSONObject(resopsne);
			JSONObject cityObject = jsonObject.getJSONObject("city");
			String name = cityObject.getString("name");
			weather.setName(name);
			
			JSONArray jsonListArray=jsonObject.getJSONArray("list");
			JSONObject mainJsonObject=jsonListArray.getJSONObject(0);
			JSONObject mainChildJsonObject=mainJsonObject.getJSONObject("main");
			String temp=mainChildJsonObject.getString("temp");
			weather.setTemp(temp);

			//To get description
            JSONArray jsonlist = jsonObject.getJSONArray("list");
            JSONObject weatherJsonObject = jsonlist.getJSONObject(0);
            JSONArray weatherList = weatherJsonObject.getJSONArray("weather");
            JSONObject weatherchildObject = weatherList.getJSONObject(0);
            String desc = weatherchildObject.getString("description");
            weather.setDescription(desc);
			
			
			
		} catch (JSONException e) {

		}
		// json parsing

		return weather;
	}

	// main thread
	@Override
	protected void onPostExecute(Weather result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
		resultCallBack.onSuccess(result);

	}

}
