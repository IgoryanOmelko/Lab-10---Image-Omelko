package com.example.lab09_imageomelko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Bitmap bmp1;
    Bitmap bmp2;
    int width, height;
    ImageView imgv1;
    ImageView imgv2;
    EditText etFile;
    SeekBar sB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenFile();
    }

    public void btnOpenFile(View v) {
        OpenFile();
    }

    public void OpenFile() {
        AssetManager asset = getAssets();
        InputStream stream = null;
        etFile = findViewById(R.id.etFileName);
        try {
            stream = asset.open(etFile.getText().toString());
        } catch (IOException e) {
            DialogWindow("Name of file is wrong");
            return;
        }
        bmp1 = BitmapFactory.decodeStream(stream);
        try {
            stream.close();
        } catch (IOException e) {
            DialogWindow("Error of closing the stream");
        }
        width = bmp1.getWidth();
        height = bmp1.getHeight();
        bmp2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        imgv1 = findViewById(R.id.imgv1);
        imgv2 = findViewById(R.id.imgv2);
        imgv1.setImageBitmap(bmp1);
        imgv2.setImageBitmap(bmp2);
        sB = findViewById(R.id.seekBar);
    }

    public void DialogWindow(String mes) { //created by Igor Omelko
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage(mes).setNegativeButton(android.R.string.no, null)
                .create()
                .show();
    }

    public void filterCopy(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c = bmp1.getPixel(x, y);
                bmp2.setPixel(x, y, c);
            }
        }
    }

    public void filterInvert(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                int r = 255 - Color.red(c0);
                int g = 255 - Color.green(c0);
                int b = 255 - Color.blue(c0);
                int c1 = Color.argb(255, r, g, b);
                bmp2.setPixel(x, y, c1);
            }
        }
    }

    public void filterGray(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                double r = Color.red(c0) * 0.333;
                double g = Color.red(c0) * 0.333;
                double b = Color.red(c0) * 0.333;
                double gray = r + g + b;
                int c1 = Color.argb(255, (int) gray, (int) gray, (int) gray);
                bmp2.setPixel(x, y, c1);
            }
        }
    }

    public void filterBlackAndWhite(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                double r = Color.red(c0) * 0.333;
                double g = Color.red(c0) * 0.333;
                double b = Color.red(c0) * 0.333;
                double gray = r + g + b;
                int c = 0;
                if (gray <= 127) {
                    c = Color.argb(255, 0, 0, 0);
                } else {
                    c = Color.argb(255, 255, 255, 255);
                }
                bmp2.setPixel(x, y, c);
            }
        }
    }

    public void filterLightening(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                int r = 0, g = 0, b = 0;
                r = sB.getProgress() + Color.red(c0);
                if (r > 255) r = 255;
                g = sB.getProgress() + Color.green(c0);
                if (g > 255) g = 255;
                b = sB.getProgress() + Color.blue(c0);
                if (b > 255) b = 255;
                int c = Color.argb(255, (int) r, (int) g, (int) b);
                bmp2.setPixel(x, y, c);
            }
        }
    }

    public void reverseX(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 1; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                int x2 = width - x;
                int c = Color.argb(255, Color.red(c0), Color.green(c0), Color.blue(c0));
                bmp2.setWidth(width);
                bmp2.setHeight(height);
                bmp2.setPixel(x2, y, c);
            }
        }
    }

    public void reverseY(View v) {
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int c0 = bmp1.getPixel(x, y);
                int y2 = height - y;
                int c = Color.argb(255, Color.red(c0), Color.green(c0), Color.blue(c0));
                bmp2.setWidth(width);
                bmp2.setHeight(height);
                bmp2.setPixel(x, y2, c);
            }
        }
    }

    public void filterContrast(View v) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c0 = bmp1.getPixel(x, y);
                int r = 0, g = 0, b = 0;
                r = Color.red(c0) - 25;
                if (r < sB.getProgress()) r = 0;
                if (r > sB.getProgress()) r += 10;
                g = Color.green(c0) - 25;
                if (g < sB.getProgress()) g = 0;
                if (g > sB.getProgress()) g += 10;
                b = Color.blue(c0) - 25;
                if (b < sB.getProgress()) b = 0;
                if (b > sB.getProgress()) b += 10;
                int c = Color.argb(255, (int) r, (int) g, (int) b);
                bmp2.setPixel(x, y, c);
            }
        }
    }


}