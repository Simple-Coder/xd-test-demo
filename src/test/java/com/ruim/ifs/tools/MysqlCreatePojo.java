
package com.ruim.ifs.tools;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 适用于Oracle创建pojo
 * 
 * @author peng.ning
 * 
 */
public class MysqlCreatePojo {

	private Connection conn = null;
	private String url = "jdbc:mysql://localhost:3306/snow_new?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false&useSSL=false";
	private String userName = "snow_new";
	private String userPwd = "snow_new";
	private String[] charTypes = { "varchar", "char" };
	private String[] intTypes = { "int", "integer" };
	private String[] bigDecimalTypes = { "decimal", "numeric" };
	private String[] dateTypes = { "date", "datetime" };
	private boolean dataSetIsSize = false;// dataset是否产生size属性
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private int genType =1;// 生成po、dataset,1只生成po，2生成dataset
	private int isAllTable = 2;// 所有表0，全部，2 指定开头表
	private String[] tableStart = null;
	private ArrayList<String> tables = new ArrayList<String>();// 生成的表名
	private String pojopack = "com.ruimin.ifs.batch.bean.po";
	private String filePath = "/Users/ningpeng/Documents/workspace/ifs/ifs-all/ifs-snowweb/src/main/java/com/ruimin/ifs/batch/bean/po/";
	private String datasetFilePath = "/Users/ningpeng/Documents/workspace/ifs/ifs-all/ifs-snowweb/src/main/java/com/ruimin/ifs/batch/dataset/";

	// 连接数据库
	public MysqlCreatePojo() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Properties prop = new Properties();
		prop.setProperty("user", userName);
		prop.setProperty("password", userPwd);
		conn = DriverManager.getConnection(url, prop);

