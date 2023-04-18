package com.ecommerce.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.ecommerce.Info.MemberInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;

@Configuration
public class EcommerceConfig {

	@Bean(name = "sessionMemberInfo")
	@SessionScope
	public MemberInfo sessionMemberInfo() {
		return MemberInfo.builder().isLogin(false).build();
	}

	@Bean(name = "sessionCartGoods")
	@SessionScope
	public List<ShoppingCartGoodsInfo> sessionCartGoods() {
		return new ArrayList<>();
	}

//	@Bean(name = "objectMapper")
//	public ObjectMapper objectMapper() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
//		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//		return mapper;
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.findAndRegisterModules();
//		objectMapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		return objectMapper;
//	}
}
