package com.project.library.Library.repository;

import com.project.library.Library.model.Books;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query(nativeQuery = true,value = "select b.name from Books b join Customer c on b.custid = c.id where b.custid=:adp")
    Optional<Books> findCustomer(@Param("adp") long adp);

    @Query(nativeQuery = true,value = "select * from books where empid = :empid")
    List<Books> fd(@Param("empid") long empid);

    @Query(nativeQuery = true,value = "select * from books where empid = :empid and name = :name")
    Optional<Books> fdi(@Param("empid") long empid,@Param("name") String name);

    @Query(nativeQuery = true,value = "select * from books where category= :category")
    Optional<Books> fC(@Param("category") String category);

}
