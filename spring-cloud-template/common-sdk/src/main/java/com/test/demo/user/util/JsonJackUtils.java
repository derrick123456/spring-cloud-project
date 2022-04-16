package com.test.demo.user.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: JsonJackUtils.java </p> 
 * <p>类型描述: [描述] </p>
 */
@Slf4j
public class JsonJackUtils {
	
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static final String DATETIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMATTER_STR = "yyyy-MM-dd";
	
	/**
	 * 日期序列化
	 */
	public static class SQLTimestampSerializer extends JsonSerializer<Timestamp> {

		@Override
		public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMATTER_STR);
			gen.writeString(dateFormat.format(value));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class SQLTimestampDeserializer extends JsonDeserializer<Timestamp> {

		@Override
		public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return Timestamp.valueOf(p.getValueAsString());
		}
	}

	/**
	 * 日期序列化
	 */
	public static class UtilDateSerializer extends JsonSerializer<Date> {
		@Override
		public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMATTER_STR);
			gen.writeString(dateFormat.format(value));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class UtilDateDeserializer extends JsonDeserializer<Date> {

		@Override
		public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMATTER_STR);
			try {
				return dateFormat.parse(p.getValueAsString());
			} catch (ParseException e) {
				// e.printStackTrace();
				log.debug("", e);
				return null;
			}
		}
	}

	/**
	 * 日期序列化
	 */
	public static class SQLDateSerializer extends JsonSerializer<java.sql.Date> {
		@Override
		public void serialize(java.sql.Date value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMATTER_STR);
			gen.writeString(dateFormat.format(value));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class SQLDateDeserializer extends JsonDeserializer<java.sql.Date> {
		@Override
		public java.sql.Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return java.sql.Date.valueOf(p.getValueAsString());
		}
	}

	/**
	 * 日期序列化
	 */
	public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

		@Override
		public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException {
			gen.writeString(value.format(DATETIME_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

		@Override
		public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return LocalDateTime.parse(p.getValueAsString(), DATETIME_FORMATTER);
		}
	}

	/**
	 * 日期序列化
	 */
	public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

		@Override
		public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(DATE_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

		@Override
		public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return LocalDate.parse(p.getValueAsString(), DATE_FORMATTER);
		}
	}

	/**
	 * 日期序列化
	 */
	public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

		@Override
		public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(TIME_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

		@Override
		public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return LocalTime.parse(p.getValueAsString(), TIME_FORMATTER);
		}
	}
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
		javaTimeModule.addSerializer(Timestamp.class, new SQLTimestampSerializer());
		javaTimeModule.addSerializer(java.sql.Date.class, new SQLDateSerializer());
		javaTimeModule.addSerializer(Date.class, new UtilDateSerializer());

		// 反序列化日期格式
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
		javaTimeModule.addDeserializer(Timestamp.class, new SQLTimestampDeserializer());
		javaTimeModule.addDeserializer(java.sql.Date.class, new SQLDateDeserializer());
		javaTimeModule.addDeserializer(Date.class, new UtilDateDeserializer());

		objectMapper.registerModule(javaTimeModule);

		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		return objectMapper;
	}

//	static ObjectMapper objectMapper;
//
//	static {
//		if (objectMapper == null) {
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			JavaTimeModule javaTimeModule = new JavaTimeModule();
//			javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//			javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
//			javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());
//			javaTimeModule.addSerializer(Timestamp.class, new SQLTimestampSerializer());
//			javaTimeModule.addSerializer(java.sql.Date.class, new SQLDateSerializer());
//			javaTimeModule.addSerializer(Date.class, new UtilDateSerializer());
//
//			// 反序列化日期格式
//			javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//			javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//			javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
//			javaTimeModule.addDeserializer(Timestamp.class, new SQLTimestampDeserializer());
//			javaTimeModule.addDeserializer(java.sql.Date.class, new SQLDateDeserializer());
//			javaTimeModule.addDeserializer(Date.class, new UtilDateDeserializer());
//
//			objectMapper.registerModule(javaTimeModule);
//
//			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//			objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//		}
////		objectMapper.setSerializationInclusion(Include.NON_NULL);
////		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
////		// 允许出现特殊字符和转义符
////		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
////		objectMapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
////		// 允许出现单引号
////		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
////		objectMapper.configure(Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
//	}

	/**
	 * <p>功能描述: [使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * (2)转换为List:readValue(json,List.class).但是如果我们想把json转换为特定类型的List
	 * ,比如List<Student>，就不能直接进行转换了。
	 * .因为readValue(json
	 * ,List.class)返回的其实是List<Map<k,v>>类型，你不能指定readValue()的第二个参数是List<Student>.class，所以不能直接转换。
	 * .我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays
	 * .asList();方法把得到的数组转换为特定类型的List。 
	 * (3)转换为Map：readValue(json,Map.class)我们使用泛型，得到的也是泛型] </p>
	 * @Title readValue
	 * @param <T> .
	 * @param content 要转换的JavaBean类型
	 * @param valueType 原始json字符串数据
	 * @return T JavaBean对象
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		if (StringUtils.isEmpty(content) || valueType == null) {
			return null;
		}
		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	/**
	 * 
	* @Function: JsonJackUtils.java
	* @Title: toJavaObject
	* @Description: 将JSON字符串转换为指定java对象,不支持泛型嵌套,如toJavaObject("", Student.class);
	* @param <T>
	* @param content
	* @param valueType
	* @return  T
	 */
	public static <T> T toJavaObject(String content, Class<T> valueType) {
		if (StringUtils.isEmpty(content) || valueType == null) {
			return null;
		}
		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
	
	/**
	 * 
	* @Function: JsonJackUtils.java
	* @Title: toJavaObject
	* @Description: 将JSON字符串转换为指定java对象,支持泛型嵌套转换,如List<Map<String,String>>,如toJavaObject("", new TypeReference<List<Map<String,String>>>() {});
	* @param <T>
	* @param content
	* @param valueTypeRef
	* @return  T
	 */
	public static <T> T toJavaObject(String content, TypeReference<T> valueTypeRef) {
		if (StringUtils.isEmpty(content) || valueTypeRef == null) {
			return null;
		}
		try {
			return getObjectMapper().readValue(content, valueTypeRef);
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}

	/**
	 * <p>功能描述: [把JavaBean转换为json字符串 
	 * (1)普通对象转换：toJson(Student) 
	 * (2)List转换：toJson(List)
	 * (3)Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法] </p>
	 * @Title toJSon
	 * @param object JavaBean对象
	 * @return String json字符串
	 */
	public static String toJSon(Object object) {
		try {
			return getObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			log.error("",e);
			return null;
		}
	}
}
