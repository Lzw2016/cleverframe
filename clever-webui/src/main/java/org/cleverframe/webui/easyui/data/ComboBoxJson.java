package org.cleverframe.webui.easyui.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * EasyUI ComboBox数据绑定的Json格式数据<br>
 * 
 * @author LiZhiWei
 * @version 2015年7月7日 下午9:20:35
 */
@JsonInclude(Include.NON_NULL)
public class ComboBoxJson implements Serializable
{
	private static final long serialVersionUID = 1L;

	/** 是否被选中 */
	private boolean selected = false;
	/** ComboBox显示名称 */
	private String text;
	/** ComboBox表单提交值 */
	private String value;
    /** 绑定的实体对象 */
    private Serializable object;
    
	public ComboBoxJson()
	{

	}

	/**
	 * @param selected 是否被选中
	 * @param text ComboBox显示名称
	 * @param value ComboBox表单提交值
	 * @param object 绑定的实体对象
	 */
	public ComboBoxJson(boolean selected, String text, String value,Serializable object)
	{
		this.selected = selected;
		this.text = text;
		this.value = value;
		this.object = object;
	}

	   /**
     * @param selected 是否被选中
     * @param text ComboBox显示名称
     * @param value ComboBox表单提交值
     */
    public ComboBoxJson(boolean selected, String text, String value)
    {
        this.selected = selected;
        this.text = text;
        this.value = value;
    }
	
	/**
	 * @param text ComboBox显示名称
	 * @param value ComboBox表单提交值
	 */
	public ComboBoxJson(String text, String value)
	{
		this.text = text;
		this.value = value;
	}

	/**
	 * @param value ComboBox表单提交值
	 */
	public ComboBoxJson(String value)
	{
		this.value = value;
	}

	/*--------------------------------------------------------------
	 * 			getter、setter
	 * -------------------------------------------------------------*/
	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

    public Serializable getObject()
    {
        return object;
    }

    public void setObject(Serializable object)
    {
        this.object = object;
    }
}
