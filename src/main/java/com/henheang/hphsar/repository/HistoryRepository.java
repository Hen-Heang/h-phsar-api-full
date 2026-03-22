package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.history.ImportHistory;
import com.henheang.hphsar.model.history.OrderDetailHistory;
import com.henheang.hphsar.model.history.OrderHistory;
import com.henheang.hphsar.model.product.ProductOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface HistoryRepository {

    List<ImportHistory> getProductImportHistoryASC(Integer pageNumber, Integer pageSize, Integer storeId);

    List<ImportHistory> getProductImportHistoryDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    Integer getImportDetailQty(Integer productId, Date date, Integer storeId);

    Double getImportDetailPrice(Integer productId, Date date, Integer storeId);

    Double getImportDetailTotal(Integer productId, Date date, Integer storeId);

    String getCategoryNameById(Integer id, Integer test);

    String getProductNameByid(Integer id);

    Integer findTotalImportDetail(Integer storeId);

    Integer findTotalOrderHistory(Integer storeId);

    List<OrderDetailHistory> getOrderHistoryASC(Integer pageNumber, Integer pageSize, Integer storeId);

    ProductOrder getProductOrderForOrderDetail(Integer orderId);

    OrderHistory getOrderByOrderId(Integer orderId);

    List<OrderDetailHistory> getOrderHistoryDESC(Integer pageNumber, Integer pageSize, Integer storeId);

    Integer findTotalRetailerOrder(Integer currentUserId);

    List<OrderDetailHistory> getRetailerOrderHistoryASC(Integer pageNumber, Integer pageSize, Integer currentUserId);

    List<OrderDetailHistory> getRetailerOrderHistoryDESC(Integer pageNumber, Integer pageSize, Integer currentUserId);

    OrderHistory getRetailerOrderByOrderId(Integer orderId);

    Integer findTotalRetailerDraft(Integer currentUserId);

    List<OrderDetailHistory> getRetailerDraftASC(Integer pageNumber, Integer pageSize, Integer currentUserId);

    List<OrderDetailHistory> getRetailerDraftDESC(Integer pageNumber, Integer pageSize, Integer currentUserId);

    boolean checkDraftById(Integer id);

    Integer deleteDraftById(Integer id);

    Integer updateDraftById(Integer id);

    OrderDetailHistory getDraftHistory(Integer id, Integer currentUserId);
}