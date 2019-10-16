
package com.ruim.ifs.tools;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * 适用于Oracle创建pojo
 * 
 * @author peng.ning
 * 
 */
public class OracleCreatePojo {

	private Connection conn = null;
	private String url = "jdbc:oracle:thin:@192.168.23.196:1521:xe";
	private String userName = "hmms";
	private String userPwd = "hmms";
	private String datasetPkNm = "market";
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private ResultSetMetaData rm = null;
	private int genType = 2;// 生成po、dataset,1只生成po，2生成dataset
	private int isAllTable = 0;// 所有表 1，指定表，2 指定开头表
	private String[] tableStart = null;
	private ArrayList<String> tables = new ArrayList<String>();// 生成的表名
	private String pojopack = "com.ruimin." + userName + ".po";
	private String filePath = "/Users/ningpeng/Documents/workspace/bms/hmms-common/src/com/ruimin/hmms/po/";
	private String datasetFilePath = "/Users/ningpeng/Documents/workspace/bms/msmg/com.ruimin.ms." + datasetPkNm + "/com/ruimin/ms/" + datasetPkNm + "/dataset/";

	// 连接数据库
	public OracleCreatePojo() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Properties prop = new Properties();
		prop.setProperty("user", userName);
		prop.setProperty("password", userPwd);
		conn = DriverManager.getConnection(url, prop);
		isAllTable = 1;// 0查询库中所有用户表 1指定表名
		// 等于1放入需要生成表
		tables.add("EM_RANDOM_PRIZE_CFG");/// 在此处放入自定义表，并将isAllTable修改为1
		// tables.add("EM_MAIN_EXT_INF");
		// tables.add("EM_TRIGGER_CFG");
		/*
		 * tables.add("COND_DIST_DET_INF"); tables.add("COND_DIST_INF");
		 * tables.add("DIST_RANGE_CFG"); tables.add("DRAW_PART_LOG");
		 * tables.add("EM_EXPLAIN_INF"); tables.add("EM_MAIN_EXT_INF");
		 * tables.add("EM_MAIN_INF"); tables.add("EM_MAIN_OPR_LOG");
		 * tables.add("EM_PAGE_CTL"); tables.add("EM_PART_LOG");
		 * tables.add("EM_RED_PACKET"); tables.add("EM_ROLL_GROUP_INF");
		 * tables.add("EM_TRANS_TXN"); tables.add("EM_TRIGGER_CFG");
		 * tables.add("EM_USE_CFG"); tables.add("MEMBER_INF");
		 * tables.add("RANDOM_DIST_INF"); tables.add("ROLL_GROUP_DET");
		 * tables.add("ROLL_GROUP_INF"); tables.add("SERVER_CALL_LOG");
		 * tables.add("STAT_DIST_INF"); tables.add("SYS_PAGE_CFG");
		 */
		// 等于2,
		// tableStart = new String[] { ,"EAS_BANK_EACCT_TRANS_TXN"};
	}

	// 获取库中所有用户表
	private ArrayList<String> getDataTables() throws Exception {
		ArrayList<String> tmpList = new ArrayList<String>();
		String sql = "select * from user_tab_comments";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			tmpList.add(rs.getString(1));
		}
		this.closeConn(conn, ps, rs);
		return tmpList;
	}

	private ArrayList<String> getDataTablesByStart() throws Exception {
		ArrayList<String> tmpList = new ArrayList<String>();
		String sql = "select * from user_tab_comments";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String tname = rs.getString(1);
			for (String tmp : tableStart) {
				if (tname.toLowerCase().startsWith(tmp.toLowerCase())) {
					tmpList.add(tname);
					break;
				}
			}
		}
		this.closeConn(conn, ps, rs);
		return tmpList;
	}

	private Connection getConn() throws Exception {
		if (conn == null) {
			conn = DriverManager.getConnection(url, userName, userPwd);
		}
		return conn;
	}

	private void closeConn(Connection con, PreparedStatement ps, ResultSet rs) throws Exception {
		if (rs != null) {
			rs.close();
			rs = null;
		}
		if (ps != null) {
			ps.close();
			ps = null;
		}
		if (con != null) {
			con.close();
			conn = null;
		}
	}

	private void getTablePojo() throws Exception {
		if (isAllTable == 0) {
			tables = this.getDataTables();
		} else if (isAllTable == 2) {
			tables = this.getDataTablesByStart();
		}
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String reString = this.getTableString(sql, tables.get(i));
			// 写入文件
			File file = FileUtils.getFile(filePath,getTableOrColumn(tables.get(i), 1) + ".java");
			SaveFile.writeFile(file, reString);
		}
	}

	private void getTableDataSet() throws Exception {
		if (isAllTable == 0) {
			tables = this.getDataTables();
		} else if (isAllTable == 2) {
			tables = this.getDataTablesByStart();
		}
		for (int i = 0; i < tables.size(); i++) {
			String sql = "select * from " + tables.get(i);
			String reString = this.getTableDataSetString(sql, tables.get(i));
			String tname = getTableOrColumn(tables.get(i), 1) + ".dtst";
			// 写入文件
			File file = FileUtils.getFile(datasetFilePath,tname);
			SaveFile.writeFile(file, reString);
		}
	}

	private String getTableDataSetString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tableCommt = this.getTableCommt(tableName);
		Map<String, Integer> scalMap = getColumnScal(tableName);
		Map<String, String> commtMap = getColumnCommt(tableName);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<Data xmlns=\"http://www.rmitec.cn/dtst-mapper\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.rmitec.cn/dtst-mapper http://www.rmitec.cn/dtst-mapper\">\n");
		sb.append("   <Define desc=\"" + tableCommt + "\" type=\"call\" pagesize=\"10\" log=\"false\" flowid=\"ACTION\" expmaxrcd=\"100\" flowctxid=\"\" flowretid=\"\" />\n");
		sb.append("   <Fields>\n");
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		for (int i = 1; i <= rm.getColumnCount(); i++) {
			String colNm = rm.getColumnName(i);
			String cname = getTableOrColumn(colNm, 0);
			String comm = commtMap.get(colNm) == null ? "" : commtMap.get(colNm);
			int size = rm.getColumnDisplaySize(i);
			if (rm.getColumnType(i) == Types.INTEGER) {
				sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\"  datatype=\"int\" xpath=\"/" + cname + "\" />\n");
			} else if (rm.getColumnType(i) == Types.NUMERIC || rm.getColumnType(i) == Types.DOUBLE || rm.getColumnType(i) == Types.FLOAT) {
				Integer scal = scalMap.get(colNm);
				if (scal == null || scal == 0) {
					sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\"  datatype=\"int\" xpath=\"/" + cname + "\" />\n");
				} else {
					sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\"  datatype=\"number\" scale=\"" + scal + "\" xpath=\"/" + cname + "\" />\n");
				}
			} else if (rm.getColumnType(i) == Types.DATE || rm.getColumnTypeName(i).equalsIgnoreCase("Date")) {
				sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"datebox\"  datatype=\"date\" xpath=\"/" + cname + "\" />\n");
			} else if (rm.getColumnType(i) == Types.TIMESTAMP && rm.getColumnTypeName(i).equalsIgnoreCase("TIMESTAMP")) {
				sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"datetimebox\"  datatype=\"timestamp\"  xpath=\"/" + cname + "\" />\n");
			} else {
				sb.append("      <Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\"  datatype=\"string\" size=\"" + size + "\" xpath=\"/" + cname + "\" />\n");
			}
		}
		sb.append("   </Fields>\n");
		sb.append("   <Commands>\n");
		sb.append("      <Command id=\"btnAdd\" desc=\"新增\" type=\"addrecord\" url=\"#\" txn=\"\" icon=\"fa fa-plus\"/>\n");
		sb.append("      <Command id=\"btAddOk\" desc=\"确定\" type=\"asyncsubmit\" flowid=\"ACTION\" url=\"#\" txn=\"\" icon=\"fa fa-plus\"/>\n");
		sb.append("   </Commands>\n</Data>");
		this.closeConn(conn, ps, rs);
		return sb.toString();
	}

	private String getTableString(String sql, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("package " + pojopack + ";\n\n");
		sb.append("import com.ruimin.ifs.rql.annotation.Id;\n");
		sb.append("import com.ruimin.ifs.rql.annotation.Table;\n");
		String tableCommt = this.getTableCommt(tableName);
		sb.append("/**\n");
		sb.append(" * @deprecated:" + tableCommt + "\n");
		sb.append(" * 创建日期:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
		sb.append(" */\n");

		Set<String> pks = getTabelPks(tableName);
		Map<String, Integer> scalMap = getColumnScal(tableName);
		Map<String, String> commtMap = getColumnCommt(tableName);

		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		rm = rs.getMetaData();
		sb.append("@Table(\"" + tableName + "\")\n");
		sb.append("public class " + tname + "{\n\n");

		for (int i = 1; i <= rm.getColumnCount(); i++) {
			String colNm = rm.getColumnName(i);
			String cname = getTableOrColumn(colNm, 0);
			if (pks.contains(colNm)) {
				sb.append("   @Id\n");
			}
			String comm = commtMap.get(colNm);
			if (comm != null && comm.length() > 0) {
				sb.append("   /**" + comm + " **/\n");
			}
			if (rm.getColumnType(i) == Types.INTEGER) {
				sb.append("   private Integer " + cname + ";\n");
			} else if (rm.getColumnType(i) == Types.NUMERIC || rm.getColumnType(i) == Types.DOUBLE || rm.getColumnType(i) == Types.FLOAT) {
				Integer scal = scalMap.get(colNm);
				if (scal == null || scal == 0) {
					sb.append("   private Integer " + cname + ";\n");
				} else {
					sb.append("   private java.math.BigDecimal " + cname + ";\n");
				}
			} else if (rm.getColumnType(i) == Types.DATE || rm.getColumnTypeName(i).equalsIgnoreCase("Date")) {
				sb.append("   private java.util.Date " + cname + ";\n");
			} else if (rm.getColumnType(i) == Types.TIMESTAMP && rm.getColumnTypeName(i).equalsIgnoreCase("TIMESTAMP")) {
				sb.append("   private java.sql.Timestamp " + cname + ";\n");
			} else {
				sb.append("   private String " + cname + ";\n");
			}
		}

		sb.append("\n");
		sb.append("  //get和set方法\n");
		for (int i = 1; i <= rm.getColumnCount(); i++) {
			String colNm = rm.getColumnName(i);
			String cname = getTableOrColumn(colNm, 0);
			String comm = commtMap.get(colNm);
			if (rm.getColumnType(i) == Types.INTEGER) {
				sb.append(this.createGetAndSetMethod("Integer", cname, comm));
			} else if (rm.getColumnType(i) == Types.NUMERIC || rm.getColumnType(i) == Types.DOUBLE || rm.getColumnType(i) == Types.FLOAT) {
				Integer scal = scalMap.get(colNm);
				if (scal == null || scal == 0) {
					sb.append(this.createGetAndSetMethod("Integer", cname, comm));
				} else {
					sb.append(this.createGetAndSetMethod("java.math.BigDecimal", cname, comm));
				}
			} else if (rm.getColumnType(i) == Types.DATE || rm.getColumnTypeName(i).equalsIgnoreCase("Date")) {
				sb.append(this.createGetAndSetMethod("java.util.Date", cname, comm));
			} else if (rm.getColumnType(i) == Types.TIMESTAMP && rm.getColumnTypeName(i).equalsIgnoreCase("TIMESTAMP")) {
				sb.append(this.createGetAndSetMethod("java.sql.Timestamp", cname, comm));
			} else {
				sb.append(this.createGetAndSetMethod("String", cname, comm));
			}
		}
		sb.append("}");
		this.closeConn(conn, ps, rs);
		return sb.toString();
	}

	private Map<String, Integer> getColumnScal(String tableName) throws Exception {
		Map<String, Integer> colScalMap = new HashMap<String, Integer>();
		String sql = "select * from col where tname = '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String colNm = rs.getString("CNAME");
			Integer scal = rs.getInt("SCALE");
			colScalMap.put(colNm, scal);
		}
		this.closeConn(conn, ps, rs);
		return colScalMap;
	}

	private Map<String, String> getColumnCommt(String tableName) throws Exception {
		Map<String, String> colScalMap = new HashMap<String, String>();
		String sql = "select * from user_col_comments where table_name = '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String colNm = rs.getString("COLUMN_NAME");
			String comm = rs.getString("COMMENTS");
			colScalMap.put(colNm, comm);
		}
		this.closeConn(conn, ps, rs);
		return colScalMap;
	}

	private String getTableCommt(String tableName) throws Exception {
		String sql = "select * from user_tab_comments where table_name = '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		String tc = "";
		while (rs.next()) {
			tc = rs.getString("COMMENTS");
			break;
		}
		this.closeConn(conn, ps, rs);
		return tc;
	}

	private Set<String> getTabelPks(String tableName) throws Exception {
		Set<String> pkSet = new HashSet<String>();
		String sql = "select cu.COLUMN_NAME from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			String pk = rs.getString(1);
			pkSet.add(pk);
		}
		this.closeConn(conn, ps, rs);
		return pkSet;
	}

	private String createGetAndSetMethod(String type, String colsName, String cmmt) {
		StringBuffer result = new StringBuffer();
		String tmp1 = colsName.substring(0, 1).toUpperCase();
		String tmp2 = colsName.substring(1, colsName.length());
		String tmp3 = colsName.substring(0, 1).toLowerCase() + tmp2;
		if (cmmt != null) {
			result.append("   /**\n");
			result.append("    *" + cmmt + "\n");
			result.append("    *@return " + type + "\n");
			result.append("    */\n");
		}
		result.append("   public " + type + " get" + tmp1 + tmp2 + "(){\n");
		result.append("      return " + colsName + ";\n");
		result.append("   }\n");
		result.append("\n");
		if (cmmt != null) {
			result.append("   /**\n");
			result.append("    *" + cmmt + "\n");
			result.append("    *@param " + tmp3 + "\n");
			result.append("    */\n");
		}
		result.append("   public void set" + tmp1 + tmp2 + "(" + type + " " + tmp3 + "){\n");
		result.append("      this." + colsName + " = " + tmp3 + ";\n");
		result.append("   }\n");
		result.append("\n");
		return result.toString();
	}

	// 对表名和列名进行转换 type==1为表名 否则为列名
	private String getTableOrColumn(String oldname, int type) {
		String tmp = oldname.toLowerCase();
		String newStr = "";
		if (type == 1) {
			String[] tbs = tmp.split("_");
			for (int i = 0; i < tbs.length; i++) {
				String strat = tbs[i].substring(0, 1).toUpperCase();
				String end = tbs[i].substring(1, tbs[i].length());
				newStr += strat + end;
			}
		} else {
			String[] cols = tmp.split("_");
			for (int i = 0; i < cols.length; i++) {
				if (i == 0) {
					if (cols[i].trim().length() == 1) {
						newStr += cols[i].toUpperCase();
					} else {
						newStr += cols[i];
					}
				} else {
					String strat = cols[i].substring(0, 1).toUpperCase();
					String end = cols[i].substring(1, cols[i].length());
					newStr += strat + end;
				}
			}
		}
		return newStr;

	}

	public static void main(String[] args) {
		try {
			System.out.println("==============start================");
			OracleCreatePojo cp = new OracleCreatePojo();
			if (cp.genType == 0 || cp.genType == 1) {
				System.out.println("===========创建pojo===================");
				cp.getTablePojo();
			}
			 if (cp.genType==0 || cp.genType==2) {
			 System.out.println("==========创建dataset=================");
			 cp.getTableDataSet();
			 }
			System.out.println("=================end======================");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
