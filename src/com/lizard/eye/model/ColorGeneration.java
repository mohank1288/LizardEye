package com.lizard.eye.model;

import java.util.Random;

import android.content.Context;

import com.lizard.eyes.R;

public class ColorGeneration {

	private static Context context;

	public static int getColorCode(Context contexts, int min, int max) {
		context = contexts;
		int randomnumber = randInt(min, max);
		return getRandomColorCode(context, randomnumber);
	}

	
	/**Function for generation array of random numbers without repition**/
	public static int[] randomList(int min, int max, int number) {
	    // fills an array with all numbers from min to max
	    int[] choices = new int[max - min + 1];
	    int lastIndex = choices.length - 1;
	    for(int i = min; i <= max; i++) {
	        choices[i - min] = i;
	    }

	    // fills the new array with values from choices
	    Random r = new Random();
	    int[] randomList = new int[number];
	    for(int i = 0; i < number; i++) {
	        int index = r.nextInt(lastIndex + 1); 
	        randomList[i] = choices[index];
	        int copy = choices[lastIndex];
	        choices[lastIndex] = randomList[i];
	        choices[index] = copy;
	        lastIndex--;
	    }
	    return randomList;
	}
	
	public static int randInt(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static int getRandomColorCode(Context contexts, int i) {
		context = contexts;
		int colorCode;
		switch (i) {
		case 1:
			colorCode = context.getResources().getColor(R.color.cornflowerblue);
			break;

		case 2:
			colorCode = context.getResources().getColor(R.color.indianred);
			break;

		case 3:
			colorCode = context.getResources().getColor(R.color.cadetblue);
			break;

		case 4:
			colorCode = context.getResources().getColor(R.color.slategrey);
			break;

		case 5:
			colorCode = context.getResources().getColor(R.color.palevioletred);
			break;

		case 6:
			colorCode = context.getResources().getColor(R.color.seagreen);
			break;

		case 7:
			colorCode = context.getResources().getColor(R.color.teal);
			break;

		case 8:
			colorCode = context.getResources().getColor(R.color.orangered);
			break;

		case 9:
			colorCode = context.getResources().getColor(R.color.olivedrab);
			break;

		case 10:
			colorCode = context.getResources().getColor(R.color.indigo);
			break;

		case 11:
			colorCode = context.getResources().getColor(R.color.mediumpurple);

			break;
		case 12:
			colorCode = context.getResources().getColor(R.color.crimson);
			break;
		case 13:
			colorCode = context.getResources().getColor(R.color.red);
			break;

		case 14:
			colorCode = context.getResources().getColor(R.color.darkcyan);
			break;

		case 15:
			colorCode = context.getResources().getColor(R.color.deepskyblue);

			break;
		case 16:
			colorCode = context.getResources().getColor(R.color.royalblue);
			break;

		case 17:
			colorCode = context.getResources().getColor(R.color.blueviolet);
			break;

		case 18:
			colorCode = context.getResources().getColor(R.color.mediumorchid);
			break;

		case 19:
			colorCode = context.getResources().getColor(R.color.magneta);
			break;

		case 20:
			colorCode = context.getResources().getColor(R.color.dodgerblue);
			break;

		case 21:
			colorCode = context.getResources().getColor(R.color.deeppink);
			break;

		case 22:
			colorCode = context.getResources().getColor(R.color.yellowGreen);
			break;

		case 23:
			colorCode = context.getResources().getColor(R.color.forestgreen);
			break;

		default:
			colorCode = context.getResources().getColor(R.color.blueviolet);
			break;
		}
		return colorCode;
	}
}
