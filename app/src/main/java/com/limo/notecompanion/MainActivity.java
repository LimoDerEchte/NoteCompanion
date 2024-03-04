package com.limo.notecompanion;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import maximsblog.blogspot.com.jlatexmath.cache.JLaTeXMathCache;
import maximsblog.blogspot.com.jlatexmath.core.AjLatexMath;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AjLatexMath.init(this);
        JLaTeXMathCache.setMaxCachedObjects(0);

        findViewById(R.id.latex_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, LatexEditor.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.chemics_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, ReactionEditor.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
    }
}