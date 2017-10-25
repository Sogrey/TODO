/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sogrey.sogreyframe.utils;

import java.math.BigDecimal;

/**
 * © 2012 amsoft.cn 名称：AbMathUtil.java 描述：数学处理类.
 * 
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */
public class MathUtil {

	/**
	 * 四舍五入.
	 * 
	 * @param number
	 *            原数
	 * @param decimal
	 *            保留几位小数
	 * @return 四舍五入后的值
	 */
	public static BigDecimal round(double number,int decimal) {
		return new BigDecimal(number).setScale(decimal,
											   BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 描述：字节数组转换成16进制串.
	 * 
	 * @param b
	 *            the b
	 * @param length
	 *            the length
	 * @return the string
	 */
	public static String byte2HexStr(byte[] b,int length) {
		String hs  = "";
		String stmp= "";
		for (int n = 0; n < length; ++n) {
			stmp = Integer.toHexString(b[n]&0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}
			hs = hs + ",";
		}
		return hs.toUpperCase();
	}

	private static final String BINARYTOHEX= "0123456789ABCDEF";

	public static char binaryToHex0(int binary) {
		char ch = ' ';
		if (binary >= 0 && binary <= 15) {
			char[] charArray = BINARYTOHEX.toCharArray();
			ch = charArray[binary];
		}
		return ch;
	}

	/**
	 * 二进制转为十六进制.
	 * 
	 * @param binary
	 *            the binary
	 * @return char hex
	 */
	public static char binaryToHex(int binary) {
		char ch = ' ';
		switch (binary) {
		case 0:
			ch = '0';
			break;
		case 1:
			ch = '1';
			break;
		case 2:
			ch = '2';
			break;
		case 3:
			ch = '3';
			break;
		case 4:
			ch = '4';
			break;
		case 5:
			ch = '5';
			break;
		case 6:
			ch = '6';
			break;
		case 7:
			ch = '7';
			break;
		case 8:
			ch = '8';
			break;
		case 9:
			ch = '9';
			break;
		case 10:
			ch = 'a';
			break;
		case 11:
			ch = 'b';
			break;
		case 12:
			ch = 'c';
			break;
		case 13:
			ch = 'd';
			break;
		case 14:
			ch = 'e';
			break;
		case 15:
			ch = 'f';
			break;
		default:
			ch = ' ';
		}
		return ch;
	}

	/**
	 * 
	 * 一维数组转为二维数组
	 * 
	 * 
	 * @param m
	 *            the m
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the int[][]
	 */
	public static int[][] arrayToMatrix(int[] m, int width, int height) {
		int[][] result = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int p = j * height + i;
				result[i][j] = m[p];
			}
		}
		return result;
	}

