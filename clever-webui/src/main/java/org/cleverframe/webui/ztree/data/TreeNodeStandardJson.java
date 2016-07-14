package org.cleverframe.webui.ztree.data;


import org.cleverframe.common.tree.ITreeNode;

import java.util.List;

/**
 * zTree 标准Json数据节点<br>
 *
 * @author LiZW
 * @version 2015年6月29日 下午2:25:38
 */
public class TreeNodeStandardJson implements ITreeNode {
    private static final long serialVersionUID = 1L;

    @Override
    public Long getId() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public Long getParentId() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public String getFullPath() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    public boolean isBulid() {
        // TODO 自动生成的方法存根
        return false;
    }

    @Override
    public void setBulid(boolean isBulid) {
        // TODO 自动生成的方法存根

    }

    @Override
    public List<ITreeNode> getChildren() {
        // TODO 自动生成的方法存根
        return null;
    }

    @Override
    @Deprecated
    public void addChildren(ITreeNode node) {
        // TODO 自动生成的方法存根

    }
}
