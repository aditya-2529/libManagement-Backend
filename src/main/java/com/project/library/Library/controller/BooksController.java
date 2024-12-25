package com.project.library.Library.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.library.Library.exception.ResourceNotFoundException;
import com.project.library.Library.model.Books;
import com.project.library.Library.model.DatabaseFile;
import com.project.library.Library.model.Employee;
import com.project.library.Library.repository.BooksRepository;
import com.project.library.Library.repository.DatabaseFileRepository;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/books")
public class BooksController {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private DatabaseFileRepository databaseFileRepository;
    @GetMapping
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }
    @PostMapping
    public Books createBooks(@RequestBody Books Books) {
        return booksRepository.save(Books);
    }
    @GetMapping("/edit/{id}")
    public List<Books> fd(@PathVariable Long id) {
        List<Books> books = booksRepository.fd(id);
        return books;
    }
    @GetMapping("/edit-book/{id}")
    public ResponseEntity<Books> getBooksById(@PathVariable Long id) {
        Books books = booksRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Books not exist with id :" + id));
        return ResponseEntity.ok(books);
    }
    @PutMapping("/mbooks/{id}")
    public ResponseEntity<Books> up(@PathVariable Long id) {
        Books books = booksRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Books not exist with id :" + id));
        books.setCustid(null);

        booksRepository.save(books);

        return ResponseEntity.ok(books);
    }
    @GetMapping("{adp}")
    public ResponseEntity<Books> findCustomerwithBookId(@PathVariable String adp) {
        Books b = booksRepository.findCustomer(Long.parseLong(adp))
        .orElseThrow(() -> new ResourceNotFoundException("Books not found"));
        return ResponseEntity.ok(b);
    }
    @PutMapping("{id}")
    public ResponseEntity<Books> updateBooks(@PathVariable Long id, @RequestBody Books Books) {
        Books existingBooks = booksRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Books does not exists with id: "+id));
        existingBooks.setName(Books.getName());
        existingBooks.setTitle(Books.getTitle());
        existingBooks.setEmpid(Books.getEmpid());
        existingBooks.setCustid(Books.getCustid());

        booksRepository.save(existingBooks);
        return ResponseEntity.ok(existingBooks);
    }
    @GetMapping("/filter/{category}")
    public ResponseEntity<Books> findCategory(@PathVariable String category){
        Books b = booksRepository.fC(category).orElseThrow(() -> {
            throw new ResourceNotFoundException("No category name: "+category);
        });
        return ResponseEntity.ok(b);
    }
    @DeleteMapping("{id}/{name}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable long id,@PathVariable String name){

        Books books = booksRepository.fdi(id,name)
                .orElseThrow(() -> new ResourceNotFoundException("Books not exist with id: " + id));
        DatabaseFile file = databaseFileRepository.fempid(id).orElseThrow(() -> new ResourceNotFoundException("File not exist with empid: " + id));
        booksRepository.delete(books);
        databaseFileRepository.delete(file);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 

    }
}
