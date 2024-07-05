package com.booknic.api;

import lombok.Getter;

public class LibApi {

    @Getter
    public enum EndPoint{
        LIBSRCH("/libSrch"),
        ITEMSRCH("/itemSrch"),
        LOANITEMSRCH("/loanItemSrch"),
        RECOMMANDLIST("/recommandList"),
        SRCHDTLLIST("srchDtlList"),
        USAGEANALYSISLIST("/usageAnalysisList"),
        LOANITEMSRCHBYLIB("/loanItemSrchByLib"),
        USAGETREND("/usageTrend"),
        BOOKEXIST("/bookExist"),
        HOTTREND("/hotTrend"),
        LIBSRCHBYBOOK("/libSrchByBook"),
        EXTENDLIBSRCH("/extends/libSrch"),
        EXTENDLOANITEMSRCHBYLIB("extends/loanItemSrchByLib"),
        SRCHBOOKS("/srchBooks"),
        READQT("/readQt"),
        MONTHLYKEYWORDS("/monthlyKeywords");




        private String endpoint;
        EndPoint(String endpoint) { this.endpoint = endpoint; }
        public String getEndPoint() {return endpoint;}
    }

    @Getter
    public enum Param{
        BASEURL("http://data4library.kr/api"),
        FORMAT("format=json"),
        AUTHKEY("authKey=ccf8bde5893bd49f1ddb4d365522f4aa407dd0c303d6adaaabff27db12594ede"),
        PAGENO("pageNo="),
        PAGESIZE("pageSize="),
        LIBCODE("libCode="),
        STARTDT("startDt="),
        ENDDT("endDt="),
        TYPE("type="), // mania = recommandList(마니아), reader = recommandList
        GENDER("gender="),
        FROM_AGE("from_age="),
        TO_AGE("to_age="),
        REGION("region="),
        DTL_REGION("dtl_region="),
        ADDCODE("addCode="),
        KDC("kdc="), // 대주제
        DTLKDC("dtl_kdc="), // 대주제
        ISBN13("isbn13="),
        SEARCHDT("searchDt="),
        KEYWORD("keyword=");


        private String param;
        Param(String param) {this.param = param;}
        public String getParam() {return param;}
    }
}
