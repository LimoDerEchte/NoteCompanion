package com.limo.notecompanion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.limo.notecompanion.util.Settings;
import maximsblog.blogspot.com.jlatexmath.cache.JLaTeXMathCache;
import maximsblog.blogspot.com.jlatexmath.core.AjLatexMath;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Settings.load(this);
        Settings.applyThemeAndLanguage(this, getDelegate());

        AjLatexMath.init(this);
        JLaTeXMathCache.setMaxCachedObjects(0);

        findViewById(R.id.latex_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, LatexEditor.class);
            if(Settings.desktopMode)
                intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.chemics_button).setOnClickListener((e) -> {
            Intent intent = new Intent(this, ReactionEditor.class);
            if(Settings.desktopMode)
                intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.settingsButton).setOnClickListener((e) -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            if(Settings.desktopMode)
                intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.githubButton).setOnClickListener((e) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LimoDerEchte/NoteCompanion"));
            startActivity(browserIntent);
        });
        findViewById(R.id.webButton).setOnClickListener((e) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://foxteargames.eu/notecompanion"));
            startActivity(browserIntent);
        });
        findViewById(R.id.youtubeButton).setOnClickListener((e) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@LimoDieFlasche"));
            startActivity(browserIntent);
        });
        findViewById(R.id.discordButton).setOnClickListener((e) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.com/invite/zyxqnNja5a"));
            startActivity(browserIntent);
        });
        findViewById(R.id.icon).setClipToOutline(true);
    }
}