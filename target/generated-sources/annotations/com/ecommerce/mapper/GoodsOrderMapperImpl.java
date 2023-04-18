package com.ecommerce.mapper;

import com.ecommerce.Info.BeverageGoodsInfo2;
import com.ecommerce.Info.CheckoutCompleteInfo;
import com.ecommerce.Info.CheckoutCompleteInfo.CheckoutCompleteInfoBuilder;
import com.ecommerce.Info.GoodsSalesInfo;
import com.ecommerce.Info.GoodsSalesInfo.GoodsSalesInfoBuilder;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageGoods.BeverageGoodsBuilder;
import com.ecommerce.entity.BeverageOrder;
import com.ecommerce.entity.BeverageOrder.BeverageOrderBuilder;
import com.ecommerce.model.CheckoutReceipt;
import com.ecommerce.model.CheckoutReceipt.CheckoutReceiptBuilder;
import com.ecommerce.model.GoodsReportSales;
import com.ecommerce.model.PageUtility;
import com.ecommerce.vo.GoodsOrderVo;
import com.ecommerce.vo.OrderVo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-17T23:52:57+0800",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.300.v20221108-0856, environment: Java 17.0.6 (Eclipse Adoptium)"
)
@Component
public class GoodsOrderMapperImpl implements GoodsOrderMapper {

    @Override
    public BeverageGoods convertToEntity(GoodsOrderVo goodsOrderVo) {
        if ( goodsOrderVo == null ) {
            return null;
        }

        BeverageGoodsBuilder<?, ?> beverageGoods = BeverageGoods.builder();

        beverageGoods.goodsDescription( goodsOrderVo.getDescription() );
        beverageGoods.goodsPrice( goodsOrderVo.getPrice() );
        beverageGoods.goodsQuantity( goodsOrderVo.getQuantity() );
        beverageGoods.goodsImageName( goodsOrderVo.getImageName() );
        beverageGoods.goodsStatus( goodsOrderVo.getStatus() );
        beverageGoods.goodsName( goodsOrderVo.getGoodsName() );

        return beverageGoods.build();
    }

    @Override
    public BeverageOrder convertToEntity(OrderVo orderVo, BigDecimal id) {
        if ( orderVo == null && id == null ) {
            return null;
        }

        BeverageOrderBuilder<?, ?> beverageOrder = BeverageOrder.builder();

        if ( orderVo != null ) {
            beverageOrder.buyQuantity( orderVo.getBuyQuantity() );
            beverageOrder.customerID( orderVo.getCustomerID() );
            beverageOrder.goodsBuyPrice( orderVo.getGoodsBuyPrice() );
            beverageOrder.orderDate( orderVo.getOrderDate() );
            beverageOrder.orderID( orderVo.getOrderID() );
        }
        if ( id != null ) {
            beverageOrder.goodsID( id.longValue() );
        }

        return beverageOrder.build();
    }

    @Override
    public void updateEntity(GoodsOrderVo goodsOrderVo, BeverageGoods beverageGoods) {
        if ( goodsOrderVo == null ) {
            return;
        }

        if ( goodsOrderVo.getDescription() != null ) {
            beverageGoods.setGoodsDescription( goodsOrderVo.getDescription() );
        }
        if ( goodsOrderVo.getImageName() != null ) {
            beverageGoods.setGoodsImageName( goodsOrderVo.getImageName() );
        }
        if ( goodsOrderVo.getStatus() != null ) {
            beverageGoods.setGoodsStatus( goodsOrderVo.getStatus() );
        }
        if ( goodsOrderVo.getGoodsName() != null ) {
            beverageGoods.setGoodsName( goodsOrderVo.getGoodsName() );
        }

        beverageGoods.setGoodsPrice( utility.goodsPriceCheck(goodsOrderVo.getPrice(),beverageGoods.getGoodsPrice()) );
        beverageGoods.setGoodsQuantity( utility.goodsQuantityCheck(goodsOrderVo.getQuantity(),beverageGoods.getGoodsQuantity()) );
    }

    @Override
    public void updateEntity(BigDecimal id, OrderVo orderVo, BeverageOrder beverageOrder) {
        if ( id == null && orderVo == null ) {
            return;
        }

        if ( id != null ) {
            beverageOrder.setGoodsID( id.longValue() );
        }
        if ( orderVo != null ) {
            if ( orderVo.getCustomerID() != null ) {
                beverageOrder.setCustomerID( orderVo.getCustomerID() );
            }
            if ( orderVo.getOrderDate() != null ) {
                beverageOrder.setOrderDate( orderVo.getOrderDate() );
            }
            beverageOrder.setOrderID( orderVo.getOrderID() );
        }
        beverageOrder.setGoodsBuyPrice( utility.orderPriceCheck(orderVo.getGoodsBuyPrice(),beverageOrder.getGoodsBuyPrice()) );
        beverageOrder.setBuyQuantity( utility.orderQuantityCheck(orderVo.getBuyQuantity(),beverageOrder.getBuyQuantity()) );
    }

