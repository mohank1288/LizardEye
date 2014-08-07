package com.lizard.eye.model;

public class ColorSearchStorage {
	private int colorCode, imageId;

	public ColorSearchStorage(int colorCode, int imageId) {
		super();
		this.colorCode = colorCode;
		this.imageId = imageId;
	}

	public int getColorCode() {
		return this.colorCode;
	}

	public void setColorCode(int colorCode) {
		this.colorCode = colorCode;
	}

	public int getimageId() {
		return this.imageId;
	}

	public void setimageId(int imageId) {
		this.imageId = imageId;
	}
}
