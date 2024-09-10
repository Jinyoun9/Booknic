package com.booknic.repository;

import com.booknic.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminById(String id);
    boolean existsByEmail(String email);
    boolean existsById(String id);
    @Query("SELECT a.library FROM Admin a WHERE a.id = :id AND a.name = :name")
    String findLibraryByIdAndName(@Param("id") String id, @Param("name") String name);

}
