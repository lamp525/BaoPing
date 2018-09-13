package com.ncgroup.bp;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @Description: �������ݴ�����ȡ
 * @author: Lin
 * @version:
 * @date: 2018��9��12�� ����4:37:57
 */
public class DataSource {
	private static int _count1M = 0;
	private static int _count5M = 0;
	
	/**
	 * 5�������ݴ���
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
	 * 1�������ݴ���
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
		if (count > _count5M) {
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
			String sql = "SELECT COUNT(1) FROM [dbo].[BP_5MResult_SZ] Where [����] = '" + today + "'";
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
