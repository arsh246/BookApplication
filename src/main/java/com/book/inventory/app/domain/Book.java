package com.book.inventory.app.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookdata")
public class Book {

    @Id
    private String id;

    private String bookid;
    private String title;
    private String author;
    private String publisher;
    private String publicationYear;
    private double price;
    private String quantity;
    private String language;

    @Transient
    private String total;
    @Transient
    private String totalauthor;
    @Transient
    private String totalprice;
}
