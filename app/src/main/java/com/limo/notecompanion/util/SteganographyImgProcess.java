package com.limo.notecompanion.util;

import android.graphics.Bitmap;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SteganographyImgProcess {
	public static Bitmap encode(Bitmap originalBitmap, String message) {
		int width = originalBitmap.getWidth();
		int height = originalBitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		newBitmap.setHasAlpha(true);

		byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
		int length = bytes.length;
		if((bytes.length) % 3 != 0) {
			bytes = Arrays.copyOf(bytes, bytes.length + 3 - bytes.length % 3);
		}
		System.out.println("Length: " + bytes.length);

		int maxLength = width * height * 3;
		if (bytes.length + 6 > maxLength) {
			return null;
		}

		// Header + Length
		newBitmap.setPixel(0, 0, 0xFF48C59D);
		newBitmap.setPixel(0, 1, length + 0xFF000000);

        for (int x = 0; x < width; x++) {
			for(int y = (x == 0 ? 2 : 0); y < height; y++) {
                int i = x * height + y * 3 - 6;
                if (i < bytes.length) {
					System.out.printf("X: %s, Y: %s, I: %s%n", x, y, i);
                    newBitmap.setPixel(x, y, bytesToInt((byte)0xFF, bytes[i], bytes[i + 1], bytes[i + 2]));
                } else
                    newBitmap.setPixel(x, y, originalBitmap.getPixel(x, y));
            }
        }
		System.out.println(Integer.toHexString(newBitmap.getPixel(0, 0)));
		System.out.println(newBitmap.getPixel(0, 1));
		return newBitmap;
	}

	public static String decode(Bitmap encodedBitmap) {
		System.out.println(encodedBitmap.getPixel(0, 0));
		System.out.println(encodedBitmap.getPixel(0, 1));
		int width = encodedBitmap.getWidth();
		int height = encodedBitmap.getHeight();

		// Header Check
		if(height > 1 && width > 0 && encodedBitmap.getPixel(0, 0) != 0xFF48C59D)  {
			System.out.println("Failed to find Header in Steganography");
			return null;
		}

		int realLength = encodedBitmap.getPixel(0, 1) - 0xFF000000;
		System.out.println("Length: " + realLength);

		int length = realLength;
		if(length % 3 != 0) {
			length = length + 3 - length % 3;
		}
		byte[] bytes = new byte[length];

		loop: for(int x = 0; x < width; x++) {
			for(int y = (x == 0 ? 2 : 0); y < height; y++) {
				int i = x * height + y * 3 - 6;
				if(i >= length)
					break loop;
				System.out.printf("X: %s, Y: %s, I: %s%n", x, y, i);
				byte[] split = intToBytes(encodedBitmap.getPixel(x, y));
				bytes[  i  ] = split[1];
				bytes[i + 1] = split[2];
				bytes[i + 2] = split[3];
			}
		}
		return new String(Arrays.copyOf(bytes, realLength), StandardCharsets.UTF_8);
	}

	public static int bytesToInt(byte b1, byte b2, byte b3, byte b4) {
		return ((b1 & 0xFF) << 24) | ((b2 & 0xFF) << 16) | ((b3 & 0xFF) << 8) | (b4 & 0xFF);
	}

	public static byte[] intToBytes(int value) {
		return new byte[] {
				(byte)((value >> 24) & 0xFF),
				(byte)((value >> 16) & 0xFF),
				(byte)((value >> 8) & 0xFF),
				(byte)(value & 0xFF)
		};
	}
}
