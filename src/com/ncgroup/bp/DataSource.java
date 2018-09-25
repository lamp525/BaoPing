package com.ncgroup.bp;

import java.sql.*;
import java.util.ArrayList;

/**
 * @description: �������ݴ�����ȡ
 * @author: Lin
 * @created: 2018��9��13�� ����4:46:01
 * @version: 1.0
 */
public class DataSource {
	private static int _count1M = 0;
	private static int _count5M = 0;
	private static final int ROW_COUNT = 48;

	/**
	 * 5�������ݴ���
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
	 * @description: �̺�1�������ݴ���
	 */
	public static void closeHourProc1M() {
		try {
			String today = DateHelper.now("yyyy-MM-dd");

			/* �������1�������ݣ��������30�յļ�Ȩƽ��ֵ */
			String sql = "EXEC [dbo].[sp_BP_1MEnd] @CurDate = '" + today + "'";
			SqlHelper.execSql(sql);
		} catch (Exception e) {
			Log.error("closeHourProc1M", e);
		}
	}

	/**
	 * @description: �̺�5�������ݴ���
	 */
	public static void closeHourProc5M() {
		try {
			String today = DateHelper.now("yyyy-MM-dd");

			/* �������5�������ݣ��������30�յļ�Ȩƽ��ֵ */
			String sql = "EXEC [dbo].[sp_BP_5MEnd] @CurDate = '" + today + "'";
			SqlHelper.execSql(sql);

			/* ƽ������ */
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
						+ indexCode + "] WHERE TradeDate = '" + today + "'";
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
							+ "_new]([TradeDate],[TradeTime],[VolumeRate],[AmountRate],[Volume],[Amount],[AccVolume],[AccAmount]) VALUES(?,?,?,?,?,?,?,?) ";
					Connection connection = SqlHelper.getConnection();
					PreparedStatement ps = connection.prepareStatement(sqlInsert);

					index = 0;
					rs.beforeFirst();
					while (rs.next()) {
						ps.setDate(1, rs.getDate(1));
						ps.setString(2, rs.getString(2));
						ps.setDouble(3, rs.getDouble(3));
						ps.setDouble(4, rs.getDouble(4));
						ps.setDouble(5, volList[index]);
						ps.setDouble(6, amountList[index]);
						ps.setDouble(7, rs.getDouble(7));
						ps.setDouble(8, rs.getDouble(8));
						ps.addBatch();
						++index;
					}
					/* ɾ���������� */
					sqlDelete = "DELETE FROM  [dbo].[BP_5MStandard_" + indexCode + "_new] WHERE TradeDate = '" + today
							+ "'";
					SqlHelper.execSql(sqlDelete);

					// ����ƽ���������
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
	 * 1�������ݴ���
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
	 * 1�������ݸ��¼��
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
	 * 5�������ݸ��¼��
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
	 * ȡ��1���������������
	 */
	public static ArrayList<String> getResult1M(String indexCode) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			String sql = " SELECT TOP(1) [TradeTime],[VolRate],[AccVolRate],[AmountRate],[AccAmountRate]  FROM [dbo].[BP_1MResult_"
					+ indexCode + "]  ORDER BY TradeTime DESC ";
			ResultSet rs = SqlHelper.getResultSet(sql);

			if (rs != null && rs.first()) {
				arr.add(rs.getString(1)); // ʱ��
				arr.add(rs.getString(2)); // ����
				arr.add(rs.getString(3)); // �ۼ�����
				arr.add(rs.getString(4)); // ����
				arr.add(rs.getString(5)); // �ۼƽ���
			}
		} catch (Exception e) {
			Log.error("getResult1M", e);
		}

		return arr;
	}

	/**
	 * ȡ��5��������������Ϣ
	 */
	public static ArrayList<String> getResult5M(String indexCode) {
		ArrayList<String> arr = new ArrayList<String>();

		try {
			String sql = " SELECT TOP(1) [ʱ��],[��׼ֵ_����],[��׼ֵ_�ۼƽ���] FROM [dbo].[BP_5MResult_" + indexCode
					+ "]  ORDER BY  [ʱ��] DESC ";
			ResultSet rs = SqlHelper.getResultSet(sql);

			if (rs != null && rs.first()) {
				arr.add(rs.getString(1));// ʱ��
				arr.add(rs.getString(2)); // ����
				arr.add(rs.getString(3)); // �ۼƽ���
			}
		} catch (Exception e) {
			Log.error("getResult5M", e);
		}

		return arr;
	}

	/**
	 * ȡ�õ���1���ӽ����¼��
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
	 * ȡ�õ���5���ӽ����¼��
	 */
	private static int getResultCount5M() {
		int count = 0;

		try {
			String today = DateHelper.now("yyyy-MM-dd");
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT SUM(aa) FROM( ");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_SZ] Where [����] =");
			sb.append("'" + today + "' ");
			sb.append(" UNION ALL");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_ZX] Where [����] =");
			sb.append("'" + today + "' ");
			sb.append(" UNION ALL ");
			sb.append(" SELECT aa = COUNT(1)  FROM [FinancialCenter].[dbo].[BP_5MResult_CY] Where [����] =");
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
