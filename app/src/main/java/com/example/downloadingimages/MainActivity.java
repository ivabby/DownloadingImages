package com.example.downloadingimages;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    public void downloadImage(View view)
    {
        //https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png
        String url = "https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png";

        try {
            Bitmap image = (new ImageDownloader()).execute(url).get();
            imageView.setImageBitmap(image);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class ImageDownloader extends AsyncTask<String , Void , Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            Log.d("URL" , "URL is: "+urls[0]);
            URL url;
            HttpURLConnection connection;

            try{
                url = new URL(urls[0]);

                connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
    }
}
