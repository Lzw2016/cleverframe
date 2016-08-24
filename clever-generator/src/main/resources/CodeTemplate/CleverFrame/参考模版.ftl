数据库名称 - ${tableSchema.schemaName}
表名称 - ${tableSchema.tableName}
表注释 - ${tableSchema.description}

================================================================================

<#list tableSchema.columnList as column>
--------------------------------------------------------
序号位置 - ${column.ordinalPosition}
列名称 - ${column.columnName}
字符串最大长度 - ${column.size}
是否是外键 - ${column.partOfForeignKey}
是否是主键 - ${column.partOfPrimaryKey}
是否唯一约束 - ${column.partOfUniqueIndex}
数据类型 - ${column.dataType}
是否不能为空 - ${column.notNull}
是否自增长 - ${column.autoIncrement}
默认值 - ${column.defaultValue}
列注释 - ${column.comment}
</#list>
--------------------------------------------------------

${tableSchema.setTableName('自定义表名')}
${tableSchema.getTableName()}



================================================================================
GeneratorEntityUtils

转驼峰字符串 - ${GeneratorEntityUtils.getCamelCaseString('core_resources',true)}

类名 - ${GeneratorEntityUtils.toClassName('core_resources')}

字段名 - ${GeneratorEntityUtils.toFieldName('core_resources')}

字段类型 - ${GeneratorEntityUtils.toFieldType('INT')}

getter方法名 - ${GeneratorEntityUtils.toGetMethodName('core_resources', 'INT')}

setter方法名 - ${GeneratorEntityUtils.toSetMethodName('core_resources')}