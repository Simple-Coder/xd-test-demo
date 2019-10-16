package com.ruimin.ifs.mng.comp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.util.file.DirectoryUtil;
import com.ruimin.ifs.framework.dataobject.QueryParamBean;
@ActionResource
public class SnowManageAction {

	private static List<Object[]> icons = null;

	@Action
	@SnowDoc(desc = "查询所有图标")
	public List<Object[]> listIcons(QueryParamBean queryBean) throws SnowException {
		String iconName = queryBean.getParameter("qName");
		String iconCssFile = queryBean.getParameter("iconCssFile");
		String cache = queryBean.getParameter("cache");
		if ((icons == null || "false".equalsIgnoreCase(cache)) && StringUtils.isNotBlank(iconCssFile)) {
			String rootpath = DirectoryUtil.getProjectRootPath();
			int index = rootpath.indexOf("/WEB-INF/");
			String path = rootpath.substring(0, index);
			try {
				List<String> cssList = FileUtils.readLines(new File(path + iconCssFile));
				icons = new ArrayList<Object[]>();
				int i = 0;
				for (String string : cssList) {
					if (StringUtils.isNotBlank(string)) {
						boolean startFa = StringUtils.startsWith(string, ".fa-");
						int lastIndexOf = StringUtils.lastIndexOf(string, ":before");
						if(startFa && lastIndexOf>0) {
							String showNm = StringUtils.substring(string, 1,lastIndexOf);
							String name = StringUtils.substring(string, 4,lastIndexOf);
							icons.add(new Object[] { ++i, name, "fa "+showNm.toString() });
						}
					}
				}
//				Matcher m = Pattern.compile("(fa-(\\w+)):before").matcher(css);
//				int i = 0;
//				while (m.find()) {
//					icons.add(new Object[] { ++i, m.group(2), "fa " + m.group(1) });
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		List<Object[]> filteredList = null;
		if (StringUtils.isBlank(iconName)) {
			filteredList = icons;
		} else {
			filteredList = new ArrayList<Object[]>();
			for (Object[] obj : icons) {
				String name = (String) obj[1];
				if (name.indexOf(iconName) > -1) {
					filteredList.add(obj);
				}
			}
		}

		return filteredList;
	}

}
