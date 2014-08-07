package com.lizard.eye.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SerializedColorSearchStorage implements Parcelable {

	private int colorCode, imageId;

	public SerializedColorSearchStorage(int colorCode, int imageId) {
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

	public int getImageId() {
		return this.imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	// The following methods that are required for using Parcelable
	private SerializedColorSearchStorage(Parcel in) {
		// This order must match the order in writeToParcel()
		colorCode = in.readInt();
		imageId = in.readInt();
		// Continue doing this for the rest of your member data
	}

	public void writeToParcel(Parcel out, int flags) {
		// Again this order must match the Question(Parcel) constructor
		out.writeInt(colorCode);
		out.writeInt(imageId);
		// Again continue doing this for the rest of your member data
	}

	// Just cut and paste this for now
	public int describeContents() {
		return 0;
	}

	// Just cut and paste this for now
	public static final Parcelable.Creator<SerializedColorSearchStorage> CREATOR = new Parcelable.Creator<SerializedColorSearchStorage>() {
		public SerializedColorSearchStorage createFromParcel(Parcel in) {
			return new SerializedColorSearchStorage(in);
		}

		public SerializedColorSearchStorage[] newArray(int size) {
			return new SerializedColorSearchStorage[size];
		}
	};
}
