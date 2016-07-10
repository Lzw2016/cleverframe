package org.cleverframe.core.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.DictDao;
import org.cleverframe.core.entity.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 21:23 <br/>
 */
@Service(CoreBeanNames.DictService)
public class DictService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.DictDao)
    private DictDao dictDao;

    /**
     * 获取数据字典，使用分页<br/>
     *
     * @param page     分页对象
     * @param dictKey  查询参数：字典键
     * @param dictType 查询参数：字典分类
     * @param id       查询参数：ID
     * @param uuid     查询参数：UUID
     * @param delFlag  查询参数：删除标记
     * @return 分页数据
     */
    public Page<Dict> findByPage(Page<Dict> page, String dictKey, String dictType, Long id, String uuid, Character delFlag) {
        return dictDao.findByPage(page, dictKey, dictType, id, uuid, delFlag);
    }

    /**
     * 保存数据字典
     *
     * @param dict 数据字典对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean saveDict(Dict dict) {
        dictDao.getHibernateDao().save(dict);
        return true;
    }

    /**
     * 更新数据字典
     *
     * @param dict 数据字典对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean updateDict(Dict dict) {
        dictDao.getHibernateDao().update(dict, false, true);
        return true;
    }

    /**
     * 根据ID删除数据字典
     *
     * @param id 数据字典ID
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean deleteDict(Long id) {
        Dict dict = new Dict();
        dict.setId(id);
        dictDao.getHibernateDao().delete(dict);
        return true;
    }

    /**
     * 根据字典类型查询所有的字典
     *
     * @param dictType 字典类型
     * @return 字典集合
     */
    public List<Dict> findDictByType(String dictType) {
        return dictDao.findByType(dictType);
    }
}
