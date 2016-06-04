package org.cleverframe.webui.easyui.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * EasyUI DataGrid数据绑定的Json格式数据<br>
 * 
 * @author LiZhiWei
 * @version 2015年5月31日 下午8:04:52
 * @param <T> 数据表格的行数据类型
 */
@JsonInclude(Include.NON_NULL)
public class DataGridJson<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 数据总数 */
    private long total = 0;

    /** 要显示的数据集合 */
    private List<T> rows = new ArrayList<T>();

    /** 页脚统计的数据 */
    private List<T> footer;

    public DataGridJson()
    {

    }

    /**
     * @param rows 要显示的数据集合
     * @param footer 页脚统计的数据
     * */
    public DataGridJson(List<T> rows, List<T> footer)
    {
        this.rows = rows;
        this.footer = footer;
        if (this.rows != null)
        {
            this.total = this.rows.size();
        }
    }

    /**
     * 向数据集合中增加一行数据，会自动计算 数据总数：total<br>
     * */
    public DataGridJson<T> addRow(T row)
    {
        if (this.rows == null)
        {
            this.rows = new ArrayList<T>();
        }
        this.rows.add(row);
        this.total = this.rows.size();
        return this;
    }

    /**
     * 向页脚统计的数据中增加一行统计数据
     * */
    public DataGridJson<T> addFooter(T footer)
    {
        if (this.footer == null)
        {
            this.footer = new ArrayList<T>();
        }
        this.footer.add(footer);
        return this;
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/
    public Long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<T> getRows()
    {
        return rows;
    }

    /** 设置要显示的数据集合，会自动计算 数据总数：total */
    public void setRows(List<T> rows)
    {
        this.rows = rows;
        if (this.rows != null)
        {
            this.total = this.rows.size();
        }
        else
        {
            this.total = 0;
        }
    }

    public List<T> getFooter()
    {
        return footer;
    }

    public void setFooter(List<T> footer)
    {
        this.footer = footer;
    }
}
