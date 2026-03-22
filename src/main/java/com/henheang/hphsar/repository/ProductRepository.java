package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.product.Product;
import com.henheang.hphsar.model.product.ProductRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductRepository {
    Product getProductValueExist(String name);

    Product addNewProduct(@Param("product") ProductRequest productRequest);


}