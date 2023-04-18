package com.ecommerce.service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.Info.GoodsDataInfo;
import com.ecommerce.Info.GoodsSalesInfo;
import com.ecommerce.dao.BackendCriteriaDao;
import com.ecommerce.dao.BeverageGoodsJpaDao;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.mapper.BeverageGoodsMapper;
import com.ecommerce.mapper.GoodsOrderMapper;
import com.ecommerce.model.GoodsDataCondition;
import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.model.GoodsSalesCondition;
import com.ecommerce.model.PageUtility;
import com.ecommerce.vo.BeverageGoodsVo;

@Service
public class BackendService {

	@Autowired
	private BeverageGoodsJpaDao beverageGoodsJpaDao;

	@Autowired
	private BeverageGoodsMapper goodsConverter;
	
	@Autowired
	private GoodsOrderMapper orderConverter;

	@Autowired
	private BackendCriteriaDao backendCriteriaDao;

	public List<BeverageGoods> queryAllGoods() {
		return backendCriteriaDao.queryAllGoods();
	}

	public BeverageGoods queryGoodsByID(long goodsID) {
		return backendCriteriaDao.queryGoodsByID(goodsID);
	}

	public GoodsDataInfo queryPageGoodsWithCondition(GoodsDataCondition condition, PageUtility pageUtility,
			Pageable pageable) {
		Page<BeverageGoods> pageGoodsList = backendCriteriaDao.queryPageGoodsWithCondition(condition, pageable);
		int totalDataSize = (int) pageGoodsList.getTotalElements();
		int currentPageNo = pageUtility.getCurrentPageNo();
		int pageDataSize = pageUtility.getPageDataSize();
		int pageIconSize = pageUtility.getPageIconSize();
		List<Integer> pageList = getPageList(totalDataSize, pageDataSize);
		int endPageNo = pageList.get(pageList.size() - 1);
		List<Integer> showPageIcon = getShowPageIcon(currentPageNo, pageIconSize, endPageNo);

		pageUtility = pageUtility.toBuilder().totalDataSize(totalDataSize).endPageNo(endPageNo)
				.showPageIcon(showPageIcon).build();

		GoodsDataInfo goodsDataInfo = GoodsDataInfo.builder().dataList(pageGoodsList.toList()).pageUtility(pageUtility).build();
		return goodsDataInfo;
	}
	
	public GoodsSalesInfo queryGoodsSales(GoodsSalesCondition condition, PageUtility pageUtility, Pageable pageable) {
		Page<GoodsReportSales> pageOrderList=backendCriteriaDao.queryGoodsSales(condition, pageable);
		int totalDataSize = (int) pageOrderList.getTotalElements();
		int currentPageNo = pageUtility.getCurrentPageNo();
		int pageDataSize = pageUtility.getPageDataSize();
		int pageIconSize = pageUtility.getPageIconSize();
		List<Integer> pageList = getPageList(totalDataSize, pageDataSize);
		int endPageNo = pageList.get(pageList.size() - 1);
		List<Integer> showPageIcon = getShowPageIcon(currentPageNo, pageIconSize, endPageNo);
		
		pageUtility = pageUtility.toBuilder().totalDataSize(totalDataSize).endPageNo(endPageNo)
				.showPageIcon(showPageIcon).build();
		
		List<GoodsReportSales> goodsReportSalesList=pageOrderList.toList();
		GoodsSalesInfo goodsSalesInfo = orderConverter.convertToVo(pageUtility, goodsReportSalesList);
		
		return goodsSalesInfo;
	}

	public BeverageGoods createGoods(BeverageGoodsVo beverageGoodsVo) throws Exception {
		BeverageGoods goods = goodsConverter.convertToEntity(beverageGoodsVo);
		MultipartFile file = beverageGoodsVo.getFile();
		String fileName = beverageGoodsVo.getImageName();
		Path newPath = Paths.get("/Users/mac_home/Desktop/DrinksImage").resolve(fileName);
		// mac
		Files.copy(file.getInputStream(), newPath, StandardCopyOption.REPLACE_EXISTING);
		// window
//		Files.copy(file.getInputStream(), Paths.get("/home/VendingMachine/DrinksImage").resolve(fileName));
		return beverageGoodsJpaDao.save(goods);
	}

	@Transactional
	public BeverageGoods updateGoods(BeverageGoodsVo beverageGoodsVo) {
		Optional<BeverageGoods> optBeverageGoods = beverageGoodsJpaDao.findById(new BigDecimal(beverageGoodsVo.getGoodsID()));
		BeverageGoods goods = null;
		if (optBeverageGoods.isPresent()) {
			goods = optBeverageGoods.get();
			goodsConverter.updateEntity(beverageGoodsVo, goods);
		}
		return goods;
	}

	public List<Integer> getPageList(int totalDataSize, int pageDataSize) {
		List<Integer> pageList = new ArrayList<>();
		for (Integer i = 1; i <= totalDataSize / pageDataSize; i++) {
			pageList.add(i);
			if (i == totalDataSize / pageDataSize && totalDataSize % pageDataSize != 0) {
				pageList.add(++i);
			}
		}
		return pageList;
	}

	public List<Integer> getShowPageIcon(int currentPageNo, int pageIconSize, int endPageNo) {
		List<Integer> showPageIcon = new ArrayList<>();
		Integer startPage = 1;
		if ((currentPageNo % pageIconSize == 1 || pageIconSize == 1) && currentPageNo != 1)
			startPage = currentPageNo;
		else if (currentPageNo == endPageNo || currentPageNo % pageIconSize == 0) {
			for (int i = currentPageNo; i >= 1; i--) {
				if (i % pageIconSize == 1) {
					startPage = i;
					break;
				}
			}
		}

		while (showPageIcon.size() < pageIconSize) {
			if (startPage > endPageNo) break;
			showPageIcon.add(startPage++);
		}
		return showPageIcon;
	}

}
