package com.ncgroup.bp;

import java.sql.*;
import java.util.ArrayList;

/**
 * @description: 播报数据处理、获取
 * @author: Lin
 * @created: 2018年9月13日 下午4:46:01
 * @version: 1.0
 */
public class DataSource {
	private static int _count1M = 0;
	private static int _count5M = 0;
	private static final int ROW_COUNT = 48;

	/**
	 * @description: 1分钟数据处理
	 *
	 */
	public static void dataProc1M() {
		try {
			String sql = "EXEC [dbo].[sp_BP_1MStart]";
			SqlHelper.execSql(sql);
		} catch (Exception e) {
			Log.error("dataProc1M", e);
		}
	}

	/**
	 * @description: 5分钟数据处理
	 *
	 */
	public static void dataProc5M() {
		try {
			String sql = "EXEC [dbo].[sp_BP_5MStart]";
			SqlHelper.execSql(sql);
		} catch (Exception e) {
			Log.error("dataProc5M", e);
		}
	}

	/**
	 * @description: 1分钟数据更新检查
	 * @return
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
	 * @description: 5分钟数据更新检查
	 * @return
	 */
	public static Boolean checkUpdate5M() {
		Boolean result = false;
		int count = getResultCount5M();
		if (count > _count5M && count % 3 == 0) {
			result = true;
			_count5M = count;
		}
		return result;
	}

	/**
	 * @description: 取得1分钟量、金额数据
	 * @param indexCode
	 * @return
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
	 * @description: 取得5分钟量、金额播报信息
	 * @param indexCode
	 * @return
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
	 * @description: 盘后1分钟数据处理
	 * @param date [yyyy-MM-dd]
	 */
	public static void closeHourProc1M(String date) {
		try {
			/* 保存今日1分钟数据，并计算近30日的加权平均值 */
			String sql = "EXEC [dbo].[sp_BP_1MEnd] @CurDate = '" + date + "'";
			SqlHelper.execSql(sql);
		} catch (Exception e) {
			Log.error("closeHourProc1M", e);
		}
	}

	/**
	 * @description: 盘后5分钟数据处理
	 * @param date [yyyy-MM-dd]
	 */
	public static void closeHourProc5M(String date) {
		try {
			/* 保存今日5分钟数据，并计算近30日的加权平均值 */
			String sql = "EXEC [dbo].[sp_BP_5MEnd] @CurDate = '" + date + "'";
			SqlHelper.execSql(sql);

			/* 平滑处理 */
			String sqlSelect, sqlInsert, sqlDelete;
			ResultSet rs = null;
			double[] volList = new double[ROW_COUNT];
			double[] amountList = new double[ROW_COUNT];
			ArrayList<String> indexList = new ArrayList<String>();
			indexList.add("SZ");
			indexList.add("ZX");
			indexList.add("CY");
			for (String indexCode : indexList) {
				sqlSelect = "SELECT TradeDate,TradeTime,VolumeRate,AmountRate,Volume,Amount,AccVolume,AccAmount FROM [dbo].[BP_5MStandard_"
						+ indexCode + "] WHERE TradeDate = '" + date + "'";
				rs = SqlHelper.getResultSet(sqlSelect);
				int index = 0;
				while (rs.next()) {
					volList[index] = rs.getDouble(5);
					amountList[index] = rs.getDouble(6);
					++index;
				}
				if (volList.length == ROW_COUNT && amountList.length == ROW_COUNT) {
					volList = LeastSquareSmooth.sevenPointsSmooth(volList);
					amountList = LeastSquareSmooth.sevenPointsSmooth(amountList);
					sqlInsert = "INSERT INTO [dbo].[BP_5MStandard_" + indexCode
							+ "]([TradeDate],[TradeTime],[VolumeRate],[AmountRate],[Volume],[Amount],[AccVolume],[AccAmount]) VALUES(?,?,?,?,?,?,?,?) ";
					Connection connection = SqlHelper.getConnection();
					PreparedStatement ps = connection.prepareStatement(sqlInsert);

					index = 0;
					rs.beforeFirst();
					while (rs.next()) {
						ps.setDate(1, rs.getDate(1)); // 日期
						ps.setString(2, rs.getString(2)); // 时间
						ps.setDouble(3, rs.getDouble(3)); // 量分配比例
						ps.setDouble(4, rs.getDouble(4)); // 金额分配比例
						ps.setDouble(5, volList[index]); // 量
						ps.setDouble(6, amountList[index]); // 金额
						ps.setDouble(7, rs.getDouble(7)); // 累计量
						ps.setDouble(8, rs.getDouble(8)); // 累计金额
						ps.addBatch();
						++index;
					}
					/* 删除已有数据 */
					sqlDelete = "DELETE FROM  [dbo].[BP_5MStandard_" + indexCode + "] WHERE TradeDate = '" + date + "'";
					SqlHelper.execSql(sqlDelete);

					// 插入平滑后的数据
					ps.executeBatch();
					ps.close();
					connection.close();
				}
			}
		} catch (Exception e) {
			Log.error("closeHourProc5M", e);
		}
	}

	/**
	 * @description: 取得当日1分钟结果记录数
	 * @return
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
	 * @description: 取得当日5分钟结果记录数
	 * @return
	 */
	private static int getResultCount5M() {
		int count = 0;
		try {
			String today = DateHelper.now("yyyy-MM-dd");
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT SUM(aa) FROM( ");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_SZ] Where [日期] =");
			sb.append("'" + today + "' ");
			sb.append(" UNION ALL");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_ZX] Where [日期] =");
			sb.append("'" + today + "' ");
			sb.append(" UNION ALL ");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_CY] Where [日期] =");
			sb.append("'" + today + "' ");
			sb.append(" ) T");
			Object obj = SqlHelper.ExecScalar(sb.toString());
			if (obj != null) {
				count = Integer.parseUnsignedInt(obj.toString());
			}
		} catch (Exception e) {
			Log.error("getResultCount5M", e);
		}
		return count;
	}

}
