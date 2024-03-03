/* ScaleBox.java
 * =========================================================================
 * This file is part of the JLaTeXMath Library - http://forge.scilab.org/jlatexmath
 *
 * Copyright (C) 2009 DENIZET Calixte
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 */

package maximsblog.blogspot.com.jlatexmath.core;

import java.awt.font.TextAttribute;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

/**
 * A box representing a scaled box.
 */
public class JavaFontRenderingBox extends Box {

	// private static final Graphics2D TEMPGRAPHIC = new BufferedImage(1, 1,
	// BufferedImage.TYPE_INT_ARGB).createGraphics();

	private static Typeface font = Typeface.SERIF;

	private String str;
	private float size;
	private static TextAttribute KERNING;
	private static Integer KERNING_ON;
	private static TextAttribute LIGATURES;
	private static Integer LIGATURES_ON;

	static {
		try { // to avoid problems with Java 1.5
			KERNING = (TextAttribute) (TextAttribute.class.getField("KERNING")
					.get(TextAttribute.class));
			KERNING_ON = (Integer) (TextAttribute.class.getField("KERNING_ON")
					.get(TextAttribute.class));
			LIGATURES = (TextAttribute) (TextAttribute.class
					.getField("LIGATURES").get(TextAttribute.class));
			LIGATURES_ON = (Integer) (TextAttribute.class
					.getField("LIGATURES_ON").get(TextAttribute.class));
		} catch (Exception e) {
		}
	}

	public JavaFontRenderingBox(String str, int type, float size, Typeface f,
			boolean kerning) {
		this.str = str;
		this.size = size;
		/*
		 * if (kerning && KERNING != null) { Map<TextAttribute, Object> map =
		 * new Hashtable<TextAttribute, Object>(); map.put(KERNING, KERNING_ON);
		 * map.put(LIGATURES, LIGATURES_ON); f = f.deriveFont(map); }
		 */

		// this.text = new TextLayout(str, f.deriveFont(type),
		// TEMPGRAPHIC.getFontRenderContext());
		// Rectangle2D rect = text.getBounds();
		// this.height = (float) (-rect.getY() * size / 10);
		// this.depth = (float) (rect.getHeight() * size / 10) - this.height;
		// this.width = (float) ((rect.getWidth() + rect.getX() + 0.4f) * size /
		// 10);
	}

	public JavaFontRenderingBox(String str, int type, float size) {
		this(str, type, size, font, true);
	}

	public static void setFont(String name) {
		AssetManager mng = AjLatexMath.getAssetManager();
		font = Typeface.createFromAsset(mng, name);
	}

	public void draw(Canvas g2, float x, float y) {
		drawDebug(g2, x, y);
		Paint st = AjLatexMath.getPaint();
		float w = st.getStrokeWidth();
		Style s = st.getStyle();
		Typeface f = st.getTypeface();

		st.setStrokeWidth(1);
		st.setStyle(Style.FILL_AND_STROKE);
		st.setTypeface(font);
		g2.save();
		g2.translate(x, y);
		g2.scale(0.1f * size, 0.1f * size);
		g2.drawText(str, x, y, st);
		g2.drawText(str, 0, 0, st);
		g2.restore();
		st.setStyle(s);
		st.setStrokeWidth(w);
		st.setTypeface(f);
	}

	public int getLastFontId() {
		return 0;
	}

}
