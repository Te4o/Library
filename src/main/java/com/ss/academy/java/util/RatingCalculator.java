package com.ss.academy.java.util;

import java.text.DecimalFormat;
import java.util.List;

import com.ss.academy.java.model.rating.Rating;

public class RatingCalculator {
	
	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private RatingCalculator() {
		
	}
	
	public static double calculate(List<Rating> bookRatings) {
		double result = 0;
		int bookRatingsCount = bookRatings.size();

		for (Rating rating : bookRatings) {
			result += Double.valueOf(rating.getRatingValue());
		}

		if (result == 0.0d) {
			return result;
		}

		return RoundTo2Decimals(result / bookRatingsCount);
	}

	private static double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("#.##");

		return Double.valueOf(df2.format(val));
	}
}
