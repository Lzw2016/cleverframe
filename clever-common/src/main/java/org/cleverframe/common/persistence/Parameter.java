package org.cleverframe.common.persistence;

import org.cleverframe.common.mapper.BeanMapConverter;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 数据库查询参数，继承HashMap，存储参数名、参数值键值对<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 0:17 <br/>
 */
public class Parameter extends HashMap<String, Object> {
    /**
     * 默认构造<br/>
     */
    public Parameter() {

    }

    /**
     * 构造类，JavaBean对象的属性名转成key，属性值转成value<br/>
     * <b>注意: 参数 bean 只能是一个JavaBean,不能是一个基本类型如：int,byte,short等</b><br/>
     *
     * @param bean JavaBean对象
     */
    public Parameter(Object bean) {
        // 确认 bean 不是基本类型
        if (bean instanceof Short
                || bean instanceof Long
                || bean instanceof Integer
                || bean instanceof Float
                || bean instanceof Double
                || bean instanceof Charset
                || bean instanceof String
                || bean instanceof Byte) {
            put("p1", bean);
            return;
        }
        if (bean != null) {
            Map<String, Object> map = BeanMapConverter.toMap(bean);
            if (map != null && map.size() > 0) {
                this.putAll(map);
            }
        }
    }

    /**
     * 构造类，例：new Parameter(id, name)<br/>
     * 参数名默认按照参数位置：p1、p2、p3...<br/>
     *
     * @param values 参数值
     */
    public Parameter(Object... values) {
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                put("p" + (i + 1), values[i]);
            }
        }
    }

    /**
     * 构造类，例：new Parameter(new Object[][]{{"id", id}, {"name", name}})
     *
     * @param parameters 参数二维数组
     */
    public Parameter(Object[][] parameters) {
        if (parameters != null) {
            for (Object[] os : parameters) {
                if (os.length == 2) {
                    put((String) os[0], os[1]);
                }
            }
        }
    }

    /**
     * 参数中设置删除标识参数
     *
     * @param delFlag 删除标识参数，SQL(HQL)参数名：delFlag
     */
    public Parameter(Character delFlag) {
        put("delFlag", delFlag);
    }

    /**
     * 参数中设置删除标识参数、和公司ID
     *
     * @param delFlag   删除标识参数，SQL(HQL)参数名：delFlag
     * @param companyId 公司ID参数，SQL(HQL)参数名：companyId
     */
    public Parameter(Character delFlag, Long companyId) {
        put("delFlag", delFlag);
        put("companyId", companyId);
    }

    /**
     * 返回二维数组形式参数
     */
    public Object[][] getParameter() {
        Object[][] parameter = new Object[this.size()][2];
        Set<Map.Entry<String, Object>> set = this.entrySet();
        int count = 0;
        for (Map.Entry<String, Object> entry : set) {
            parameter[count][0] = entry.getKey();
            parameter[count][1] = entry.getValue();
            count++;
        }
        return parameter;
    }

    /**
     * 返回参数的名称数组
     */
    public String[] getNames() {
        String[] names = new String[this.size()];
        Set<Map.Entry<String, Object>> set = this.entrySet();
        int count = 0;
        for (Map.Entry<String, Object> entry : set) {
            names[count] = entry.getKey();
            count++;
        }
        return names;
    }

    /**
     * 返回参数的值数组
     */
    public Object[] getValues() {
        Object[] values = new Object[this.size()];
        Set<Map.Entry<String, Object>> set = this.entrySet();
        int count = 0;
        for (Map.Entry<String, Object> entry : set) {
            values[count] = entry.getValue();
            count++;
        }
        return values;
    }
}
