package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.distributor.Distributor;
import com.henheang.hphsar.model.distributor.DistributorRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DistributorProfileRepository {


    //for getting addition phone number by distributor info id
    List<String> getAdditionalPhoneNumberByDistributorInfoId(Integer id);

    Distributor getUserProfile(Integer currentUserId);

    //create distributor info profile and return distributor info id in real time
    Distributor insertDistributorInfo(Integer currentUserId, @Param("dis") DistributorRequest distributorRequest);

    Distributor updateUserProfile(Integer currentUserId, @Param("dis") DistributorRequest distributorRequest);

    void addAdditionalPhoneNumber(Integer infoId, String additionalPhoneNumber);

    boolean checkIfUserProfileIsCreated(Integer currentUserId);

    void deleteAdditioanlPhoneNumber(Integer infoId);

    Integer getDistributorInfoId(Integer currentUserId);

    boolean checkIfAdditionalPhoneNumberExist(String additionalPhoneNumber);

    Integer getDistributorIdByStoreId(Integer storeId);
}