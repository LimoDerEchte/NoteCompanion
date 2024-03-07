package com.limo.notecompanion.util;

import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatDelegate;
import com.limo.notecompanion.R;

import java.util.Locale;

public class Settings {
    public static String language;
    public static String theme;
    public static String exportColor;
    public static boolean desktopMode;

    public static void load(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences("default_settings", Context.MODE_PRIVATE);

        language = pref.getString("language", "en_us");
        theme = pref.getString("theme", "SYSTEM");
        exportColor = pref.getString("exportColor", "INHERIT");
        desktopMode = pref.getBoolean("desktopMode", isDesktopMode(ctx));
    }

    public static void applyThemeAndLanguage(Context ctx, AppCompatDelegate delegate) {
        ctx.getResources().getConfiguration().setLocale(new Locale(language));
        delegate.setLocalNightMode(theme.equalsIgnoreCase("SYSTEM") ?
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM : theme.equalsIgnoreCase("LIGHT") ?
                AppCompatDelegate.MODE_NIGHT_NO :
                AppCompatDelegate.MODE_NIGHT_YES);
    }

    public static void save(Context ctx) {
        SharedPreferences pref = ctx.getSharedPreferences("default_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("language", language);
        edit.putString("theme", theme);
        edit.putString("exportColor", exportColor);
        edit.putBoolean("desktopMode", desktopMode);
        edit.apply();
    }

    private static boolean isDesktopMode(Context context){
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_DESK);
    }
}
