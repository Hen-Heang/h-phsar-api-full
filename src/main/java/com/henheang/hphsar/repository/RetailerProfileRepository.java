package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.retailer.Retailer;
import com.henheang.hphsar.model.retailer.RetailerRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RetailerProfileRepository {


    //get retailer info id in real time
    Integer createRetailerProfile(Integer currentUserId, @Param("re") RetailerRequest retailerRequest);

    void insertAdditinalPhoneNumber(Integer retailerInfoId, String additionalPhoneNumber);

    Retailer getRetailerProfile(Integer currentUserId);

    List<String> getAdditionalPhoneNumberByRetailerInfoId(Integer id);

    void updateRetailerProfile(Integer currentUserId, @Param("re") RetailerRequest retailerRequest);

    Integer getRetailerInfoId(Integer currentUserId);

    void deleteAdditionalPhoneNumber(Integer retailerInfoId);

    boolean checkIfRetailerProfileIsAlreadyCreated(Integer currentUserId);

    String getRetailerNameById(Integer retailerId);

    String getRetailerImageById(Integer retailerId);

    String getRetailerAddressById(Integer retailerId);

    String getRetailerPhoneById(Integer retailerId);

    String getRetailerEmailById(Integer id);
}