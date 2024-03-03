package com.limo.notecompanion;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.limo.notecompanion.databinding.LatexEditorBinding;
import com.limo.notecompanion.util.ClipboardUtil;
import com.limo.notecompanion.util.SteganographyImgProcess;
import maximsblog.blogspot.com.jlatexmath.cache.JLaTeXMathCache;
import maximsblog.blogspot.com.jlatexmath.core.AjLatexMath;
import maximsblog.blogspot.com.jlatexmath.core.TeXConstants;
import maximsblog.blogspot.com.jlatexmath.core.TeXFormula;

import java.util.ArrayList;
import java.util.List;

public class LatexEditor extends AppCompatActivity {
    public SwitchMaterial integrateStego;
    private ImageView imageView;
    private EditText editText;
    private Bitmap currentBitmap;
    private boolean isEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AjLatexMath.init(this);
        JLaTeXMathCache.setMaxCachedObjects(100);

        LatexEditorBinding binding = LatexEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        integrateStego = findViewById(R.id.integrateSteganography);
        imageView = findViewById(R.id.texView);
        editText = findViewById(R.id.editTexCode);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                isEncoded = false;
                try {
                    StringBuilder builder = new StringBuilder();
                    String[] lines = charSequence.toString().split("\\\\\\\\\\\\\\\\|\\n");
                    int minTabs = 3;
                    for(int i = 0; i < lines.length; i++) {
                        if(i == 0) {
                            if(lines[i].startsWith("#align ")) {
                                List<String> list = new ArrayList<>();
                                for (char c : lines[i].substring(7).toCharArray()) {
                                    list.add(String.valueOf(c));
                                }
                                minTabs = list.size();
                                System.out.println(minTabs);
                                builder .append("\\[\\begin{tabular}{")
                                        .append(String.join("@{\\;}", list))
                                        .append("}\n");
                                continue;
                            } else
                                builder.append("\\[\\begin{tabular}{l@{\\;}c@{\\;}r}\n");

                        }
                        builder .append("$")
                                .append(lines[i]
                                        .replace("=", "$ & $=$ & $")
                                        .replace("\\tab", "&& "))
                                .append("$");
                        int tabs = lines[i].split("=").length;
                        if(tabs > 0)
                            tabs = tabs * 2 - 1;
                        for(int i2 = tabs; i2 < minTabs; i2++) {
                            builder.append("&");
                        }
                        builder.append("\\\\\n");
                    }
                    if(lines.length > 0)
                        builder.append("\\end{tabular}\\]");

                    System.out.println(builder);
                    TeXFormula tf = new TeXFormula(builder.toString());
                    imageView.setImageBitmap(currentBitmap = tf.createBufferedImage(TeXConstants.STYLE_DISPLAY, 30, null, null));
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        findViewById(R.id.copy).setOnClickListener((v) -> {
            if(currentBitmap == null)
                return;
            if(integrateStego.isChecked()) {
                String text = editText.getText().toString();
                if(!isEncoded){
                    Bitmap newBitmap = SteganographyImgProcess.encode(currentBitmap, text);
                    if(newBitmap == null)
                        Toast.makeText(this, "Failed to encode Sourcecode", Toast.LENGTH_SHORT).show();
                    else {
                        currentBitmap = newBitmap;
                        imageView.setImageBitmap(currentBitmap);
                    }
                } else if (!text.equals(SteganographyImgProcess.decode(currentBitmap))) {
                    System.out.println("Failed to verify Steganography in currentBitmap");
                }
                isEncoded = true;
            }
            ClipboardUtil.copyBitmapToClipboard(this, currentBitmap);
        });
        findViewById(R.id.paste).setOnClickListener((v) -> {
            Bitmap bm = ClipboardUtil.getBitmapFromClipboard(this);
            if(bm == null) {
                Toast.makeText(this, "No Image in Clipboard", Toast.LENGTH_SHORT).show();
                return;
            }
            String stego = SteganographyImgProcess.decode(bm);
            if(stego == null || stego.isEmpty()) {
                Toast.makeText(this, "No Steganography in Image", Toast.LENGTH_SHORT).show();
                return;
            }
            currentBitmap = bm;
            isEncoded = true;
            editText.setText(stego);
            imageView.setImageBitmap(bm);
        });
    }
}