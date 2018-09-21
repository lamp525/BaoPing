package com.ncgroup.bp;

/**
 * @description: 基于最小二乘法的数据平滑算法
 * @author: Lin
 * @create: 2018年9月21日 下午4:03:51
 * @version: 1.0
 */
public class LeastSquareSmooth {

	/**
	 * @description: 禁止实例化
	 * 
	 */
	private LeastSquareSmooth() {
	}

	/**
	 * @description: 五点平滑
	 * @param input
	 * @return
	 */
	public static double[] fivePointsSmooth(double[] input) {
		if (input == null || input.length == 0)
			throw new IllegalArgumentException("数组不能为空！");

		double[] output = new double[input.length];
		output = linearSmooth5(input, input.length);
		output = quadraticSmooth5(output, input.length);
		output = cubicSmooth5(output, input.length);

		return output;
	}

	/**
	 * @description: 七点平滑
	 * @param input
	 * @return
	 */
	public static double[] sevenPointsSmooth(double[] input) {
		if (input == null || input.length == 0)
			throw new IllegalArgumentException("数组不能为空！");

		double[] output = new double[input.length];
		output = linearSmooth7(input, input.length);
		output = quadraticSmooth7(output, input.length);
		output = cubicSmooth7(output, input.length);

		return output;
	}

