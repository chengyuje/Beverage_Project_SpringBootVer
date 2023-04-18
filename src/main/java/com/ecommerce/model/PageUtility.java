package com.ecommerce.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Data
@ToString
public class PageUtility {

	private int currentPageNo;

	private int pageDataSize;

	private int pageIconSize;

	private int totalDataSize;

	private List<Integer> showPageIcon;

	private int endPageNo;

}
