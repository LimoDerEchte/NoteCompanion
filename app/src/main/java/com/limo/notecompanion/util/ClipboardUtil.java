package com.limo.notecompanion.util;

import android.content.*;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ClipboardUtil {

    // Method to copy a Bitmap to the clipboard
    public static void copyBitmapToClipboard(Context context, Bitmap bitmap) {
        // Save bitmap to external storage
        String fileName = "image_" + System.currentTimeMillis() + ".png";
        File imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(imagesDir, fileName);

        try {
            OutputStream outputStream = Files.newOutputStream(imageFile.toPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // Use PNG format
            outputStream.close();
        } catch (IOException e) {
            Log.e("SaveBitmap", "Error saving bitmap", e);
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add image to MediaStore
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // Specify PNG MIME type

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        // Copy URI to clipboard
        if (uri != null) {
            try {
                OutputStream imageStream = contentResolver.openOutputStream(uri);
                if (imageStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, imageStream);
                    imageStream.close();
                }
            } catch (IOException e) {
                Log.e("SaveBitmap", "Error saving bitmap to MediaStore", e);
                Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
                return;
            }

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newUri(context.getContentResolver(), "Image", uri);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Image saved to MediaStore and URI copied to clipboard", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to retrieve a Bitmap from the clipboard
    public static Bitmap getBitmapFromClipboard(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null && clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                if (item != null) {
                    Uri uri = item.getUri();
                    if (uri != null) {
                        return uriToBitmap(context.getContentResolver(), uri);
                    }
                    String html = item.getHtmlText();
                    if(html != null && !html.isEmpty()) {
                        return parseHtmlBitmap(context.getContentResolver(), html);
                    }
                }
            }
        }
        return null;
    }

    private static Bitmap uriToBitmap(ContentResolver contentResolver, Uri uri) {
        try {
            return android.graphics.BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
        } catch (java.io.FileNotFoundException e) {
            return null;
        }
    }

    private static Bitmap parseHtmlBitmap(ContentResolver contentResolver, String html) {
        int imgStart = html.indexOf("<img ");
        int srcStart = html.indexOf("src", imgStart);
        int tagStart = html.indexOf("\"", srcStart) + 1;
        int tagEnd = html.indexOf("\"", tagStart);
        html = html.substring(tagStart, tagEnd);
        return uriToBitmap(contentResolver, Uri.parse(html));
    }
}
