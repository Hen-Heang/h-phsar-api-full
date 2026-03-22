package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.invoice.Invoice;
import com.henheang.hphsar.model.order.Order;
import com.henheang.hphsar.model.order.OrderDetail;
import com.henheang.hphsar.model.product.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDistributorRepository {

    List<Order> getAllOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getAllOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getNewOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getNewOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getPreparingOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getPreparingOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getDispatchingOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getDispatchingOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getConfirmingOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getConfirmingOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getCompleteOrderCurrentUserSortByCreatedDateASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<Order> getCompleteOrderCurrentUserSortByCreatedDateDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    Integer getCurrentStoreTotalPreparingOrder(Integer storeId);

    Double getTotalOrderAmount(Integer orderId);

    String getStatusByStatusId(Integer statusId);

    Integer getCurrentStoreTotalOrder(Integer storeId);

    Integer getCurrentStoreTotalNewOrder(Integer storeId);

    Integer getTotalDispatchingOrder(Integer storeId);

    Integer getTotalConfirmingOrder(Integer storeId);

    Integer getTotalCompleteOrder(Integer storeId);

    Boolean checkOrderExist(Integer orderId, Integer storeId);

    Boolean checkForPendingOrder(Integer orderId);

    String acceptPendingOrder(Integer orderId);

    String declinePendingOrder(Integer orderId);

    Boolean checkForPreparingOrder(Integer orderId);

    String finishPreparing(Integer orderId);

    Boolean checkForDispatchOrder(Integer orderId);

    String orderDelivered(Integer orderId);

    OrderDetail getOrderDetailsByOrderId(Integer id);

    ProductOrder getProductOrder(Integer orderId);

    ProductOrder getProductOrderForOrderDetail(Integer orderId);

    List<ProductOrder> getProductOrderByOrderId(Integer orderId);

    Order getOrderByOrderId(Integer orderId);

    Boolean checkForCompleteOrder(Integer orderId);

    Invoice getInvoiceByOrderId(Integer orderId);

    Integer productEligibleCount(Integer orderId);

    Integer getProductDetailCount(Integer orderId);

    void deductStock(Integer orderId, Integer productId, Integer storeId);

    List<Integer> getAllProductIdFromProductDetails(Integer orderId);

    Integer getDistributorIdByOrderId(Integer cartId);
}