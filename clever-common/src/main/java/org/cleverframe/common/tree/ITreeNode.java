package org.cleverframe.common.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点接口，如果对象需要构建成一个树结构，则需要实现此接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 22:07 <br/>
 *
 * @see org.cleverframe.common.tree.BuildTreeUtils
 */
public interface ITreeNode extends Serializable {
    /**
     * 节点ID
     */
    Long getId();

    /**
     * 父节点ID
     */
    Long getParentId();

    /**
     * 节点全路径
     */
    String getFullPath();

    /**
     * 判断当前节点是否被构建到树中了
     */
    boolean isBulid();

    /**
     * 设置当前节点是否构建到树中
     */
    void setBulid(boolean isBulid);

    /**
     * 返回所有子节点，必须是List否则顺序会不一致
     */
    List<ITreeNode> getChildren();

    /**
     * 增加子节点
     *
     * @param node 子节点
     */
    void addChildren(ITreeNode node);
}
