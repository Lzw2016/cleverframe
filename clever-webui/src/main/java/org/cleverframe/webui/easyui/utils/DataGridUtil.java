package org.cleverframe.webui.easyui.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cleverframe.webui.easyui.model.Column;
import org.cleverframe.webui.easyui.model.DataGrid;

/**
 * EasyUI DataGrid数据绑定工具类<br>
 * 1.根据实体类集合得到EasyUI DataGrid格式的Json数据<br>
 * 
 * @author LiZhiWei
 * @version 2015年5月31日 下午7:34:35
 */
public class DataGridUtil
{
	/** 列field属性和title属性的分隔字符串，例如："userName|用户名" */
	public final static String SEPARATE="\\|";
	
	/**
	 * 根据一行数据得到EasyUI的DataGrid模型对象<br>
	 * 1.这一行数据的格式是Map<String, E>键值对<br>
	 * 2.键集合代表列数，且键的格式是："field|title"，例如："userName|用户名"<br>
	 * */
	public static <E extends Serializable> DataGrid getDataGrid(Map<String, E> dataRow)
	{
		DataGrid dataGrid = new DataGrid(true, true, true);
		if (dataRow != null)
		{
			Set<String> columns = dataRow.keySet();
			for (String col : columns)
			{
				String[] str = col.split(SEPARATE);
				String field = str[0];
				String title = field;
				if (str.length >= 2)
				{
					title = str[1];
				}
				dataGrid.addColumn(new Column(field, title, 100, "center", "center", true, true));
			}
		}
		return dataGrid;
	}
	
	/**
	 * 根据一行数据得到EasyUI DataGrid中的Column模型集合对象<br>
	 * 1.这一行数据的格式是Map<String, E>键值对<br>
	 * 2.键集合代表列数，且键的格式是："field|title"，例如："userName|用户名"<br>
	 * */
	public static <E extends Serializable> List<Column> getColumns(Map<String, E> dataRow)
	{
		List<Column> columnList=new ArrayList<>();
		if (dataRow != null)
		{
			Set<String> columns = dataRow.keySet();
			for (String col : columns)
			{
				String[] str = col.split(SEPARATE);
				String field = str[0];
				String title = field;
				if (str.length >= 2)
				{
					title = str[1];
				}
				columnList.add(new Column(field, title, 100, "center", "center", true, true));
			}
		}
		return columnList;
	}
}
