package com.limo.notecompanion;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.switchmaterial.SwitchMaterial;
import maximsblog.blogspot.com.jlatexmath.cache.JLaTeXMathCache;
import maximsblog.blogspot.com.jlatexmath.core.AjLatexMath;

public class MainActivity extends AppCompatActivity {
    public static SwitchMaterial desktopMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AjLatexMath.init(this);
        JLaTeXMathCache.setMaxCachedObjects(0);

        desktopMode = findViewById(R.id.desktopModeSwitch);
        desktopMode.setChecked(isDesktopMode(this));

        findViewById(R.id.latex_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, LatexEditor.class);
            if(desktopMode.isChecked())
                intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.chemics_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, ReactionEditor.class);
            if(desktopMode.isChecked())
                intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.icon).setClipToOutline(true);
    }

    private boolean isDesktopMode(Context context){
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_DESK);
    }
}