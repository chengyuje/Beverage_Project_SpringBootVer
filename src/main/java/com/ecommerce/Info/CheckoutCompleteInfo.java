package com.ecommerce.Info;

import java.util.List;

import com.ecommerce.model.CheckoutReceipt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@ToString
public class CheckoutCompleteInfo {
	
	private String customerName;
	
	private List<CheckoutReceipt> receipt;
	
	private Integer totalAmount;

}
