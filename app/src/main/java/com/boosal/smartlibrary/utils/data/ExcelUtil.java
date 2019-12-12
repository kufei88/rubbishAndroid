package com.boosal.smartlibrary.utils.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil {

	private Workbook workbook = null;

	/**
	 * 根据文件路径返回Sheet对象
	 * @param path
	 * @return sheet
	 */
	public Sheet getSheetByPath(String path) {
		try {
			//获取文件
			File file = new File(path);
			Sheet sheet = null;
			workbook = Workbook.getWorkbook(file);
			sheet = workbook.getSheet(0);
			return sheet;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据Sheet对象获取图片集合
	 * @param sheet
	 * @return map
	 */
	public Map<String,byte[]> getSheetPictures(Sheet sheet) {
		Map<String,byte[]> map = new HashMap<String,byte[]>();
		for(int i=0;i<sheet.getNumberOfImages();i++) {
			Image image = sheet.getDrawing(i);
			if(image != null) {
				//拿到图片所在行索引
				String key = String.valueOf((int)image.getRow());
				System.out.println("读取到的图片索引："+key);
				byte[] imageData = image.getImageData();
				map.put(key, imageData);
			}
		}
		return map;
	}

	public void realseObject() {
		if(workbook != null) {
			workbook.close();
		}
	}
}
