    package com.asyntask.details;

    import android.app.Activity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    public class MainActivity extends Activity implements ResultCallBack {
        private String url="http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=0ba6de8511ff60f099e580d3362b4108";
       // private String url="http://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=ae2e42965c987fc67d9b93c63681bcfe%3A6%3A73774230";
       //private String url="http://api.bestbuy.com/v1/products(customerReviewAverage%3E=4&customerReviewCount%3E100)?show=customerReviewAverage,customerReviewCount,name,sku&format=json&apiKey=n54qczmxrsgwknfkkqky9mrc";
        private Button fetch;
        private TextView name;
        private TextView temperature;
        private TextView description;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            fetch=(Button)findViewById(R.id.button1);
            name=(TextView)findViewById(R.id.name);
            temperature=(TextView)findViewById(R.id.temp);
            description = (TextView)findViewById(R.id.desc);
            fetch.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    serverConnection();
                }
            });
        }

        @Override
        public void onSuccess(Weather weather) {

            temperature.setText(weather.getTemp());
            name.setText(weather.getName());
            description.setText(weather.getDescription());
        }

        @Override
        public void onFailure(String error) {
            // TODO Auto-generated method stub
            Log.e("Error","Check again");

        }

        private void serverConnection() {
            DownloadAsyncTask downloadAsyc=new DownloadAsyncTask(this,MainActivity.this,ServiceHandler.GET,null);
            downloadAsyc.execute(url);
        }
    }
