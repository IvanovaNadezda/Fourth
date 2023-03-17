package com.example.fourth;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FragmentScreen2 extends Fragment {
    ListView listView;
    Random random;
    private static final HashMap<String, Integer> IMAGE_RESOURCE_MAP = new HashMap<>();
    static {
        //сами книги
        IMAGE_RESOURCE_MAP.put("owl1.png", R.drawable.owl1);
        IMAGE_RESOURCE_MAP.put("owl2.png", R.drawable.owl2);
        IMAGE_RESOURCE_MAP.put("owl3.png", R.drawable.owl3);
        IMAGE_RESOURCE_MAP.put("owl4.png", R.drawable.owl4);
        IMAGE_RESOURCE_MAP.put("owl5.png", R.drawable.owl5);
        IMAGE_RESOURCE_MAP.put("owl6.png", R.drawable.owl6);
    }

    public FragmentScreen2()
    {
        super(R.layout.screen2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.listview);
        // Обработчик нажатия на элемент ListView
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // Получение выбранного элемента
            Item item = (Item) parent.getItemAtPosition(position);
            Toast.makeText(getContext(), "Нажатие на: " + item.getText(), Toast.LENGTH_SHORT).show();
            Log.d("FragmentScreenTwo", "Нажатие на: " + item.getText());
        });
        // Массив для названия сов
        String [] detectiveBooks ;
        try {
            detectiveBooks = getBooksFromFile(getContext()).toArray(new String[getBooksFromFile(getContext()).size()]);
            // Метод считывания строк построчно из файла
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Cлучайный выбор картинки совы
        random = new Random();
        ArrayList<Item> items = new ArrayList<>();
        String[] imageNames = {"owl1.png", "owl2.png", "owl3.png", "owl4.png", "owl5.png", "owl6.png"};

        // Цикл по всем 200 эл-там ListView
        for (int i = 0; i < 200; i++) {
            // Рандомное задание картинки
            int randomIndex = random.nextInt(imageNames.length);
            String imageName = imageNames[randomIndex];
            int imageResourceId = IMAGE_RESOURCE_MAP.get(imageName);
            // Установка картинки и вида совы
            Item item = new Item(imageResourceId, detectiveBooks[i]);
            items.add(item);
        }
        // Создание адаптера
        Adapter adapter = new Adapter(getContext(), R.layout.list_view, items);
        // Установка адаптера
        listView.setAdapter(adapter);

    }
    // Читаем корма построчно из файла
    public ArrayList<String> getBooksFromFile(Context context) throws IOException
    {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String line;
            AssetManager assetManager = context.getAssets();
            InputStreamReader istream = new InputStreamReader(assetManager.open("owls.txt"));
            BufferedReader in = new BufferedReader(istream);
            while ((line = in.readLine()) != null){
                arrayList.add(line);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;

    }
}

