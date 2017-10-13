package com.example.mnkj.newobject.Utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;


import com.example.mnkj.newobject.Base.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * 拍照类
 * @author miliotech01
 *
 */
public class PictureFun {
	// 图片质量压缩
	private static byte[] compressBmpToFile(String picturePath) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bitmap bmp = getBitmap(picturePath);
		int options = 80;
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		while (baos.toByteArray().length / 1024 > 500) {
			baos.reset();
			options -= 10;
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		return baos.toByteArray();
	}

	public static Bitmap getBitmap(String imgPath) {
		// Get bitmap through image path
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		// Do not compress

		newOpts.inSampleSize = 8;
		newOpts.inPreferredConfig = Config.RGB_565;
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}

	private static boolean fileIsExists(String strFile) {
		try {
			File f = new File(strFile);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String getPictureStr(String imgPath) {
		if (fileIsExists(imgPath)) {
			byte[] buffer = compressBmpToFile(imgPath);
			Log.i("aaa", "psize" + buffer.length);
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		}
		return "";
	}


	/***
	 * 將JSOn保存到本地文件中
	 * @param json
	 * @return
	 */
	public static boolean SavaJson(String json) {
		boolean state = false;
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 判断是否存在SD卡
			
			return false;
		}
		String strPath = MyApplication.getContext().getExternalFilesDir("")
				.getAbsolutePath()
				+ File.separator
				+ "Error"
				+ File.separator
				+ "Error"
				+ "_json_"  + ".txt";
		File file = new File(strPath);
		if (!file.getParentFile().exists()) {// 判断父文件是否存在，如果不存在则创建
			file.getParentFile().mkdirs();
		}
		PrintStream out = null; // 打印流
		try {
			out = new PrintStream(new FileOutputStream(file)); // 实例化打印流对象
			out.print(json); // 输出数据
			state = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) { // 如果打印流不为空，则关闭打印流
				out.close();
			}
		}
		return state;
	}

}