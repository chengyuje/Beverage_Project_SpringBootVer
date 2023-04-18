package com.ecommerce.mapper;

import com.ecommerce.Info.BeverageGoodsInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo;
import com.ecommerce.Info.ShoppingCartGoodsInfo.ShoppingCartGoodsInfoBuilder;
import com.ecommerce.entity.BeverageGoods;
import com.ecommerce.entity.BeverageGoods.BeverageGoodsBuilder;
import com.ecommerce.vo.BeverageGoodsVo;
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
public class BeverageGoodsMapperImpl implements BeverageGoodsMapper {

    @Override
    public BeverageGoods convertToEntity(BeverageGoodsVo beverageGoodsVo) {
        if ( beverageGoodsVo == null ) {
            return null;
        }

        BeverageGoodsBuilder<?, ?> beverageGoods = BeverageGoods.builder();

        beverageGoods.goodsDescription( beverageGoodsVo.getDescription() );
        beverageGoods.goodsPrice( beverageGoodsVo.getPrice() );
        beverageGoods.goodsQuantity( beverageGoodsVo.getQuantity() );
        beverageGoods.goodsImageName( beverageGoodsVo.getImageName() );
        beverageGoods.goodsStatus( beverageGoodsVo.getStatus() );
        beverageGoods.goodsName( beverageGoodsVo.getGoodsName() );

        return beverageGoods.build();
    }

    @Override
    public BeverageGoodsInfo convertToVo(BeverageGoods beverageGoods) {
        if ( beverageGoods == null ) {
            return null;
        }

        BeverageGoodsInfo beverageGoodsInfo = new BeverageGoodsInfo();

        beverageGoodsInfo.setDescription( beverageGoods.getGoodsDescription() );
        if ( beverageGoods.getGoodsPrice() != null ) {
            beverageGoodsInfo.setPrice( beverageGoods.getGoodsPrice() );
        }
        if ( beverageGoods.getGoodsQuantity() != null ) {
            beverageGoodsInfo.setQuantity( beverageGoods.getGoodsQuantity() );
        }
        beverageGoodsInfo.setImageName( beverageGoods.getGoodsImageName() );
        beverageGoodsInfo.setStatus( beverageGoods.getGoodsStatus() );
        if ( beverageGoods.getGoodsID() != null ) {
            beverageGoodsInfo.setGoodsID( beverageGoods.getGoodsID().longValue() );
        }
        beverageGoodsInfo.setGoodsName( beverageGoods.getGoodsName() );

        return beverageGoodsInfo;
    }

    @Override
    public ShoppingCartGoodsInfo checkGoodsInfo(BeverageGoods dbGoodsInfo, int cartGoodsQuantity, int buyQuantity) {
        if ( dbGoodsInfo == null ) {
            return null;
        }

        ShoppingCartGoodsInfoBuilder<?, ?> shoppingCartGoodsInfo = ShoppingCartGoodsInfo.builder();

        if ( dbGoodsInfo != null ) {
            if ( dbGoodsInfo.getGoodsPrice() != null ) {
                shoppingCartGoodsInfo.buyPrice( dbGoodsInfo.getGoodsPrice() );
            }
            shoppingCartGoodsInfo.description( dbGoodsInfo.getGoodsDescription() );
            if ( dbGoodsInfo.getGoodsID() != null ) {
                shoppingCartGoodsInfo.goodsID( dbGoodsInfo.getGoodsID().longValue() );
            }
            shoppingCartGoodsInfo.goodsName( dbGoodsInfo.getGoodsName() );
        }
        shoppingCartGoodsInfo.buyQuantity( utility.renewCartGoodsQuantity(dbGoodsInfo.getGoodsQuantity(),cartGoodsQuantity,buyQuantity) );

        return shoppingCartGoodsInfo.build();
    }

    @Override
    public void updateEntity(BeverageGoodsVo beverageGoodsVo, BeverageGoods beverageGoods) {
        if ( beverageGoodsVo == null ) {
            return;
        }

        if ( beverageGoodsVo.getDescription() != null ) {
            beverageGoods.setGoodsDescription( beverageGoodsVo.getDescription() );
        }
        if ( beverageGoodsVo.getImageName() != null ) {
            beverageGoods.setGoodsImageName( beverageGoodsVo.getImageName() );
        }
        if ( beverageGoodsVo.getStatus() != null ) {
            beverageGoods.setGoodsStatus( beverageGoodsVo.getStatus() );
        }
        if ( beverageGoodsVo.getGoodsName() != null ) {
            beverageGoods.setGoodsName( beverageGoodsVo.getGoodsName() );
        }

        beverageGoods.setGoodsPrice( utility.goodsPriceCheck(beverageGoodsVo.getPrice(),beverageGoods.getGoodsPrice()) );
        beverageGoods.setGoodsQuantity( utility.goodsQuantityCheck(beverageGoodsVo.getQuantity(),beverageGoods.getGoodsQuantity()) );
    }

    @Override
    public List<BeverageGoodsInfo> convertToVo(List<BeverageGoods> goodsList) {
        if ( goodsList == null ) {
            return null;
        }

        List<BeverageGoodsInfo> list = new ArrayList<BeverageGoodsInfo>( goodsList.size() );
        for ( BeverageGoods beverageGoods : goodsList ) {
            list.add( convertToVo( beverageGoods ) );
        }

        return list;
    }
}
