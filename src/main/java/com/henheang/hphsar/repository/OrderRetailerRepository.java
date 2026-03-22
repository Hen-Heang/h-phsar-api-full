package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.Cart.CartSummery;
import com.henheang.hphsar.model.order.Order;
import com.henheang.hphsar.model.product.ProductOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderRetailerRepository {

    Boolean checkForCart(Integer storeId, Integer retailerId);

    Boolean checkForCartOrPending(Integer storeId, Integer retailerId);

    Integer createCart(Integer storeId, Integer retailerId);

    Integer getOrderIdByStoreIdAndRetailerId(Integer storeId, Integer retailerId);

    Boolean checkStock(Integer productId, Integer qty);

    Double getProductPrice(Integer productId);

    String addProductToCart(Integer orderId, Integer storeProductId, Integer qty, Double price);

    ProductOrder getProductFromCart(Integer orderId, Integer storeProductId);

    boolean productIsInCart(Integer storeProductId, Integer orderId);

    String updateProductQtyFromCart(Integer storeProductId, Integer orderId, Integer qty, Double price);

    String removeProductFromCart(Integer storeProductId, Integer orderId);

    Order getOrderByOrderId(Integer id);

    List<ProductOrder> getProductOrderByOrderId(Integer orderId, @Param("pageNumber") Integer pageNumber, @Param("pageSize") Integer pageSize);

    boolean isCartExist(Integer orderId);

    Integer getTotalProduct(Integer orderId);

    String cancelCart(Integer orderId);

    String saveToDraft(Integer orderId);

    String confirmOrder(Integer orderId);

    List<Order> getUserOrderActivitiesOrderByDateASC(Integer retailerId, Integer pageNumber, Integer pageSize);

    List<Order> getUserOrderActivitiesOrderByDateDESC(Integer retailerId, Integer pageNumber, Integer pageSize);

    Integer getTotalOrder(Integer retailerId);

    boolean orderIsConfirming(Integer id);

    boolean checkOrderExist(Integer id);

    String confirmTransaction(Integer id);

    boolean checkOrderIsComplete(Integer id);

    Integer getStoreProductId(Integer storeId, Integer productId);

    String deleteOrderDetail(Integer orderId);

    String deleteOrder(Integer orderId);

    boolean checkUserInfo(Integer retailerId);

    boolean checkForAnyCart(Integer currentUserId);

    List<CartSummery> getAllCarts(Integer currentUserId);

    Integer getUserCartId(Integer retailerId);

    Integer getStoreIdByOrderId(Integer cartId);

    boolean checkForCartInOtherStore(Integer storeId, Integer retailerId);

    boolean checkForDispatchingOrder(Integer id, Integer currentUserId);
}