package com.booknic.service;

import static com.booknic.api.LibApi.EndPoint.*;
import static com.booknic.api.LibApi.Param.*;
import static com.booknic.util.URLBuilder.fetch;

import com.booknic.util.URLBuilder.*;
import com.booknic.api.LibApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    public static List<?> getLibInfo(Integer pageNo, Integer pageSize, String endpoint) throws URISyntaxException {

        StringBuilder reqUrlBuilder = new StringBuilder(endpoint).append("?");

        if (pageNo != null) {
            reqUrlBuilder.append(PAGENO.getParam()).append(pageNo).append("&");
        }
        if (pageSize != null) {
            reqUrlBuilder.append(PAGESIZE.getParam()).append(pageSize).append("&");
        }

        String reqUrl = reqUrlBuilder.toString();
        List<?> libInfos = fetch(reqUrl);
        return libInfos;
    }
    public static List<?> getLibInfo(Map<String, String> params, String endPoint) throws URISyntaxException {

        StringBuilder reqUrlBuilder = new StringBuilder(endPoint).append("?");

        Integer libCode = params.get("libCode") != null ? Integer.valueOf(params.get("libCode")) : null;
        String type = params.get("type");
        String startDt = params.get("startDt");
        String endDt = params.get("endDt");
        String keyword = params.get("keyword");
        String searchDt = params.get("searchDt");
        Integer pageNo = params.get("pageNo") != null ? Integer.valueOf(params.get("pageNo")) : 1;  // 기본 값 1 설정
        Integer pageSize = params.get("pageSize") != null ? Integer.valueOf(params.get("pageSize")) : 10;  // 기본 값 10 설정
        Integer gender = params.get("gender") != null ? Integer.valueOf(params.get("gender")) : null;
        Integer from_age = params.get("from_age") != null ? Integer.valueOf(params.get("from_age")) : null;
        Integer to_age = params.get("to_age") != null ? Integer.valueOf(params.get("to_age")) : null;
        Integer region = params.get("region") != null ? Integer.valueOf(params.get("region")) : null;
        Integer dtl_region = params.get("dtl_region") != null ? Integer.valueOf(params.get("dtl_region")) : null;
        Integer addCode = params.get("addCode") != null ? Integer.valueOf(params.get("addCode")) : null;
        Integer kdc = params.get("kdc") != null ? Integer.valueOf(params.get("kdc")) : null;
        Integer dtl_kdc = params.get("dtl_kdc") != null ? Integer.valueOf(params.get("dtl_kdc")) : null;


        if (libCode != null) {
            reqUrlBuilder.append(LIBCODE.getParam()).append(libCode).append("&");
        }
        if (type != null) {
            reqUrlBuilder.append(TYPE.getParam()).append(type).append("&");
        }
        if (startDt != null) {
            reqUrlBuilder.append(STARTDT.getParam()).append(startDt).append("&");
        }
        if (endDt != null) {
            reqUrlBuilder.append(ENDDT.getParam()).append(endDt).append("&");
        }
        if (keyword != null) {
            reqUrlBuilder.append(KEYWORD.getParam()).append(keyword).append("&");
        }
        if (searchDt != null) {
            reqUrlBuilder.append(SEARCHDT.getParam()).append(searchDt).append("&");
        }
        if (pageNo != null) {
            reqUrlBuilder.append(PAGENO.getParam()).append(pageNo).append("&");
        }
        if (pageSize != null) {
            reqUrlBuilder.append(PAGESIZE.getParam()).append(pageSize).append("&");
        }
        if (gender != null) {
            reqUrlBuilder.append(GENDER.getParam()).append(gender).append("&");
        }
        if (from_age != null) {
            reqUrlBuilder.append(FROM_AGE.getParam()).append(from_age).append("&");
        }
        if (to_age != null) {
            reqUrlBuilder.append(TO_AGE.getParam()).append(to_age).append("&");
        }
        if (region != null) {
            reqUrlBuilder.append(REGION.getParam()).append(region).append("&");
        }
        if (dtl_region != null) {
            reqUrlBuilder.append(DTL_REGION.getParam()).append(dtl_region).append("&");
        }
        if (addCode != null) {
            reqUrlBuilder.append(ADDCODE.getParam()).append(addCode).append("&");
        }
        if (kdc != null) {
            reqUrlBuilder.append(KDC.getParam()).append(kdc).append("&");
        }
        if (dtl_kdc != null) {
            reqUrlBuilder.append(DTLKDC.getParam()).append(dtl_kdc).append("&");
        }

        String reqUrl = reqUrlBuilder.toString();
        List<?> searchInfos = fetch(reqUrl);
        return searchInfos;
    }


}
