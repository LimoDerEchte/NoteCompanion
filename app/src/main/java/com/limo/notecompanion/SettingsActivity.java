package com.limo.notecompanion;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    private Spinner languageSpinner;
    private Spinner appThemeSpinner;
    private Spinner outputColorSpinner;
    public static SwitchMaterial desktopMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languageSpinner = findViewById(R.id.language_selector);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter1);

        appThemeSpinner = findViewById(R.id.theme_selector);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.app_themes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appThemeSpinner.setAdapter(adapter2);

        outputColorSpinner = findViewById(R.id.output_color_selector);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.output_color, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outputColorSpinner.setAdapter(adapter3);

        desktopMode = findViewById(R.id.desktopModeSwitch);
        desktopMode.setChecked(isDesktopMode(this));
    }

    private boolean isDesktopMode(Context context){
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_DESK);
    }
}