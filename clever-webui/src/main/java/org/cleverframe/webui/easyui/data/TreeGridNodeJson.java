package org.cleverframe.webui.easyui.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.io.Serializable;

/**
 * EasyUI TreeGrid 行信息封装<br>
 *
 * @param <T> 数据表格行节点的数据
 * @author LiZhiWei
 * @version 2015年6月27日 下午2:34:40
 */
@JsonInclude(Include.NON_NULL)
public class TreeGridNodeJson<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private T entity;

    /**
     * 父节点ID
     */
    @JsonProperty("_parentId")
    private Long parentId;

    /**
     * 构造EasyUI TreeGrid树表格的节点行数据<br>
     * 1.注意：parentId参数必须是entity参数对象的parentId值<br>
     * 2.调用例子：new TreeGridNodeJson<Area>(entity.getParentId(), entity);<br>
     *
     * @param parentId 父节点ID，可以为null
     * @param entity   实体类节点信息，不能为null
     */
    public TreeGridNodeJson(Long parentId, T entity) {
        if (parentId != null && !parentId.equals(-1L)) {
            this.parentId = parentId;
        }
        this.entity = entity;
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/
    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
