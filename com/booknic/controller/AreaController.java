package com.booknic.controller;

import com.booknic.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/area")
@CrossOrigin(origins = "https://localhost:3000/")
@RequiredArgsConstructor
public class AreaController {

    @Autowired
    AreaRepository areaRepository;

    @GetMapping
    public ResponseEntity<?> getArea(){
        List<?>resAreaInfo = areaRepository.findByDtlnameAndDtlcode(null, null);
        return ResponseEntity.ok(resAreaInfo);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getDetailArea(@RequestParam(name = "name", required = true) String name,
                                           @RequestParam(name = "code", required = true) Integer code) throws URISyntaxException{
        List<?>resAreaInfo = areaRepository.findByNameAndCode(name, code);
        List<?> filteredList = resAreaInfo.subList(1, resAreaInfo.size());
        return ResponseEntity.ok(filteredList);
    }
}