    @Override
    public BeverageGoodsInfo2 convertToVo(BeverageGoods beverageGoods) {
        if ( beverageGoods == null ) {
            return null;
        }

        BeverageGoodsInfo2 beverageGoodsInfo2 = new BeverageGoodsInfo2();

        List<BeverageOrder> list = beverageGoods.getBeverageOrders();
        if ( list != null ) {
            beverageGoodsInfo2.setBeverageOrders( new ArrayList<BeverageOrder>( list ) );
        }
        beverageGoodsInfo2.setGoodsDescription( beverageGoods.getGoodsDescription() );
        beverageGoodsInfo2.setGoodsID( beverageGoods.getGoodsID() );
        beverageGoodsInfo2.setGoodsImageName( beverageGoods.getGoodsImageName() );
        beverageGoodsInfo2.setGoodsName( beverageGoods.getGoodsName() );
        beverageGoodsInfo2.setGoodsPrice( beverageGoods.getGoodsPrice() );
        beverageGoodsInfo2.setGoodsQuantity( beverageGoods.getGoodsQuantity() );
        beverageGoodsInfo2.setGoodsStatus( beverageGoods.getGoodsStatus() );

        return beverageGoodsInfo2;
    }

    @Override
    public BeverageOrder convertToEntity(ShoppingCartGoodsInfo cartGoods, LocalDateTime date, String idCardNo, BeverageGoods goods) {
        if ( cartGoods == null && date == null && idCardNo == null && goods == null ) {
            return null;
        }

        BeverageOrderBuilder<?, ?> beverageOrder = BeverageOrder.builder();

        if ( cartGoods != null ) {
            beverageOrder.goodsBuyPrice( cartGoods.getBuyPrice() );
            beverageOrder.goodsID( cartGoods.getGoodsID() );
            beverageOrder.buyQuantity( cartGoods.getBuyQuantity() );
        }
        if ( date != null ) {
            beverageOrder.orderDate( date );
        }
        if ( idCardNo != null ) {
            beverageOrder.customerID( idCardNo );
        }
        if ( goods != null ) {
            beverageOrder.goods( goods );
        }

        return beverageOrder.build();
    }

    @Override
    public CheckoutReceipt convertToEntity(ShoppingCartGoodsInfo cartGoods, String goodsImage) {
        if ( cartGoods == null && goodsImage == null ) {
            return null;
        }

        CheckoutReceiptBuilder<?, ?> checkoutReceipt = CheckoutReceipt.builder();

        if ( cartGoods != null ) {
            checkoutReceipt.buyPrice( cartGoods.getBuyPrice() );
            checkoutReceipt.buyQuantity( cartGoods.getBuyQuantity() );
            checkoutReceipt.goodsName( cartGoods.getGoodsName() );
        }
        if ( goodsImage != null ) {
            checkoutReceipt.goodsImage( goodsImage );
        }

        return checkoutReceipt.build();
    }

    @Override
    public CheckoutCompleteInfo convertToEntity(String customerName, List<CheckoutReceipt> receiptList, Integer totalAmount) {
        if ( customerName == null && receiptList == null && totalAmount == null ) {
            return null;
        }

        CheckoutCompleteInfoBuilder<?, ?> checkoutCompleteInfo = CheckoutCompleteInfo.builder();

        if ( customerName != null ) {
            checkoutCompleteInfo.customerName( customerName );
        }
        if ( receiptList != null ) {
            List<CheckoutReceipt> list = receiptList;
            if ( list != null ) {
                checkoutCompleteInfo.receipt( new ArrayList<CheckoutReceipt>( list ) );
            }
        }
        if ( totalAmount != null ) {
            checkoutCompleteInfo.totalAmount( totalAmount );
        }

        return checkoutCompleteInfo.build();
    }

    @Override
    public GoodsSalesInfo convertToVo(PageUtility pageUtility, List<GoodsReportSales> goodsReportSalesList) {
        if ( pageUtility == null && goodsReportSalesList == null ) {
            return null;
        }

        GoodsSalesInfoBuilder<?, ?> goodsSalesInfo = GoodsSalesInfo.builder();

        if ( pageUtility != null ) {
            goodsSalesInfo.pageUtility( pageUtility );
        }
        if ( goodsReportSalesList != null ) {
            List<GoodsReportSales> list = goodsReportSalesList;
            if ( list != null ) {
                goodsSalesInfo.goodsReportSalesList( new ArrayList<GoodsReportSales>( list ) );
            }
        }

        return goodsSalesInfo.build();
    }
}
