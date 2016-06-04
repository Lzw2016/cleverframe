package org.cleverframe.webui.easyui.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.cleverframe.common.tree.ITreeNode;

import java.io.Serializable;
import java.util.*;

/**
 * EasyUI Tree节点数据
 * 
 * @author LiZhiWei
 * @version 2015年6月4日 下午9:51:39
 */
@JsonInclude(Include.NON_NULL)
public class TreeNodeJson implements ITreeNode
{
    private static final long serialVersionUID = 1L;
    /** 节点标识 */
    private Long id;
    /** 父级编号 */
    private Long parentId;
    /** 树节点全路径  */
    private String fullPath = "-";
    /** 是否被添加到父节点下 */
    private boolean isBulid = false;
    /** 显示节点文本 */
    private String text;
    /** 节点图标 */
    private String iconCls;
    /** 是否勾选状态 */
    private boolean checked;
    /** 节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点 */
    private String state = "open";
    /** 子节点 */
    private List<ITreeNode> children;
    /** 被添加到节点的自定义属性 */
    private Map<String, String> attributes;
    /** 绑定到节点的对象 */
    private Serializable object;
    
    public TreeNodeJson()
    {

    }

    /**
     * @param parentId 父级编号
     * @param id 节点标识
     * @param text 显示节点文本
     * */
    public TreeNodeJson(Long parentId, Long id, String fullPath, String text)
    {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
    }

    /**
     * @param parentId 父级编号
     * @param id 节点标识
     * @param fullPath 节点路径
     * @param text 显示节点文本
     * @param iconCls 节点图标
     * @param checked 是否勾选状态
     * @param state 节点状态 'open' 或 'closed'
     * */
    public TreeNodeJson(Long parentId, Long id, String fullPath, String text, String iconCls, boolean checked, String state)
    {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
        this.iconCls = iconCls;
        this.checked = checked;
        this.state = state;
    }

    /**
     * @param parentId 父级编号
     * @param id 节点标识
	 * @param fullPath 节点路径
     * @param text 显示节点文本
     * @param iconCls 节点图标
     * @param checked 是否勾选状态
     * @param state 节点状态 'open' 或 'closed'
     * @param children 子节点
     * @param attributes 被添加到节点的自定义属性
     * */
    public TreeNodeJson(Long parentId, Long id, String fullPath, String text, String iconCls, boolean checked, String state, Set<TreeNodeJson> children, Map<String, String> attributes)
    {
        this.parentId = parentId;
        this.id = id;
        this.fullPath = fullPath;
        this.text = text;
        this.iconCls = iconCls;
        this.checked = checked;
        this.state = state;
        this.attributes = attributes;
        this.addChildren(children);
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public Long getParentId()
    {
        return parentId;
    }

    @Override
    public String getFullPath()
    {
        return fullPath;
    }

    @Override
    public boolean isBulid()
    {
        return isBulid;
    }

    @Override
    public void setBulid(boolean isBulid)
    {
        this.isBulid = isBulid;

    }

    @Override
    public List<ITreeNode> getChildren()
    {
        return children;
    }

    @Override
    @Deprecated
    public void addChildren(ITreeNode node)
    {
        if (this.children == null)
        {
            this.children = new ArrayList<ITreeNode>();
        }
        this.children.add(node);
    }

    /**
     * 增加子节点<br>
     * */
    public TreeNodeJson addChildren(TreeNodeJson node)
    {
        if (this.children == null)
        {
            this.children = new ArrayList<ITreeNode>();
        }
        this.children.add(node);
        return this;
    }

    /**
     * 增加子节点集合<br>
     * */
    public TreeNodeJson addChildren(Collection<TreeNodeJson> nodes)
    {
        if (this.children == null)
        {
            this.children = new ArrayList<ITreeNode>();
        }
        this.children.addAll(nodes);
        return this;
    }

    /**
     * 增加自定义属性<br>
     * */
    public TreeNodeJson addAttributes(String name, String value)
    {
        if (this.attributes == null)
        {
            this.attributes = new HashMap<String, String>();
        }
        this.attributes.put(name, value);
        return this;
    }

    /**
     * 增加自定义属性<br>
     * */
    public TreeNodeJson addAttributes(Map<String, String> attributes)
    {
        if (this.attributes == null)
        {
            this.attributes = new HashMap<String, String>();
        }
        this.attributes.putAll(attributes);
        return this;
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/
    public void setFullPath(String fullPath)
    {
        this.fullPath = fullPath;
    }
    
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getIconCls()
    {
        return iconCls;
    }

    public void setIconCls(String iconCls)
    {
        this.iconCls = iconCls;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Map<String, String> getAttributes()
    {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public void setChildren(List<ITreeNode> children)
    {
        this.children = children;
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
