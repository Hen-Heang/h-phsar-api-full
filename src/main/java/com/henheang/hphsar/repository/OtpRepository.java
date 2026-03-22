package com.henheang.hphsar.repository;

import com.henheang.hphsar.model.appUser.AppUser;
import com.henheang.hphsar.model.otp.Otp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * OtpRepository — OTP Database Operations
 *
 * All SQL is now in: resources/mapper/OtpMapper.xml
 *
 * This interface only declares method signatures.
 * MyBatis matches each method here to a SQL block in OtpMapper.xml
 * using the method name as the id.
 *
 * Example:
 *   Java:  Otp getDistributorOtpByEmail(String email)
 *   XML:   <select id="getDistributorOtpByEmail" ...>SELECT ...</select>
 */
@Mapper
public interface OtpRepository {

    // ─── CHECK IF ACCOUNT IS VERIFIED ──────────────────────────────────────────
    // Returns user only if is_verified = TRUE — null means not yet verified
    AppUser checkIfActivatedByDistributorEmail(String email);
    AppUser checkIfActivatedByRetailerEmail(String email);

    // ─── GET USER BY EMAIL ─────────────────────────────────────────────────────
    // Used to find which account the email belongs to before generating OTP
    AppUser getUserDistributorByEmail(String email);
    AppUser getUserRetailerByEmail(String email);

    // ─── GENERATE OTP ──────────────────────────────────────────────────────────
    // @Param is required because the method has multiple parameters
    // Without @Param, MyBatis cannot tell which value belongs to which #{placeholder}
    Otp generateDistributorOtp(@Param("currentUserId") Integer currentUserId,
                                @Param("otpNumber") Integer otpNumber,
                                @Param("email") String email,
                                @Param("time") java.sql.Timestamp time);

    Otp generateRetailerOtp(@Param("currentUserId") Integer currentUserId,
                             @Param("otpNumber") Integer otpNumber,
                             @Param("email") String email,
                             @Param("time") java.sql.Timestamp time);

    // ─── GET LATEST OTP ────────────────────────────────────────────────────────
    // Fetches the most recently generated OTP for a given email
    Otp getDistributorOtpByEmail(String email);
    Otp getRetailerOtpByEmail(String email);

    // ─── VERIFY ACCOUNT ────────────────────────────────────────────────────────
    // Sets is_verified = true, returns '1' on success
    String verifyDistributor(String email);
    String verifyRetailer(String email);

    // ─── DELETE OTP AFTER USE ──────────────────────────────────────────────────
    // Deletes OTP record after it has been used — prevents reuse
    void deleteDistributorOtp(String email);
    void deleteRetailerOtp(String email);
}