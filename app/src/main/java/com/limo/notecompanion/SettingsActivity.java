package com.limo.notecompanion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.limo.notecompanion.util.Settings;

public class SettingsActivity extends AppCompatActivity {
    public static SwitchMaterial desktopMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner languageSpinner = findViewById(R.id.language_selector);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter1);

        Spinner appThemeSpinner = findViewById(R.id.theme_selector);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.app_themes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appThemeSpinner.setAdapter(adapter2);

        Spinner outputColorSpinner = findViewById(R.id.output_color_selector);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.output_color, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputColorSpinner.setAdapter(adapter3);

        desktopMode = findViewById(R.id.desktopModeSwitch);
        desktopMode.setChecked(Settings.desktopMode);
    }
}