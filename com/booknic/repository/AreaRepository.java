package com.booknic.repository;

import com.booknic.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<?> findByNameAndCode(String name, Integer code);
    List<?> findByDtlnameAndDtlcode(String DtlName, Integer DtlCode);
}
