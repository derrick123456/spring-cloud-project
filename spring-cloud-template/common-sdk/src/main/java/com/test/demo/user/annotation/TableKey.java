package com.test.demo.user.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>文件名称: TableKey.java </p>
 * <p>类型描述: [用于标注VO对象主键字段属性]
 * <p>使用规则: [1,用于按主键删除,以主键查找单个对象,以主键更新VO] </p>
 * <p>使用规则: [2,当时UUID主键时isSequence保持默认,且传入VO没有主键时,insertBase时,参考SQL:insert into ABC (ID,NAME) values (REPLACE(UUID(),'-',''),#{name})] </p>
 * <p>使用规则: [3,当时自增主键时isSequence属性需要为true,且传入VO没有主键时,insertBase时,参考SQL:sequenceTag为空insert into ABC (NAME) values (#{name}) 或 sequenceTag为SYS_USER_SEQ.NEXTVAL insert into ABC (ID,NAME) values (SYS_USER_SEQ.NEXTVAL,#{name})] </p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableKey {

	/**
	 * <p>项目名称: common-base </p> 
	 * <p>文件名称: TableKey.java </p> 
	 * <p>类型描述: [用于约束主键是否自增序列,默认为false,如为true,请根据对应数据库调整对应属性]
	 * <p>使用规则: [1,如果主键为UUID,字符串类型,请保持默认] </p>
	 * <p>使用规则: [2,如果主键为自增长,请标注该属性为true] </p>
	 * <p>创建时间: 2020年7月2日 </p>
	 * @author wgg
	 * @version V1.0
	 * @update 2020年7月2日  wgg
	 */
	public boolean isSequence() default false;

	/**
	 * <p>项目名称: common-base </p> 
	 * <p>文件名称: TableKey.java </p> 
	 * <p>类型描述: [如是主键自增,且为自增序列该属性有效,如Oracle数据库]
	 * <p>使用规则: [1,如果为mysql自增,该属性一般为空字符串,不用处理] </p>
	 * <p>使用规则: [2,如果为Oracle自增序列,该属性参考示例:SYS_USER_SEQ] </p>
	 * <p>创建时间: 2020年7月2日 </p>
	 * @author wgg
	 * @version V1.0
	 * @update 2020年7月2日  wgg
	 */
	public String sequenceTag() default "";
}
