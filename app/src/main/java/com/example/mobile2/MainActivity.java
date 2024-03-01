package com.example.mobile2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Camera;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.mobile2.R;

public class MainActivity extends AppCompatActivity {
    Camera mCamera; // Объект камеры
    static final String STATE_SCORE = "playerScore"; // Ключ для сохранения текущего счёта
    static final String STATE_LEVEL = "playerLevel"; // Ключ для сохранения текущего уровня
    int mCurrentScore, mCurrentLevel; // Текущий счёт и уровень

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            // Восстановление текущего счёта и уровня из сохранённого состояния
            mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
            mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
        } else {
            // Инициализация начальных значений счёта и уровня
        }
    }

    private void setUpActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActionBar actionBar = getActionBar();
            // Отображение кнопки "Назад" в ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Остановка трассировки метода
        android.os.Debug.stopMethodTracing();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null) {
            // Освобождение ресурсов камеры
            // mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCamera == null) {
            // Инициализация камеры
            initializeCamera();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        ContentValues values = new ContentValues();
        // values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
        // values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());

        // Обновление ContentResolver
        // сохроняет черновик в записи в постоянное хранилище
        getContentResolver().update(
                null, // mUri,
                values,
                null,
                null
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Проверка доступности GPS
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            // Запуск настроек GPS, если оно выключено
            // Intent с действием android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Сохранение текущего счёта и уровня в Bundle
        savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Восстановление текущего счёта и уровня из Bundle
        mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        mCurrentLevel = savedInstanceState.getInt(STATE_LEVEL);
    }

    private void initializeCamera()
