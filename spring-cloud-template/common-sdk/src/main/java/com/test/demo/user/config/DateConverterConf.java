package com.test.demo.user.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

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
 * jackson2 日期序列化和反序列化处理
 *
 */
@Configuration
@Slf4j
public class DateConverterConf {

	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static final String DATETIME_FORMATTER_STR = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMATTER_STR = "yyyy-MM-dd";
	// private static final String TIME_FORMATTER_STR = "HH:mm:ss";

	@Value("${jackson.default-property-inclusion:null}")
	private String enable;

	/**
	 * jackson2 json序列化 null字段输出为空串
	 *
	 * @return
	 * @author zhaoyang10
	 */
	@Bean
	@Primary
	// @ConditionalOnMissingBean(ObjectMapper.class)
	public ObjectMapper serializingObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		JavaTimeModule javaTimeModule = new JavaTimeModule();
		// 序列化日期格式
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

		if (!StringUtils.equals(enable, "null")) {
			objectMapper.setSerializationInclusion(JsonInclude.Include.valueOf(enable));
		}
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		return objectMapper;
	}

	/**
	 * 日期序列化
	 */
	public class SQLTimestampSerializer extends JsonSerializer<Timestamp> {

		@Override
		public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMATTER_STR);
			gen.writeString(dateFormat.format(value));
		}
	}

	/**
	 * 日期反序列化
	 */
	public class SQLTimestampDeserializer extends JsonDeserializer<Timestamp> {

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
	public class UtilDateSerializer extends JsonSerializer<Date> {
		@Override
		public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMATTER_STR);
			gen.writeString(dateFormat.format(value));
		}
	}

	/**
	 * 日期反序列化
	 */
	public class UtilDateDeserializer extends JsonDeserializer<Date> {

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
	public class SQLDateSerializer extends JsonSerializer<java.sql.Date> {
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
	public class SQLDateDeserializer extends JsonDeserializer<java.sql.Date> {
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
	public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

		@Override
		public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException {
			gen.writeString(value.format(DATETIME_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

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
	public class LocalDateSerializer extends JsonSerializer<LocalDate> {

		@Override
		public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(DATE_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

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
	public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

		@Override
		public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeString(value.format(TIME_FORMATTER));
		}
	}

	/**
	 * 日期反序列化
	 */
	public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

		@Override
		public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
			if (StringUtils.isEmpty(p.getValueAsString())) {
				return null;
			}
			return LocalTime.parse(p.getValueAsString(), TIME_FORMATTER);
		}
	}

	/**
	 * 接收前端入参日期的转换处理
	 *
	 * @return
	 */
	@Bean
	public Converter<String, LocalDateTime> LocalDateTimeConvert() {
		return new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(@NonNull String source) {
				LocalDateTime date = null;
				if (StringUtils.isNotBlank(source)) {
					date = LocalDateTime.parse(source, DATETIME_FORMATTER);
				}
				return date;
			}
		};
	}

	@Bean
	public Converter<String, LocalDate> LocalDateConvert() {
		return new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(@NonNull String source) {
				LocalDate date = null;
				if (StringUtils.isNotBlank(source)) {
					date = LocalDate.parse(source, DATE_FORMATTER);
				}
				return date;
			}
		};
	}

	@Bean
	public Converter<String, LocalTime> LocalTimeConvert() {
		return new Converter<String, LocalTime>() {
			@Override
			public LocalTime convert(@NonNull String source) {
				LocalTime time = null;
				if (StringUtils.isNotBlank(source)) {
					time = LocalTime.parse(source, TIME_FORMATTER);
				}
				return time;
			}
		};
	}

}
