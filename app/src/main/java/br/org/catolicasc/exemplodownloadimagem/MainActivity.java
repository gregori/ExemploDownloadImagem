package br.org.catolicasc.exemplodownloadimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    // Trata o evento do botão "baixar imagem"
    public void downloadImagem(View v) {
        String imgUrl = "https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png";
        ImageDownloader imageDownloader = new ImageDownloader();

        try {
            // baixar a imagem da internet
            Bitmap imagem = imageDownloader.execute(imgUrl).get();
            // atribuir a imagem ao imageView
            imageView.setImageBitmap(imagem);
        } catch (Exception e) {
            Log.e(TAG, "downloadImagem: Impossível baixar imagem"
                + e.getMessage());
        }
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                return bitmap;
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Erro ao baixar imagem"
                        + e.getMessage());
            }

            return null;
        }
    }
}
