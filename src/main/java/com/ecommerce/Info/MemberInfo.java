package com.ecommerce.Info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString
//@JsonInclude(Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@JsonIgnoreProperties(
		value = {
		"beanExpressionResolver", "beanFactory", "targetSource", "advisors", 
		"classFilter", "targetClass", "proxiedInterfaces", "proxyTargetClass", 
		"exposeProxy", "preFiltered", "targetObject", "frozen"
		}
)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberInfo {
	
	private Boolean isLogin;

	private String loginMessage;

	private String idCardNo;
	
	private String customerName;

	private String customerPassword;

}
