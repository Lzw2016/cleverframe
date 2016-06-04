package org.cleverframe.webui.easyui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * EasyUI DataGrid
 * 
 * @author LiZhiWei
 * @version 2015年5月30日 下午1:43:11
 */
@JsonInclude(Include.NON_DEFAULT)
public class DataGrid implements Serializable
{
	private static final long serialVersionUID = 1L;
	/** DataGrid列配置对象 */
	private List<List<Column>> columns;
	/** 同列属性，但是这些列将会被冻结在左侧 */
	private List<List<Column>> frozenColumns;
	/** 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动 */
	private boolean fitColumns = false;
	/** 调整列的位置，可用的值有：'left','right','both'。在使用'right'的时候用户可以通过拖动右侧边缘的列标题调整列，等等 */
	private String resizeHandle = "right";
	/** 定义设置行的高度，根据该行的内容。设置为false可以提高负载性能 */
	private boolean autoRowHeight = true;
	/** 是否显示斑马线效果 */
	private boolean striped = false;
	/** 该方法类型请求远程数据 */
	private String method = "post";
	/** 如果为true，则在同一行中显示数据。设置为true可以提高加载性能 */
	private boolean nowrap = true;
	/** 指明哪一个字段是标识字段 */
	private String idField;
	/** 一个URL从远程站点请求数据 */
	private String url;
	/** 在从远程站点加载数据的时候显示提示消息 */
	private String loadMsg = "处理中，请稍候...";
	/** 如果为true，则在DataGrid控件底部显示分页工具栏 */
	private boolean pagination = false;
	/** 如果为true，则显示一个行号列 */
	private boolean rownumbers = false;
	/** 如果为true，则只允许选择一行 */
	private boolean singleSelect = false;
	/** 在启用多行选择的时候允许使用Ctrl键+鼠标点击的方式进行多选操作 */
	private boolean ctrlSelect = false;
	/** 如果为true，当用户点击行的时候该复选框就会被选中或取消选中。如果为false，当用户仅在点击该复选框的时候才会呗选中或取消 */
	private boolean checkOnSelect = true;
	/**  如果为true，单击复选框将永远选择行。如果为false，选择行将不选中复选框。 */
	private boolean selectOnCheck = true;
	/** 定义分页工具栏的位置。可用的值有：'top','bottom','both' */
	private String pagePosition = "bottom";
	/** 在设置分页属性的时候初始化页码 */
	private int pageNumber = 1;
	/** 在设置分页属性的时候初始化页面大小 */
	private int pageSize = 10;
	/** 在设置分页属性的时候 初始化页面大小选择列表 */
	private int[] pageList = { 10, 20, 30, 40, 50 };
	/** 定义哪些列可以进行排序 */
	private String sortName;
	/** 定义列的排序顺序，只能是'asc'或'desc' */
	private String sortOrder = "asc";
	/** 定义是否允许多列排序 */
	private boolean multiSort = false;
	/** 定义从服务器对数据进行排序 */
	private boolean remoteSort = true;
	/** 定义是否显示行头 */
	private boolean showHeader = true;
	/** 定义是否显示行脚 */
	private boolean showFooter = false;
	/** 滚动条的宽度(当滚动条是垂直的时候)或高度(当滚动条是水平的时候) */
	private int scrollbarSize = 18;

	public DataGrid()
	{

	}

	/**
	 * @param idField 指明哪一个字段是标识字段 
	 * @param striped 是否显示斑马线效果
	 * @param rownumbers 如果为true，则显示一个行号列。
	 * @param singleSelect 如果为true，则只允许选择一行
	 * @param pagination 如果为true，则在DataGrid控件底部显示分页工具栏
	 * @param fitColumns 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动
	 * */
	public DataGrid(String idField, boolean striped, boolean rownumbers, boolean singleSelect, boolean pagination, boolean fitColumns)
	{
		this.idField = idField;
		this.striped = striped;
		this.rownumbers = rownumbers;
		this.singleSelect = singleSelect;
		this.pagination = pagination;
		this.fitColumns = fitColumns;
	}

	/**
	 * @param striped 是否显示斑马线效果
	 * @param rownumbers 如果为true，则显示一个行号列。
	 * @param fitColumns 真正的自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动
	 * */
	public DataGrid(boolean striped, boolean rownumbers, boolean fitColumns)
	{
		this.striped = striped;
		this.rownumbers = rownumbers;
		this.fitColumns = fitColumns;
	}

	/**
	 * 增加一个列类别
	 * */
	public void addColumnGroup(List<Column> columnList)
	{
		if (this.columns == null)
		{
			this.columns = new ArrayList<List<Column>>();
		}
		this.columns.add(columnList);
	}

	/**
	 * 增加一个列类别
	 * */
	public void addColumnGroup(Column... columnArray)
	{
		if (this.columns == null)
		{
			this.columns = new ArrayList<List<Column>>();
		}
		List<Column> columnList = new ArrayList<Column>();
		for (Column column : columnArray)
		{
			columnList.add(column);
		}
		this.columns.add(columnList);
	}

	/**
	 * 增加一列到指定的列类别组中
	 * */
	public void addColumn(int groupIndex, Column coiumn)
	{
		if (this.columns == null)
		{
			this.columns = new ArrayList<List<Column>>();
		}
		if (groupIndex < 0 || groupIndex >= this.columns.size())
		{
			// groupIndex 范围超出
		}
		else
		{
			this.columns.get(groupIndex).add(coiumn);
		}
	}

