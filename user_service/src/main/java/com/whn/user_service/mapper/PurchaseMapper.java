package com.whn.user_service.mapper;

import com.whn.user_service.domain.Purchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wangRich
* @description 针对表【purchase】的数据库操作Mapper
* @createDate 2022-12-26 12:00:40
* @Entity com.whn.user_service.domain.Purchase
*/
@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {

}




