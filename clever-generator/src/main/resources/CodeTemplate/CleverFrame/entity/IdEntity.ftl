import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类，对应表${tableSchema.tableName}(${tableSchema.description})<br/>
 *
 * 作者：LiZW <br/>
 * 创建时间：${GeneratorEntityUtils.getCurrentTime()?datetime} <br/>
 */
@Entity
@Table(name = "${tableSchema.tableName}")
@DynamicInsert
@DynamicUpdate
public class ${GeneratorEntityUtils.toClassName(tableSchema.tableName)} extends IdEntity {
    private static final long serialVersionUID = 1L;

<#list tableSchema.columnList as column>
    <#if column.columnName != 'id'>
        /** ${column.comment} */
        private ${GeneratorEntityUtils.toFieldType(column.dataType)} ${GeneratorEntityUtils.toFieldName(column.columnName)};
    </#if>
    
</#list> 
    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/
    
<#list tableSchema.columnList as column>
    <#if column.columnName != 'id'>
        <#assign fieldName = GeneratorEntityUtils.toFieldName(column.columnName)>
        <#assign fieldType = GeneratorEntityUtils.toFieldType(column.dataType)>
        /**
         * 获取 ${column.comment}
         */
        public ${fieldType} ${GeneratorEntityUtils.toGetMethodName(fieldName, column.dataType)}() {
            return ${fieldName};
        }

        /**
         * 设置 ${column.comment}
         */
        public void ${GeneratorEntityUtils.toSetMethodName(fieldName)}(${fieldType} ${fieldName}) {
            this.${fieldName} = ${fieldName};
        }
    </#if>

</#list>
}