	/**
	 * 增加一列到第一个列类别组中，groupIndex=0
	 * */
	public void addColumn(Column column)
	{
		if (this.columns == null)
		{
			this.columns = new ArrayList<List<Column>>();
		}
		if (this.columns.size() <= 0)
		{
			this.columns.add(new ArrayList<Column>());
		}
		this.columns.get(0).add(column);
	}

	/**
	 * 增加若干列到第一个列类别组中，groupIndex=0
	 * */
	public void addColumn(Column... coiumns)
	{
		if (this.columns == null)
		{
			this.columns = new ArrayList<List<Column>>();
		}
		if (this.columns.size() <= 0)
		{
			this.columns.add(new ArrayList<Column>());
		}
		for (Column coiumn : coiumns)
		{
			this.columns.get(0).add(coiumn);
		}
	}

	/*--------------------------------------------------------------
	 * 			getter、setter
	 * -------------------------------------------------------------*/
	public List<List<Column>> getColumns()
	{
		return columns;
	}

	public void setColumns(List<List<Column>> columns)
	{
		this.columns = columns;
	}

	public List<List<Column>> getFrozenColumns()
	{
		return frozenColumns;
	}

	public void setFrozenColumns(List<List<Column>> frozenColumns)
	{
		this.frozenColumns = frozenColumns;
	}

	public boolean isFitColumns()
	{
		return fitColumns;
	}

	public void setFitColumns(boolean fitColumns)
	{
		this.fitColumns = fitColumns;
	}

	public String getResizeHandle()
	{
		return resizeHandle;
	}

	public void setResizeHandle(String resizeHandle)
	{
		this.resizeHandle = resizeHandle;
	}

	public boolean isAutoRowHeight()
	{
		return autoRowHeight;
	}

	public void setAutoRowHeight(boolean autoRowHeight)
	{
		this.autoRowHeight = autoRowHeight;
	}

	public boolean isStriped()
	{
		return striped;
	}

	public void setStriped(boolean striped)
	{
		this.striped = striped;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public boolean isNowrap()
	{
		return nowrap;
	}

	public void setNowrap(boolean nowrap)
	{
		this.nowrap = nowrap;
	}

	public String getIdField()
	{
		return idField;
	}

	public void setIdField(String idField)
	{
		this.idField = idField;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getLoadMsg()
	{
		return loadMsg;
	}

	public void setLoadMsg(String loadMsg)
	{
		this.loadMsg = loadMsg;
	}

	public boolean isPagination()
	{
		return pagination;
	}

	public void setPagination(boolean pagination)
	{
		this.pagination = pagination;
	}

	public boolean isRownumbers()
	{
		return rownumbers;
	}

	public void setRownumbers(boolean rownumbers)
	{
		this.rownumbers = rownumbers;
	}

	public boolean isSingleSelect()
	{
		return singleSelect;
	}

	public void setSingleSelect(boolean singleSelect)
	{
		this.singleSelect = singleSelect;
	}

	public boolean isCtrlSelect()
	{
		return ctrlSelect;
	}

	public void setCtrlSelect(boolean ctrlSelect)
	{
		this.ctrlSelect = ctrlSelect;
	}

	public boolean isCheckOnSelect()
	{
		return checkOnSelect;
	}

	public void setCheckOnSelect(boolean checkOnSelect)
	{
		this.checkOnSelect = checkOnSelect;
	}

	public boolean isSelectOnCheck()
	{
		return selectOnCheck;
	}

	public void setSelectOnCheck(boolean selectOnCheck)
	{
		this.selectOnCheck = selectOnCheck;
	}

	public String getPagePosition()
	{
		return pagePosition;
	}

	public void setPagePosition(String pagePosition)
	{
		this.pagePosition = pagePosition;
	}

	public int getPageNumber()
	{
		return pageNumber;
	}

	public void setPageNumber(int pageNumber)
	{
		this.pageNumber = pageNumber;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public int[] getPageList()
	{
		return pageList;
	}

	public void setPageList(int[] pageList)
	{
		this.pageList = pageList;
	}

	public String getSortName()
	{
		return sortName;
	}

	public void setSortName(String sortName)
	{
		this.sortName = sortName;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
	}

	public boolean isMultiSort()
	{
		return multiSort;
	}

	public void setMultiSort(boolean multiSort)
	{
		this.multiSort = multiSort;
	}

	public boolean isRemoteSort()
	{
		return remoteSort;
	}

	public void setRemoteSort(boolean remoteSort)
	{
		this.remoteSort = remoteSort;
	}

	public boolean isShowHeader()
	{
		return showHeader;
	}

	public void setShowHeader(boolean showHeader)
	{
		this.showHeader = showHeader;
	}

	public boolean isShowFooter()
	{
		return showFooter;
	}

	public void setShowFooter(boolean showFooter)
	{
		this.showFooter = showFooter;
	}

	public int getScrollbarSize()
	{
		return scrollbarSize;
	}

	public void setScrollbarSize(int scrollbarSize)
	{
		this.scrollbarSize = scrollbarSize;
	}
}