		// 等于2,
		 tableStart = new String[] {"IFS_BAT_PROC_LOG"};
	}

	// 获取库中所有用户表
	private ArrayList<String> getDataTables() throws Exception {
		ArrayList<String> tmpList = new ArrayList<String>();
		String sql = "select table_name from information_schema.tables where TABLE_SCHEMA='" + userName + "'";
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
		String sql = "select table_name from information_schema.tables where TABLE_SCHEMA='" + userName + "'";
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
			String reString = this.getTableString(tables.get(i));
			// 写入文件
			File file = FileUtils.getFile(filePath, getTableOrColumn(tables.get(i), 1) + ".java");
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
			String reString = this.getTableDataSetString(tables.get(i));
			String tname = getTableOrColumn(tables.get(i), 1) + ".dtst";
			// 写入文件
			File file = FileUtils.getFile(datasetFilePath, tname);
			SaveFile.writeFile(file, reString);
		}
	}

	private String getTableDataSetString(String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tableCommt = this.getTableCommt(tableName);
		// 列详细
		Map<String, ColumnDetailVo> columDetMap = getColumnDetail(tableName);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append(
				"<Data xmlns=\"http://www.rmitec.cn/dtst-mapper\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.rmitec.cn/dtst-mapper http://www.rmitec.cn/dtst-mapper\">\n");
		sb.append("   <Define desc=\"" + tableCommt + "\" type=\"call\" pagesize=\"10\" log=\"false\" flowid=\"请填写实现action\" expmaxrcd=\"100\" flowctxid=\"\" flowretid=\"\" />\n");
		sb.append("   <Fields>\n");
		for (Entry<String, ColumnDetailVo> entry : columDetMap.entrySet()) {
			sb.append("      ");
			String colNm = entry.getKey();
			ColumnDetailVo detail = entry.getValue();
			String cname = getTableOrColumn(colNm, 0);
			String comm = detail.getComment();
			Class<?> javaType = detail.getJavaType();
			if (javaType == Integer.class) {
				sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\" datatype=\"int\" xpath=\"/" + cname + "\" />\n");
			} else if (javaType == BigDecimal.class) {
				Integer scal = detail.getScaleLen();
				if (scal == null || scal == 0) {
					sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\" datatype=\"int\" xpath=\"/" + cname + "\" />\n");
				} else {
					sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\"  edittype=\"textbox\" datatype=\"number\" scale=\"" + scal + "\" xpath=\"/" + cname + "\" />\n");
				}
			} else if (javaType == Date.class) {
				sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"datebox\" datatype=\"date\" xpath=\"/" + cname + "\" />\n");
			} else {
				boolean isTm = false;
				if (StringUtils.equalsIgnoreCase(detail.getDataType(), "char")) {
					if (detail.getColumnLen() == 8) {
						isTm = true;
						sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"datebox\" datatype=\"date\" xpath=\"/" + cname + "\" />\n");
					} else if (detail.getColumnLen() == 14) {
						isTm = true;
						sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"datetimebox\" datatype=\"timestamp\" xpath=\"/" + cname + "\" />\n");
					}
				}
				if (!isTm) {
					if (dataSetIsSize) {
						sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\" datatype=\"string\" xpath=\"/" + cname + "\" />\n");
					} else {
						sb.append("<Field id=\"" + cname + "\" desc=\"" + comm + "\" edittype=\"textbox\" datatype=\"string\" xpath=\"/" + cname + "\" />\n");
					}
				}
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

	private String getTableString(String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		StringBuilder getset = new StringBuilder();
		String tname = getTableOrColumn(tableName, 1);
		sb.append("package " + pojopack + ";\n\n");
		sb.append("import com.ruimin.ifs.rql.annotation.Id;\n");
		sb.append("import com.ruimin.ifs.rql.annotation.Table;\n");
		String tableCommt = this.getTableCommt(tableName);
		sb.append("/**\n");
		sb.append(" * @deprecated:" + tableCommt + "\n");
		sb.append(" * 创建日期:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
		sb.append(" */\n");
		// 列详细
		Map<String, ColumnDetailVo> columDetMap = getColumnDetail(tableName);

		sb.append("@Table(\"" + tableName + "\")\n");
		sb.append("public class " + tname + "{\n\n");
		for (Entry<String, ColumnDetailVo> entry : columDetMap.entrySet()) {
			String colNm = entry.getKey();
			ColumnDetailVo detail = entry.getValue();
			String cname = getTableOrColumn(colNm, 0);
			String comm = detail.getComment();
			sb.append("   /**\n");
			if (StringUtils.isNotBlank(comm)) {
				sb.append("      *" + comm + " \n");
			}
			sb.append("      *数据类型：" + detail.getDbShowType() + " \n");
			sb.append("      *是否必填:" + detail.isNotNull() + " \n");
			sb.append("   **/\n");
			if (detail.isPk()) {
				sb.append("   @Id\n");
			}
			String jtype = ClassUtils.getPackageName(detail.getJavaType()) + "." + ClassUtils.getShortClassName(detail.getJavaType());
			sb.append("   private " + jtype + " " + cname + ";\n");

			getset.append(this.createGetAndSetMethod(jtype, cname, comm));
		}
		sb.append("\n");
		sb.append("  //get和set方法\n");
		sb.append(getset.toString());
		sb.append("}");
		return sb.toString();
	}

	private Map<String, ColumnDetailVo> getColumnDetail(String tableName) throws Exception {
		Map<String, ColumnDetailVo> columnMap = new LinkedHashMap<String, ColumnDetailVo>();
		String sql = "select * from information_schema.columns where table_schema ='" + userName + "'  and table_name = '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()) {
			ColumnDetailVo vo = new ColumnDetailVo();
			vo.setCloumnName(rs.getString("COLUMN_NAME"));
			String isNullAble = rs.getString("IS_NULLABLE");
			if (StringUtils.equalsIgnoreCase(isNullAble, "NO")) {
				vo.setNotNull(true);
			}
			vo.setDataType(rs.getString("DATA_TYPE"));
			if (ArrayUtils.contains(charTypes, vo.getDataType().toLowerCase())) {
				vo.setColumnLen(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
				vo.setJavaType(String.class);
			} else if (ArrayUtils.contains(bigDecimalTypes, vo.getDataType().toLowerCase())) {
				vo.setColumnLen(rs.getInt("NUMERIC_PRECISION"));
				vo.setScaleLen(rs.getInt("NUMERIC_SCALE"));
				vo.setJavaType(BigDecimal.class);
				if (rs.getInt("NUMERIC_SCALE") == 0) {
					vo.setJavaType(Integer.class);
				}

			} else if (ArrayUtils.contains(intTypes, vo.getDataType().toLowerCase())) {
				vo.setJavaType(Integer.class);
			} else if (ArrayUtils.contains(dateTypes, vo.getDataType().toLowerCase())) {
				vo.setJavaType(Date.class);
			} else {
				vo.setJavaType(String.class);
			}
			vo.setDbShowType(rs.getString("COLUMN_TYPE"));

			String keyFlg = rs.getString("COLUMN_KEY");
			if (StringUtils.equalsIgnoreCase(keyFlg, "PRI")) {
				vo.setPk(true);
			}
			vo.setComment(StringUtils.defaultString(rs.getString("COLUMN_COMMENT")));

			columnMap.put(vo.getCloumnName(), vo);
		}
		this.closeConn(conn, ps, rs);
		return columnMap;
	}

	private String getTableCommt(String tableName) throws Exception {
		String sql = "select * from information_schema.TABLES where TABLE_SCHEMA='" + userName + "' and TABLE_NAME= '" + tableName + "'";
		conn = getConn();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		String tc = "";
		while (rs.next()) {
			tc = rs.getString("TABLE_COMMENT");
			break;
		}

		this.closeConn(conn, ps, rs);
		return StringUtils.defaultString(tc);
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
			MysqlCreatePojo cp = new MysqlCreatePojo();
			if (cp.genType == 0 || cp.genType == 1) {
				System.out.println("===========创建pojo===================");
				cp.getTablePojo();
			}
			if (cp.genType == 0 || cp.genType == 2) {
				System.out.println("==========创建dataset=================");
				cp.getTableDataSet();
			}
			System.out.println("=================end======================");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
