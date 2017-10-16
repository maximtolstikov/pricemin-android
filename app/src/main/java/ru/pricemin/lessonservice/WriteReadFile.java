package ru.pricemin.lessonservice;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by name on 09/10/17.
 */

public class WriteReadFile extends AppCompatActivity {


    public WriteReadFile(Context ctx){
        this.context = ctx;
    }

    private Context context;
    final String LOG_TAG = "myLogs";
    final String FILENAME = "tmpListPoints";
    ArrayList<String> list;

    public void writeFile(ArrayList<String> list) {
        try {
            // отрываем поток для записи
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(FILENAME, MODE_PRIVATE)));
            // пишем данные
            for (String i : list) {
                bw.write(i + ";");
            }
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFile() {

        try {
            // открываем поток для чтения
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(FILENAME)));
            String line = "";
            String[] elements;
            // читаем содержимое
            while ((line = br.readLine()) != null) {
                elements = line.split(";");
                list = new  ArrayList<>(Arrays.asList(elements));
                for (String i : list){
                    Log.d(LOG_TAG, i);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
