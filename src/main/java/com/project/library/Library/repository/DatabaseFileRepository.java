package com.project.library.Library.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.library.Library.model.DatabaseFile;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
    Optional<DatabaseFile> findByfileName(String fileName);

    @Query(nativeQuery = true,value = "select file_name from files where empid = :custid")
    Optional<String> un(@Param("custid") Long custid);

    @Query(nativeQuery = true,value = "select * from files where empid= :empid")
    Optional<DatabaseFile> fempid(@Param("empid") Long empid);

}
