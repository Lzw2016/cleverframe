package org.cleverframe.core.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.MDictDao;
import org.cleverframe.core.entity.MDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 23:07 <br/>
 */
@Service(CoreBeanNames.MDictService)
public class MDictService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.MDictDao)
    private MDictDao mDictDao;

    /**
     * 根据字典类型获取数据字典(模糊查询一棵树上的数据字典)，使用分页<br/>
     *
     * @param page      分页对象
     * @param mdictType 查询参数：字典分类,不能为空
     * @param mdictKey  查询参数：字典键
     * @param id        查询参数：ID
     * @param uuid      查询参数：UUID
     * @param delFlag   查询参数：删除标记
     * @return 分页数据
     */
    public Page<MDict> findByPage(Page<MDict> page, String mdictType, String mdictKey, Long id, String uuid, Character delFlag) {
        return mDictDao.findByPage(page, mdictType, mdictKey, id, uuid, delFlag);
    }

    /**
     * 新增多级数据字典<br/>
     * <b>
     * 1.字典类型要与父节点一致<br/>
     * 2.计算出节点路径 fullPath<br/>
     * </b>
     *
     * @return 成功返回true，失败返回false
     */
    public boolean saveMDict(MDict mDict, AjaxMessage ajaxMessage) {
        MDict parent = null;
        if (mDict.getParentId() != -1L) {
            // 字典类型要与父节点一致
            parent = mDictDao.getHibernateDao().get(mDict.getParentId());
            if (parent != null) {
                mDict.setMdictType(parent.getMdictType());
            } else {
                ajaxMessage.setSuccess(false);
                ajaxMessage.setFailMessage("父节点不存在ID=[" + mDict.getParentId() + "]");
                return false;
            }
        }
        if (parent == null) {
            mDict.setFullPath("");
        } else {
            mDict.setFullPath(parent.getFullPath());
        }
        mDictDao.getHibernateDao().save(mDict);
        mDict.setFullPath(mDict.getFullPath() + MDict.FULL_PATH_SPLIT + mDict.getId());
        mDictDao.getHibernateDao().update(mDict);
        return true;
    }

    /**
     * 更新多级字典<br/>
     *
     * @param mDict
     * @param ajaxMessage
     * @return
     */
    public boolean updateMDict(MDict mDict, AjaxMessage ajaxMessage) {
        MDict oldMDict = mDictDao.getHibernateDao().get(mDict.getId());
        if (oldMDict == null) {
            ajaxMessage.setSuccess(false);
            ajaxMessage.setFailMessage("更新的字典数据不存在ID=[" + mDict.getId() + "]");
            return false;
        }

        if(oldMDict.getParentId().equals(mDict.getParentId())) {
            // 没有修改父级编号-节点位置不变

        }

//        if(没有修改父级编号-节点位置不变) {
//            if(修改的节点不是根节点) {
//                不能修改：mdict_type
//            } else {
//                任意修改
//            }
//        } else(修改了父级编号-节点位置发生变化) {
//            if(节点变成了根节点) {
//                任意修改
//            } else {
//                if(修改节点的mdict_type与当前父节点不同) {
//                    不能修改
//                } else {
//                    可以修改
//                }
//            }
//        }
//
//        if(修改了mdict_type) {
//            更新所有子节点的mdict_type
//        }
//
//        if(修改了父级编号-节点位置发生变化) {
//            更新所有子节点的full_path
//        }
//
//        重新计算full_path、mdict_type、parent_id

        return true;
    }

}
