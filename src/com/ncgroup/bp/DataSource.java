package com.ncgroup.bp;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @Description: 播报数据处理、获取
 * @author: Lin
 * @version:
 * @date: 2018年9月12日 下午4:37:57
 */
public class DataSource {
	private static int _count1M = 0;
	private static int _count5M = 0;
	
	/**
	 * 5分钟数据处理
	 */
	public static void dataProc5M() {
		try {
			String sql = "EXEC [dbo].[sp_BP_5MStart]";
			SqlHelper.ExecSql(sql);
		} catch (Exception e) {
			Log.error("dataProc5M", e);
		}
	}

	/**
	 * 1分钟数据处理
	 */
	public static void dataProc1M() {
		try {
			String sql = "EXEC [dbo].[sp_BP_1MStart]";
			SqlHelper.ExecSql(sql);
		} catch (Exception e) {
			Log.error("dataProc1M", e);
		}
	}

	/**
	 * 1分钟数据更新检查
	 */
	public static Boolean checkUpdate1M() {
		Boolean result = false;

		int count = getResultCount1M();
		if (count > _count1M) {
			result = true;
			_count1M = count;
		}

		return result;
	}

	/**
	 * 5分钟数据更新检查
	 */
	public static Boolean checkUpdate5M() {
		Boolean result = false;

		int count = getResultCount5M();
		if (count > _count5M) {
			result = true;
			_count5M = count;
		}

		return result;
	}

	/**
	 * 取得1分钟量、金额数据
	 */
	public static ArrayList<String> getResult1M(String indexCode) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			String sql = " SELECT TOP(1) [TradeTime],[VolRate],[AccVolRate],[AmountRate],[AccAmountRate]  FROM [dbo].[BP_1MResult_"
					+ indexCode + "]  ORDER BY TradeTime DESC ";
			ResultSet rs = SqlHelper.getResultSet(sql);

			if (rs != null && rs.first()) {
				arr.add(rs.getString(1)); // 时间
				arr.add(rs.getString(2)); // 量比
				arr.add(rs.getString(3)); // 累计量比
				arr.add(rs.getString(4)); // 金额比
				arr.add(rs.getString(5)); // 累计金额比
			}
		} catch (Exception e) {
			Log.error("getResult1M", e);
		}

		return arr;
	}

	/**
	 * 取得5分钟量、金额播报信息
	 */
	public static ArrayList<String> getResult5M(String indexCode) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			String sql = " SELECT TOP(1) [时间],[标准值_金额比],[标准值_累计金额比] FROM [dbo].[BP_5MResult_" + indexCode
					+ "]  ORDER BY  [时间] DESC ";
			ResultSet rs = SqlHelper.getResultSet(sql);

			if (rs != null && rs.first()) {
				arr.add(rs.getString(1));// 时间
				arr.add(rs.getString(2)); // 金额比
				arr.add(rs.getString(3)); // 累计金额比
			}
		} catch (Exception e) {
			Log.error("getResult5M", e);
		}

		return arr;
	}

	/**
	 * 取得当日1分钟结果记录数
	 */
	private static int getResultCount1M() {
		int count = 0;

		try {
			String today = DateHelper.now("yyyy-MM-dd");
			String sql = "SELECT COUNT(1) FROM [dbo].[BP_1MResult_SZ] Where TradeDate = '" + today + "'";
			Object obj = SqlHelper.ExecScalar(sql);
			if (obj != null) {
				count = Integer.parseUnsignedInt(obj.toString());
			}
		} catch (Exception e) {
			Log.error("getResultCount1M", e);
		}

		return count;
	}

	/**
	 * 取得当日5分钟结果记录数
	 */
	private static int getResultCount5M() {
		int count = 0;

		try {
			String today = DateHelper.now("yyyy-MM-dd");
			String sql = "SELECT COUNT(1) FROM [dbo].[BP_5MResult_SZ] Where [日期] = '" + today + "'";
			Object obj = SqlHelper.ExecScalar(sql);
			if (obj != null) {
				count = Integer.parseUnsignedInt(obj.toString());
			}
		} catch (Exception e) {
			Log.error("getResultCount5M", e);
		}

		return count;
	}



}
