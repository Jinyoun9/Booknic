package com.booknic.controller;

import static com.booknic.api.LibApi.EndPoint.*;

import com.booknic.api.LibApi;
import com.booknic.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LibraryController {

    @GetMapping("/lib")
    public ResponseEntity<?> getLibrary(@RequestParam Map<String, String> params) throws URISyntaxException {

        String endPoint = "/libSrch";
        List<?> libInfo = LibraryService.getLibInfo(params, endPoint);
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/item")
    public ResponseEntity<?> getItem(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(ITEMSRCH.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/loanitem")
    public ResponseEntity<?> getLoanItem(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(LOANITEMSRCH.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommend(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(RECOMMANDLIST.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getDetail(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(SRCHDTLLIST.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/analysis")
    public ResponseEntity<?> getAnalysis(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(USAGEANALYSISLIST.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/loamitemlib")
    public ResponseEntity<?> getLoanItemByLib(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(LOANITEMSRCHBYLIB.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/trend")
    public ResponseEntity<?> getTrend(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(USAGETREND.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/exist")
    public ResponseEntity<?> getExist(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(BOOKEXIST.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/hot")
    public ResponseEntity<?> getHotTrend(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(HOTTREND.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/libsrchbook")
    public ResponseEntity<?> getLibSrchByBook(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(LIBSRCHBYBOOK.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/extends/libsrch")
    public ResponseEntity<?> getExLibSrch(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(EXTENDLIBSRCH.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/extends/loanitemlib")
    public ResponseEntity<?> getExLoanItemByLib(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(EXTENDLOANITEMSRCHBYLIB.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/srchbooks")
    public ResponseEntity<?> getSrchBooks(@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(SRCHBOOKS.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
    @GetMapping("/readQt")
    public ResponseEntity<?> getReadQt (@RequestParam Map<String, String> params) throws URISyntaxException {

        LibApi.EndPoint endPoint = LibApi.EndPoint.valueOf(READQT.getEndPoint());
        List<?> libInfo = LibraryService.getLibInfo(params, String.valueOf(endPoint));
        return ResponseEntity.ok().body(libInfo);
    }
}
