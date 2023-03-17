package com.example.fourth;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FragmentScreen3 extends Fragment {

    private static final HashMap<String, Integer> IMAGE_RESOURCE_MAP = new HashMap<>();

    // Хранилище картинок корма для сов
    static {
        IMAGE_RESOURCE_MAP.put("mouse.png", R.drawable.mouse);
        IMAGE_RESOURCE_MAP.put("beatle.png", R.drawable.beatle);
    }

    private RecyclerView recyclerView;
    private Adapter2 adapter;
    private ArrayList<Item> items;
    private Random random;

    public FragmentScreen3() {
        super(R.layout.screen3);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            // Определяем слушателя касания элемента в RecyclerView
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent e) {
                // Проверяем, что был сделан щелчок по элементу списка
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
                    // Определяем позицию выбранного элемента в списке
                    int position = recyclerView.getChildAdapterPosition(child);
                    // Получаем объект Item по позиции
                    Item item = items.get(position);
                    // Показываем Toast сообщение с названием книги
                    Toast.makeText(getContext(), "Нажали на: " + item.getText(), Toast.LENGTH_SHORT).show();
                    // Выводим сообщение в Logcat
                    Log.d("FragmentScreenThree", "Нажали на: " + item.getText());
                    // Возвращаем true, чтобы сообщить, что касание обработано
                    return true;
                }
                // Возвращаем false, чтобы сообщить, что касание не было обработано
                return false;
            }
            //Методы onTouchEvent и onRequestDisallowInterceptTouchEvent добавлены для реализации
            // интерфейса RecyclerView.OnItemTouchListener.
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        // Массив для картинок
        items = new ArrayList<>();
        // Случано выбираем картинку
        random = new Random();

        String[] fodders;
        try {
            fodders = getFodders(getContext()).toArray(new String[getFodders(getContext()).size()]);
            // Построчное ситывание строк из файла
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] imageNames = {"mouse.png", "beatle.png"};

        // В цикле устанавливаем картинку случайно и вид корма к ней
        for (int i = 0; i < 200; i++) {
            // Рандомное задание картинки
            int randomIndex = random.nextInt(imageNames.length);
            String imageName = imageNames[randomIndex];
            int imageResourceId = IMAGE_RESOURCE_MAP.get(imageName);
            // Установка картинки и корма
            Item item = new Item(imageResourceId, fodders[i]);
            items.add(item);
        }

        // Создание адаптера
        adapter = new Adapter2(items);
        // Установка адаптера
        recyclerView.setAdapter(adapter);


    }

    // Построчное считывание строк из файла
    public ArrayList<String> getFodders(Context context) throws IOException
    {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String line;
            AssetManager assetManager = context.getAssets();
            InputStreamReader istream = new InputStreamReader(assetManager.open("fodders.txt"));
            BufferedReader bufferedReader = new BufferedReader(istream);
            while ((line = bufferedReader.readLine()) != null){
                arrayList.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;

    }
}