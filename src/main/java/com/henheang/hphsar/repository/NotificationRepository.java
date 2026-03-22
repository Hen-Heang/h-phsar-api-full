package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.notification.NotificationRetailer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationRepository {
    Integer createRetailerNotification(Integer retailerId, int notificationType, Integer orderId, String summery, String title, String description, boolean isRead);

    Integer createDistributorNotification(Integer distributorId, int notificationType, Integer orderId, String summery, String title, String description, boolean isRead);

    void deleteNotification(Integer check);

    boolean checkForRetailerNotification(Integer currentUserId);

    boolean checkForDistributorNotification(Integer currentUserId);

    List<NotificationRetailer> getRetailerUserAllNotification(Integer currentUserId);

    List<NotificationRetailer> getDistributorUserAllNotification(Integer currentUserId);

    boolean checkForRetailerNotificationById(Integer id, Integer currentUserId);

    boolean checkForDistributorNotificationById(Integer id, Integer currentUserId);

    String markAsReadRetailer(Integer id, Integer currentUserId);

    String markAsReadDistributor(Integer id, Integer currentUserId);

    boolean checkRetailerUnReadNotification(Integer currentUserId);

    boolean checkDistributorUnReadNotification(Integer currentUserId);

    String markAllNotificationAsReadRetailer(Integer currentUserId);

    String markAllNotificationAsReadDistributor(Integer currentUserId);

}