	/**
	 * @description: 五点线性平滑
	 * @param input
	 * @param N
	 */
	private static double[] linearSmooth5(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 5) {
			for (i = 0; i <= N - 1; i++) {
				output[i] = input[i];
			}
		} else {
			output[0] = input[0];
			// output[0] = (3.0 * input[0] + 2.0 * input[1] + input[2] - input[4]) / 5.0;
			output[1] = (4.0 * input[0] + 3.0 * input[1] + 2 * input[2] + input[3]) / 10.0;
			for (i = 2; i <= N - 3; i++) {
				output[i] = (input[i - 2] + input[i - 1] + input[i] + input[i + 1] + input[i + 2]) / 5.0;
			}
			output[N - 2] = (4.0 * input[N - 1] + 3.0 * input[N - 2] + 2 * input[N - 3] + input[N - 4]) / 10.0;
			output[N - 1] = (3.0 * input[N - 1] + 2.0 * input[N - 2] + input[N - 3] - input[N - 5]) / 5.0;
		}
		return output;
	}

	/**
	 * @description: 七点线性平滑
	 * @param input
	 * @param N
	 */
	private static double[] linearSmooth7(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 7) {
			for (i = 0; i <= N - 1; i++) {
				output[i] = input[i];
			}
		} else {
			output[0] = input[0];
			// output[0] = (13.0 * input[0] + 10.0 * input[1] + 7.0 * input[2] + 4.0 *
			// input[3] + input[4] - 2.0 * input[5] - 5.0 * input[6]) / 28.0;
			output[1] = (5.0 * input[0] + 4.0 * input[1] + 3 * input[2] + 2 * input[3] + input[4] - input[6]) / 14.0;
			output[2] = (7.0 * input[0] + 6.0 * input[1] + 5.0 * input[2] + 4.0 * input[3] + 3.0 * input[4]
					+ 2.0 * input[5] + input[6]) / 28.0;
			for (i = 3; i <= N - 4; i++) {
				output[i] = (input[i - 3] + input[i - 2] + input[i - 1] + input[i] + input[i + 1] + input[i + 2]
						+ input[i + 3]) / 7.0;
			}
			output[N - 3] = (7.0 * input[N - 1] + 6.0 * input[N - 2] + 5.0 * input[N - 3] + 4.0 * input[N - 4]
					+ 3.0 * input[N - 5] + 2.0 * input[N - 6] + input[N - 7]) / 28.0;
			output[N - 2] = (5.0 * input[N - 1] + 4.0 * input[N - 2] + 3.0 * input[N - 3] + 2.0 * input[N - 4]
					+ input[N - 5] - input[N - 7]) / 14.0;
			output[N - 1] = (13.0 * input[N - 1] + 10.0 * input[N - 2] + 7.0 * input[N - 3] + 4 * input[N - 4]
					+ input[N - 5] - 2 * input[N - 6] - 5 * input[N - 7]) / 28.0;
		}
		return output;
	}

	/**
	 * @description: 五点二次函数拟合平滑
	 * @param input
	 * @param N
	 */
	private static double[] quadraticSmooth5(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 5) {
			for (i = 0; i <= N - 1; i++) {
				output[i] = input[i];
			}
		} else {
			output[0] = input[0];
			// output[0] = (31.0 * input[0] + 9.0 * input[1] - 3.0 * input[2] - 5.0 *
			// input[3] + 3.0 * input[4]) / 35.0;
			output[1] = (9.0 * input[0] + 13.0 * input[1] + 12 * input[2] + 6.0 * input[3] - 5.0 * input[4]) / 35.0;
			for (i = 2; i <= N - 3; i++) {
				output[i] = (-3.0 * (input[i - 2] + input[i + 2]) + 12.0 * (input[i - 1] + input[i + 1])
						+ 17 * input[i]) / 35.0;
			}
			output[N - 2] = (9.0 * input[N - 1] + 13.0 * input[N - 2] + 12.0 * input[N - 3] + 6.0 * input[N - 4]
					- 5.0 * input[N - 5]) / 35.0;
			output[N - 1] = (31.0 * input[N - 1] + 9.0 * input[N - 2] - 3.0 * input[N - 3] - 5.0 * input[N - 4]
					+ 3.0 * input[N - 5]) / 35.0;
		}
		return output;
	}

	/**
	 * @description: 七点二次函数拟合平滑
	 * @param input
	 * @param N
	 */
	private static double[] quadraticSmooth7(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 7) {
			for (i = 0; i <= N - 1; i++) {
				output[i] = input[i];
			}
		} else {
			output[0] = input[0];
			// output[0] = (32.0 * input[0] + 15.0 * input[1] + 3.0 * input[2] - 4.0 *
			// input[3] - 6.0 * input[4] - 3.0 * input[5] + 5.0 * input[6]) / 42.0;
			output[1] = (5.0 * input[0] + 4.0 * input[1] + 3.0 * input[2] + 2.0 * input[3] + input[4] - input[6])
					/ 14.0;
			output[2] = (1.0 * input[0] + 3.0 * input[1] + 4.0 * input[2] + 4.0 * input[3] + 3.0 * input[4]
					+ 1.0 * input[5] - 2.0 * input[6]) / 14.0;
			for (i = 3; i <= N - 4; i++) {
				output[i] = (-2.0 * (input[i - 3] + input[i + 3]) + 3.0 * (input[i - 2] + input[i + 2])
						+ 6.0 * (input[i - 1] + input[i + 1]) + 7.0 * input[i]) / 21.0;
			}
			output[N - 3] = (1.0 * input[N - 1] + 3.0 * input[N - 2] + 4.0 * input[N - 3] + 4.0 * input[N - 4]
					+ 3.0 * input[N - 5] + 1.0 * input[N - 6] - 2.0 * input[N - 7]) / 14.0;
			output[N - 2] = (5.0 * input[N - 1] + 4.0 * input[N - 2] + 3.0 * input[N - 3] + 2.0 * input[N - 4]
					+ input[N - 5] - input[N - 7]) / 14.0;
			output[N - 1] = (32.0 * input[N - 1] + 15.0 * input[N - 2] + 3.0 * input[N - 3] - 4.0 * input[N - 4]
					- 6.0 * input[N - 5] - 3.0 * input[N - 6] + 5.0 * input[N - 7]) / 42.0;
		}
		return output;
	}

	/**
	 * @description: 五点三次函数拟合平滑
	 * @param input
	 * @param N
	 */
	private static double[] cubicSmooth5(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 5) {
			for (i = 0; i <= N - 1; i++)
				output[i] = input[i];
		} else {
			output[0] = input[0];
			// output[0] = (69.0 * input[0] + 4.0 * input[1] - 6.0 * input[2] + 4.0 *
			// input[3] - input[4]) / 70.0;
			output[1] = (2.0 * input[0] + 27.0 * input[1] + 12.0 * input[2] - 8.0 * input[3] + 2.0 * input[4]) / 35.0;
			for (i = 2; i <= N - 3; i++) {
				output[i] = (-3.0 * (input[i - 2] + input[i + 2]) + 12.0 * (input[i - 1] + input[i + 1])
						+ 17.0 * input[i]) / 35.0;
			}
			output[N - 2] = (2.0 * input[N - 5] - 8.0 * input[N - 4] + 12.0 * input[N - 3] + 27.0 * input[N - 2]
					+ 2.0 * input[N - 1]) / 35.0;
			output[N - 1] = (-input[N - 5] + 4.0 * input[N - 4] - 6.0 * input[N - 3] + 4.0 * input[N - 2]
					+ 69.0 * input[N - 1]) / 70.0;
		}
		return output;
	}

	/**
	 * @description: 七点三次函数拟合平滑
	 * @param input
	 * @param N
	 * @return
	 */
	private static double[] cubicSmooth7(double[] input, int N) {
		double[] output = new double[N];
		int i;
		if (N < 7) {
			for (i = 0; i <= N - 1; i++) {
				output[i] = input[i];
			}
		} else {
			output[0] = input[0];
			// output[0] = (39.0 * input[0] + 8.0 * input[1] - 4.0 * input[2] - 4.0 *
			// input[3] + 1.0 * input[4] + 4.0 * input[5] - 2.0 * input[6]) / 42.0;
			output[1] = (8.0 * input[0] + 19.0 * input[1] + 16.0 * input[2] + 6.0 * input[3] - 4.0 * input[4]
					- 7.0 * input[5] + 4.0 * input[6]) / 42.0;
			output[2] = (-4.0 * input[0] + 16.0 * input[1] + 19.0 * input[2] + 12.0 * input[3] + 2.0 * input[4]
					- 4.0 * input[5] + 1.0 * input[6]) / 42.0;
			for (i = 3; i <= N - 4; i++) {
				output[i] = (-2.0 * (input[i - 3] + input[i + 3]) + 3.0 * (input[i - 2] + input[i + 2])
						+ 6.0 * (input[i - 1] + input[i + 1]) + 7.0 * input[i]) / 21.0;
			}
			output[N - 3] = (-4.0 * input[N - 1] + 16.0 * input[N - 2] + 19.0 * input[N - 3] + 12.0 * input[N - 4]
					+ 2.0 * input[N - 5] - 4.0 * input[N - 6] + 1.0 * input[N - 7]) / 42.0;
			output[N - 2] = (8.0 * input[N - 1] + 19.0 * input[N - 2] + 16.0 * input[N - 3] + 6.0 * input[N - 4]
					- 4.0 * input[N - 5] - 7.0 * input[N - 6] + 4.0 * input[N - 7]) / 42.0;
			output[N - 1] = (39.0 * input[N - 1] + 8.0 * input[N - 2] - 4.0 * input[N - 3] - 4.0 * input[N - 4]
					+ 1.0 * input[N - 5] + 4.0 * input[N - 6] - 2.0 * input[N - 7]) / 42.0;
		}
		return output;
	}
}
