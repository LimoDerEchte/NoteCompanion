package com.limo.notecompanion;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.limo.notecompanion.databinding.LatexEditorBinding;
import com.limo.notecompanion.databinding.ReactionEditorBinding;
import com.limo.notecompanion.util.ClipboardUtil;
import com.limo.notecompanion.util.Settings;
import com.limo.notecompanion.util.SteganographyImgProcess;
import maximsblog.blogspot.com.jlatexmath.core.TeXConstants;
import maximsblog.blogspot.com.jlatexmath.core.TeXFormula;

import java.util.ArrayList;
import java.util.List;

public class ReactionEditor extends AppCompatActivity {
    public static final List<Character> validUpNumberChars = new ArrayList<>();

    static {
        validUpNumberChars.add('0');
        validUpNumberChars.add('1');
        validUpNumberChars.add('2');
        validUpNumberChars.add('3');
        validUpNumberChars.add('4');
        validUpNumberChars.add('5');
        validUpNumberChars.add('6');
        validUpNumberChars.add('7');
        validUpNumberChars.add('8');
        validUpNumberChars.add('9');
        validUpNumberChars.add('+');
        validUpNumberChars.add('-');
    }

    public SwitchMaterial integrateStego;
    private ImageView imageView;
    private EditText editText;
    private Bitmap currentBitmap;
    private boolean isEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReactionEditorBinding binding = ReactionEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Settings.applyThemeAndLanguage(this, getDelegate());

        integrateStego = findViewById(R.id.integrateSteganography);
        imageView = findViewById(R.id.texView);
        editText = findViewById(R.id.editTexCode);
        imageView.setClipToOutline(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                isEncoded = false;
                try {
                    StringBuilder builder = new StringBuilder();
                    String[] lines = charSequence.toString().split("\\\\\\\\\\\\\\\\|\\n");
                    builder.append("\\[\\begin{tabular}{l@{\\;}c@{\\;}r}\n");
                    for (String s : lines) {
                        StringBuilder line = new StringBuilder();
                        line.append("$ \\text{");
                        char[] chars = s.toCharArray();
                        boolean numberMode = false;
                        boolean upNumberMode = false;
                        for (int j = 0; j < chars.length; j++) {
                            if (j > 0) {
                                // High Numbers
                                if (upNumberMode && !validUpNumberChars.contains(chars[j])) {
                                    line.append("}$");
                                }
                                if (validUpNumberChars.contains(chars[j]) && chars[j - 1] == '^' && !numberMode && !upNumberMode) {
                                    upNumberMode = true;
                                    line.append("$^{");
                                } else if (!validUpNumberChars.contains(chars[j]))
                                    upNumberMode = false;
                                // Low Numbers
                                if (numberMode && !Character.isDigit(chars[j])) {
                                    line.append("}$");
                                }
                                if (Character.isDigit(chars[j]) && !Character.isDigit(chars[j - 1]) && chars[j - 1] != ' ' && !numberMode && !upNumberMode) {
                                    numberMode = true;
                                    line.append("$_{");
                                } else if (!Character.isDigit(chars[j]))
                                    numberMode = false;
                                // Line
                                if (j < chars.length - 2 && chars[j] == '-' && chars[j + 1] == '-' && chars[j + 2] == '>') {
                                    line.append(" } $ & $ \\longrightarrow $ & $ \\text{ ");
                                    j += 2;
                                    continue;
                                }
                                // Skip Errors
                                if (chars[j] == '^' && validUpNumberChars.contains(chars[j + 1]))
                                    continue;
                            }
                            line.append(chars[j]);
                        }
                        if(numberMode || upNumberMode)
                            line.append("}");
                        line.append("} $");
                        if(!s.contains("-->"))
                            line.append(" &&");

                        builder.append(line);
                        builder.append("\\\\\n");
                    }
                    if(lines.length > 0)
                        builder.append("\\end{tabular}\\]");

                    TeXFormula tf = new TeXFormula(builder.toString());
                    imageView.setImageBitmap(currentBitmap = tf.createBufferedImage(TeXConstants.STYLE_DISPLAY, 30, getResources().getColor(R.color.neutral90, getTheme()), null));
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