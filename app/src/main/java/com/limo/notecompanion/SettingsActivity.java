package com.limo.notecompanion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.limo.notecompanion.util.Settings;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private static String language;
    private static String theme;
    private static String exportColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Settings.load(this);

        language = language == null ? Settings.language : language;
        theme = theme == null ? Settings.theme : theme;
        exportColor = exportColor == null ? Settings.exportColor : exportColor;
        getResources().getConfiguration().setLocale(new Locale(language));

        Spinner languageSpinner = findViewById(R.id.language_selector);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter1);
        languageSpinner.setSelection(language.equalsIgnoreCase("de") ? 1 : 0);

        Spinner appThemeSpinner = findViewById(R.id.theme_selector);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.app_themes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appThemeSpinner.setAdapter(adapter2);
        appThemeSpinner.setSelection(theme.equalsIgnoreCase("LIGHT") ? 2 :
                                     theme.equalsIgnoreCase("DARK") ? 1 : 0);

        Spinner outputColorSpinner = findViewById(R.id.output_color_selector);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.output_color, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputColorSpinner.setAdapter(adapter3);
        outputColorSpinner.setSelection(exportColor.equalsIgnoreCase("INHERIT") ? 0 :
                                        exportColor.equalsIgnoreCase("BLACK") ? 1 :
                                        exportColor.equalsIgnoreCase("WHITE") ? 2 : 3);

        SwitchMaterial desktopMode = findViewById(R.id.desktopModeSwitch);
        desktopMode.setChecked(Settings.desktopMode);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(id + " <> " + language);
                switch (position) {
                    case 0:
                        if(language.equalsIgnoreCase("en"))
                            return;
                        language = "en";
                        getBaseContext().getResources().getConfiguration().setLocale(new Locale(language));
                        restart();
                        break;
                    case 1:
                        if(language.equalsIgnoreCase("de"))
                            return;
                        language = "de";
                        getBaseContext().getResources().getConfiguration().setLocale(new Locale(language));
                        restart();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
        appThemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        theme = "SYSTEM";
                        break;
                    case 1:
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        theme = "DARK";
                        break;
                    case 2:
                        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        theme = "LIGHT";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });
        outputColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        exportColor = "INHERIT";
                        break;
                    case 1:
                        exportColor = "BLACK";
                        break;
                    case 2:
                        exportColor = "WHITE";
                        break;
                    case 3:
                        exportColor = "CUSTOM";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(0);
            }
        });

        findViewById(R.id.save_button).setOnClickListener((l) -> {
            Settings.language = language;
            Settings.theme = theme;
            Settings.exportColor = exportColor;
            Settings.desktopMode = desktopMode.isChecked();
            Settings.save(this);
        });
    }

    private void restart() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}