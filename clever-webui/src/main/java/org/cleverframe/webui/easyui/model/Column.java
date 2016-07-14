package org.cleverframe.webui.easyui.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * DataGrid表格的Column<br>
 *
 * @author LiZhiWei
 * @version 2015年5月30日 下午12:31:53
 */
@JsonInclude(Include.NON_DEFAULT)
public class Column implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 列标题文本
     */
    private String title;
    /**
     * 列字段名称
     */
    private String field;
    /**
     * 列的宽度
     */
    private int width;
    /**
     * 指明将占用多少行单元格（合并行）
     */
    private int rowspan;
    /**
     * 指明将占用多少列单元格（合并列）
     */
    private int colspan;
    /**
     * 指明如何对齐列数据。可以使用的值有：'left','right','center'
     */
    private String align;
    /**
     * 指明如何对齐列标题。可以使用的值有：'left','right','center'。如果没有指定，则按照align属性进行对齐
     */
    private String halign;
    /**
     * 如果为true，则允许列使用排序
     */
    private boolean sortable;
    /**
     * 默认排序数序，只能是'asc'或'desc'
     */
    private String order;
    /**
     * 如果为true，允许列改变大小
     */
    private boolean resizable;
    /**
     * 如果为true，在"fitColumns"设置为true的时候阻止其自适应宽度
     */
    private boolean fixed;
    /**
     * 如果为true，则隐藏列
     */
    private boolean hidden;
    /**
     * 如果为true，则显示复选框。该复选框列固定宽度
     */
    private boolean checkbox;

    public Column() {

    }

    /**
     * @param field 列编码
     * @param title 列名称
     */
    public Column(String field, String title) {
        this.field = field;
        this.title = title;
    }

    /**
     * @param field    列编码
     * @param title    列名称
     * @param checkbox 如果为true，则显示复选框
     */
    public Column(String field, String title, boolean checkbox) {
        this.field = field;
        this.title = title;
        this.checkbox = checkbox;
    }

    /**
     * @param field     列编码
     * @param title     列名称
     * @param width     列的宽度
     * @param align     指明如何对齐列数据。可以使用的值有：'left','right','center'
     * @param halign    指明如何对齐列标题。可以使用的值有：'left','right','center'
     * @param sortable  如果为true，则允许列使用排序
     * @param resizable 如果为true，允许列改变大小
     */
    public Column(String field, String title, int width, String align, String halign, boolean sortable, boolean resizable) {
        this.field = field;
        this.title = title;
        this.width = width;
        this.align = align;
        this.halign = halign;
        this.sortable = sortable;
        this.resizable = resizable;
    }

    /**
     * 列合并设置<br>
     *
     * @param rowspan 指明将占用多少行单元格（合并行）
     * @param colspan 指明将占用多少列单元格（合并列）
     */
    public void cloumnMerger(int rowspan, int colspan) {
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getHalign() {
        return halign;
    }

    public void setHalign(String halign) {
        this.halign = halign;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}