	/**
	 * 
	 * 二维数组转为一维数组
	 * 
	 * 
	 * @param m
	 *            the m
	 * @return the double[]
	 */
	public static double[] matrixToArray(double[][] m) {
		int p = m.length * m[0].length;
		double[] result = new double[p];
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				int q = j * m.length + i;
				result[q] = m[i][j];
			}
		}
		return result;
	}

	/**
	 * 描述：int数组转换为double数组.
	 * 
	 * @param input
	 *            the input
	 * @return the double[]
	 */
	public static double[] intToDoubleArray(int[] input) {
		int length = input.length;
		double[] output = new double[length];
		for (int i = 0; i < length; i++) {
			output[i] = Double.valueOf(String.valueOf(input[i]));
		}
		return output;
	}

	/**
	 * 描述：int二维数组转换为double二维数组.
	 * 
	 * @param input
	 *            the input
	 * @return the double[][]
	 */
	public static double[][] intToDoubleMatrix(int[][] input) {
		int height = input.length;
		int width = input[0].length;
		double[][] output = new double[height][width];
		for (int i = 0; i < height; i++) {
			// 列
			for (int j = 0; j < width; j++) {
				// 行
				output[i][j] = Double.valueOf(String.valueOf(input[i][j]));
			}
		}
		return output;
	}

	/**
	 * 计算数组的平均值.
	 * 
	 * @param pixels
	 *            数组
	 * @return int 平均值
	 */
	public static int average(int[] pixels) {
		float m = 0;
		for (int i = 0; i < pixels.length; ++i) {
			m += pixels[i];
		}
		m = m / pixels.length;
		return (int) m;
	}

	/**
	 * 计算数组的平均值.
	 * 
	 * @param pixels
	 *            数组
	 * @return int 平均值
	 */
	public static int average(double[] pixels) {
		float m = 0;
		for (int i = 0; i < pixels.length; ++i) {
			m += pixels[i];
		}
		m = m / pixels.length;
		return (int) m;
	}

	/**
	 * 
	 * 描述：点在直线上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在直线BC上吗?
	 * 
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean pointAtSLine(double x, double y, double x1,
			double y1, double x2, double y2) {
		return (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1) == 0;
	}

	/**
	 * 描述：点在直线上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在直线BC上吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param point
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean pointAtSLine(Point point, Point a, Point b) {
		double x = point.x;
		double y = point.y;
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		return pointAtSLine(x, y, x1, y1, x2, y2);
	}

	/**
	 * 
	 * 描述：点在线段上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在线段BC上吗?
	 * 
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean pointAtELine(double x, double y, double x1,
			double y1, double x2, double y2) {
		if (pointAtSLine(x, y, x1, y1, x2, y2)) {
			if (x>=Math.min(x1,x2)&&x<=Math.max(x1,x2)
				&&y>=Math.min(y1,y2)&&y<=Math.max(y1,y2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 描述：点在线段上. 点A（x，y）,B(x1,y1),C(x2,y2) 点A在线段BC上吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param point
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean pointAtELine(Point point, Point a, Point b) {
		double x = point.x;
		double y = point.y;
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		return pointAtELine(x, y, x1, y1, x2, y2);
	}

	/**
	 * 
	 * 描述：两条直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 直线AB与直线CD相交吗?
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	public static boolean LineAtLine(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		double k1 = (y2 - y1) / (x2 - x1);
		double k2 = (y4 - y3) / (x4 - x3);
		if (k1 == k2) {
			// System.out.println("平行线");
			return false;
		} else {
			double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4)
					* (x1 - x2))
					/ ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
			double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
			// System.out.println("直线的交点("+x+","+y+")");
			return true;
		}
	}

	/**
	 * 描述：两条直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 直线AB与直线CD相交吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static boolean LineAtLine(Point a, Point b, Point c, Point d) {
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		double x3 = c.x;
		double y3 = c.y;
		double x4 = d.x;
		double y4 = d.y;
		return LineAtLine(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	/**
	 * 
	 * 描述：线段与线段相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与线段CD相交吗?
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	public static boolean eLineAtELine(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		double k1 = (y2 - y1) / (x2 - x1);
		double k2 = (y4 - y3) / (x4 - x3);
		if (k1 == k2) {
			// System.out.println("平行线");
			return false;
		} else {
			double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4)
					* (x1 - x2))
					/ ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
			double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
			// System.out.println("直线的交点("+x+","+y+")");
			if (x>=Math.min(x1,x2)&&x<=Math.max(x1,x2)
				&&y>=Math.min(y1,y2)&&y<=Math.max(y1,y2)
				&&x>=Math.min(x3,x4)&&x<=Math.max(x3,x4)
				&&y>=Math.min(y3,y4)&&y<=Math.max(y3,y4)) {
				// System.out.println("交点（"+x+","+y+"）在线段上");
				return true;
			} else {
				// System.out.println("交点（"+x+","+y+"）不在线段上");
				return false;
			}
		}
	}

	/**
	 * 描述：线段与线段相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与线段CD相交吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static boolean eLineAtELine(Point a, Point b, Point c, Point d) {
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		double x3 = c.x;
		double y3 = c.y;
		double x4 = d.x;
		double y4 = d.y;
		return eLineAtELine(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	/**
	 * 
	 * 描述：线段直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与直线CD相交吗?
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	public static boolean eLineAtLine(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		double k1 = (y2 - y1) / (x2 - x1);
		double k2 = (y4 - y3) / (x4 - x3);
		if (k1 == k2) {
			// System.out.println("平行线");
			return false;
		} else {
			double x = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x3 * y4 - y3 * x4)
					* (x1 - x2))
					/ ((y2 - y1) * (x3 - x4) - (y4 - y3) * (x1 - x2));
			double y = (x1 * y2 - y1 * x2 - x * (y2 - y1)) / (x1 - x2);
			// System.out.println("交点("+x+","+y+")");
			if (x>=Math.min(x1,x2)&&x<=Math.max(x1,x2)
				&&y>=Math.min(y1,y2)&&y<=Math.max(y1,y2)) {
				// System.out.println("交点（"+x+","+y+"）在线段上");
				return true;
			} else {
				// System.out.println("交点（"+x+","+y+"）不在线段上");
				return false;
			}
		}
	}

	/**
	 * 描述：线段直线相交. 点A（x1，y1）,B(x2,y2),C(x3,y3),D(x4,y4) 线段AB与直线CD相交吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static boolean eLineAtLine(Point a, Point b, Point c, Point d) {
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		double x3 = c.x;
		double y3 = c.y;
		double x4 = d.x;
		double y4 = d.y;
		return eLineAtLine(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	/**
	 * 
	 * 描述：点在矩形内. 矩形的边都是与坐标系平行或垂直的。 只要判断该点的横坐标和纵坐标是否夹在矩形的左右边和上下边之间。
	 * 点A（x，y）,B(x1,y1),C(x2,y2) 点A在以直线BC为对角线的矩形中吗?
	 * 
	 * @param x
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean pointAtRect(double x, double y, double x1, double y1,
			double x2, double y2) {
		if (x>=Math.min(x1,x2)&&x<=Math.max(x1,x2)
			&&y>=Math.min(y1,y2)&&y<=Math.max(y1,y2)) {
			// System.out.println("点（"+x+","+y+"）在矩形内上");
			return true;
		} else {
			// System.out.println("点（"+x+","+y+"）不在矩形内上");
			return false;
		}
	}

	/**
	 * 描述：点在矩形内. 矩形的边都是与坐标系平行或垂直的。 只要判断该点的横坐标和纵坐标是否夹在矩形的左右边和上下边之间。
	 * 点A（x，y）,B(x1,y1),C(x2,y2) 点A在以直线BC为对角线的矩形中吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static boolean pointAtRect(Point a, Point b, Point c) {
		double x = a.x;
		double y = a.y;
		double x1 = b.x;
		double y1 = b.y;
		double x2 = c.x;
		double y2 = c.y;
		return pointAtRect(x, y, x1, y1, x2, y2);
	}

	/**
	 * 
	 * 描述：矩形在矩形内. 只要对角线的两点都在另一个矩形中就可以了. 点A1(x1,y1),B1(x2,y2)，A2(x3,y3),B2(x4,y4)
	 * 以直线AB为对角线的矩形在以直线BC为对角线的矩形中吗?
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	public static boolean rectAtRect(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		if (x1>=Math.min(x3,x4)&&x1<=Math.max(x3,x4)
			&&y1>=Math.min(y3,y4)&&y1<=Math.max(y3,y4)
			&&x2>=Math.min(x3,x4)&&x2<=Math.max(x3,x4)
			&&y2>=Math.min(y3,y4)&&y2<=Math.max(y3,y4)) {
			// System.out.println("矩形在矩形内");
			return true;
		} else {
			// System.out.println("矩形不在矩形内");
			return false;
		}
	}

	/**
	 * 描述：矩形在矩形内. 只要对角线的两点都在另一个矩形中就可以了. 点A1(x1,y1),B1(x2,y2)，A2(x3,y3),B2(x4,y4)
	 * 以直线AB为对角线的矩形在以直线BC为对角线的矩形中吗?
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a1
	 * @param b1
	 * @param a2
	 * @param b2
	 * @return
	 */
	public static boolean rectAtRect(Point a1, Point b1, Point a2, Point b2) {
		double x1 = a1.x;
		double y1 = a1.y;
		double x2 = b1.x;
		double y2 = b1.y;
		double x3 = a2.x;
		double y3 = a2.y;
		double x4 = b2.x;
		double y4 = b2.y;
		return rectAtRect(x1, y1, x2, y2, x3, y3, x4, y4);
	}

	/**
	 * 
	 * 描述：圆心在矩形内 . 圆心在矩形中且圆的半径小于等于圆心到矩形四边的距离的最小值。 圆心O(x,y) 半径r
	 * 矩形对角点A（x1，y1），B(x2，y2)
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static boolean circleAtRect(double x, double y, double r, double x1,
			double y1, double x2, double y2) {
		// 圆心在矩形内
		if (x>=Math.min(x1,x2)&&x<=Math.max(x1,x2)
			&&y>=Math.min(y1,y2)&&y<=Math.max(y1,y2)) {
			// 圆心到4条边的距离
			double l1 = Math.abs(x-x1);
			double l2 = Math.abs(y-y2);
			double l3 = Math.abs(x-x2);
			double l4 = Math.abs(y-y2);
			if (r <= l1 && r <= l2 && r <= l3 && r <= l4) {
				// System.out.println("圆在矩形内");
				return true;
			} else {
				// System.out.println("圆不在矩形内");
				return false;
			}

		} else {
			// System.out.println("圆不在矩形内");
			return false;
		}
	}

	/**
	 * 描述：圆心在矩形内 . 圆心在矩形中且圆的半径小于等于圆心到矩形四边的距离的最小值。 圆心O(x,y) 半径r
	 * 矩形对角点A（x1，y1），B(x2，y2)
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param o
	 *            圆心
	 * @param r
	 *            半径
	 * @param a
	 *            第一个点
	 * @param b
	 *            第二个点
	 * @return
	 */
	public static boolean circleAtRect(Point o, double r, Point a, Point b) {
		float x = o.x;
		float y = o.y;
		float x1 = a.x;
		float y1 = a.y;
		float x2 = b.x;
		float y2 = b.y;
		return circleAtRect(x, y, r, x1, y1, x2, y2);
	}

	/**
	 * 
	 * 描述：获取两点间的距离.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double getDistance(double x1, double y1, double x2, double y2) {
		double x = x1 - x2;
		double y = y1 - y2;
		return Math.sqrt(x*x+y*y);
	}

	/**
	 * 描述：获取两点间的距离.
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 *            第一个点
	 * @param b
	 *            第二个点
	 * @return
	 */
	public static double getDistance(Point a, Point b) {
		double x = a.x - b.x;
		double y = a.y - b.y;
		return Math.sqrt(x*x+y*y);
	}

	/**
	 * 判断一个点与是否吻合，偏移半径
	 * 
	 * @param ax
	 *            A点X坐标
	 * @param ay
	 *            A点Y坐标
	 * @param r
	 *            半径
	 * @param bx
	 *            B点X坐标
	 * @param by
	 *            B点Y坐标
	 * @return
	 */
	public static boolean isSame(float ax, float ay, float r, float bx, float by) {
		// 开方
		return Math.sqrt((ax-bx)*(ax-bx)+(ay-by)*(ay-by))<r;
	}

	/**
	 * 判断一个点与是否吻合，偏移半径
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param a
	 *            第一个点
	 * @param b
	 *            第二个点
	 * @param r
	 *            半径（默认两点半径相等）
	 * @return
	 */
	public static boolean isSame(Point a, Point b, float r) {
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		return isSame(ax, ay, r, bx, by);
	}

	/**
	 * 获取角度（手机左上角(0,0)）
	 * 
	 * @author Sogrey
	 * @date 2015年7月13日
	 * @param ax
	 *            A点x坐标
	 * @param ay
	 *            A点y坐标
	 * @param bx
	 *            B点x坐标
	 * @param by
	 *            B点y坐标
	 * @return 角度（∠ °）
	 */
	public static double getDegress(float ax, float ay, float bx, float by) {

		if (bx == ax) {// x轴相等，90/270
			if (by > ay) {// b点在上
				return 90;
			} else {
				return 270;
			}
		} else if (by == ay) {// y轴相等，0/180
			if (bx > ax) {// b点在右
				return 0;
			} else {
				return 180;
			}
		} else {
			return Math.atan2(by-ay,bx-ax)/Math.PI*180;
		}
	}

	/**
	 * 获取角度（手机左上角(0,0)）
	 * 
	 * @param a
	 *            第一个点
	 * @param b
	 *            第二个点
	 * @return 角度（∠ °）
	 */
	public static double getDegress(Point a, Point b) {
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		return getDegress(ax, ay, bx, by);
	}

	/**
	 * 矩形碰撞检测 参数为x,y,width,height
	 * 
	 * @param x1
	 *            第一个矩形的x
	 * @param y1
	 *            第一个矩形的y
	 * @param w1
	 *            第一个矩形的w
	 * @param h1
	 *            第一个矩形的h
	 * @param x2
	 *            第二个矩形的x
	 * @param y2
	 *            第二个矩形的y
	 * @param w2
	 *            第二个矩形的w
	 * @param h2
	 *            第二个矩形的h
	 * @return 是否碰撞
	 */
	public static boolean isRectCollision(float x1, float y1, float w1,
			float h1, float x2, float y2, float w2, float h2) {
		if (x2 > x1 && x2 > x1 + w1) {
			return false;
		} else if (x2 < x1 && x2 < x1 - w2) {
			return false;
		} else if (y2 > y1 && y2 > y1 + h1) {
			return false;
		} else if (y2 < y1 && y2 < y1 - h2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 自定义点
	 * 
	 * @author Sogrey
	 * 
	 */
	public static class Point {
		// 点坐标
		public float x, y;

		public int index = 0, state = 0;

		public Point() {
		}

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
}
