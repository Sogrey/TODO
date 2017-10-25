/**
 * @author Sogrey
 * @date 2015-11-26下午3:43:30
 */
package org.sogrey.sogreyframe.utils;

import java.text.NumberFormat;

/**
 * 数字格式化
 * @author Sogrey
 * @date 2015-11-26下午3:43:30
 */
public class NumberUtils {

	public static String NumFromat(int num) {
		if (num<10000) {
			NumberFormat nf1= NumberFormat.getInstance();
			return(nf1.format(num));
		}else{
			int w = num/10000;
			int k = (num%10000)/1000;
			return(w+"."+k+"w");
		}
	}
	
	public static String NumFromat(String num) {
		int numInt = 0;
		try {
			numInt =  Integer.parseInt(num);
		} catch (Exception e) {
		}
		return NumFromat(numInt);
	}
}
