package com.book.inventory.app.controller;

import com.book.inventory.app.domain.Book;
import com.book.inventory.app.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("books", new Book());
        return "index";
    }

    @GetMapping("/addBook")
    public String showAddBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @GetMapping("/updateBook")
    public String showUpdateBook(Model model) {
        model.addAttribute("book", new Book());
        return "updatebook";
    }

    @GetMapping("/deleteBook")
    public String showDeleteBook(Model model) {
        model.addAttribute("book", new Book());
        return "deletebook";
    }

    @GetMapping("/search")
    public String searchBook(Model model) {
        model.addAttribute("books", new Book());
        return "search";
    }

    @GetMapping("/info")
    public String getInfo(Model model) {
        long totalBooks = bookRepo.count();
        double totalBooksPrice = bookRepo.findAll().stream().mapToDouble(Book::getPrice).sum();
        long totalAuthors = bookRepo.findAll().stream().map(Book::getAuthor).distinct().count();

        List<Book> books = bookRepo.findAll();

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalBooksPrice", totalBooksPrice);
        model.addAttribute("totalAuthors", totalAuthors);
        model.addAttribute("books", books);
        return "info";
    }

    @PostMapping("/addBook")
    public String saveBook(@ModelAttribute Book book, Model model) {
        try {
            bookRepo.save(book);
            model.addAttribute("message", "Book has been saved successfully");
        } catch (Exception e) {
            model.addAttribute("error", "failure" + e.getMessage());
        }
        return "addbook";
    }

    @PostMapping("/search")
    public String searchBook(@ModelAttribute Book book, Model model) {
        Book foundBook = bookRepo.findByBookid(book.getBookid());

        if (foundBook != null) {
            model.addAttribute("book", foundBook);
            model.addAttribute("errorMessage",  null);
        }else{
            model.addAttribute("book", null);
            model.addAttribute("errorMessage",  "Book not found");
        }
        return "search";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book, Model model) {

        Book existingBook = bookRepo.findByBookid(book.getBookid());

        if(existingBook != null){

            // update fields
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setPrice(book.getPrice());
            existingBook.setQuantity(book.getQuantity());
            existingBook.setLanguage(book.getLanguage());

            bookRepo.save(existingBook);

            model.addAttribute("book", existingBook);
            model.addAttribute("message", "Book updated successfully!");

        } else {
            model.addAttribute("error", "Book not found!");
            model.addAttribute("book", new Book());
        }

        return "updatebook";
    }

    @PostMapping("/deleteBook")
    public String deleteBook(@ModelAttribute Book book, Model model) {
        String bookid = book.getBookid();
        Book deletedBook = bookRepo.findByBookid(bookid);

        if (deletedBook != null) {
            bookRepo.delete(deletedBook);
            model.addAttribute("successMessage", "Book has been deleted successfully");
            model.addAttribute("book", deletedBook);
        } else  {
            model.addAttribute("book", null);
            model.addAttribute("errorMessage",  "Book not found");
        }
        return "deletebook";
    }


}
