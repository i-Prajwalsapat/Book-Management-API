package com.metapercept.bookapp.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metapercept.bookapp.entity.Book;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    private List<Book> books = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    @GetMapping
    public List<Book> getAllBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int limit) {
        return books.stream()
            .skip((long) page * limit)
            .limit(limit)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return books.stream()
            .filter(book -> book.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        book.setId(idCounter.incrementAndGet());
        books.add(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book newBook) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                book.setTitle(newBook.getTitle());
                book.setAuthor(newBook.getAuthor());
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}