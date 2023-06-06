package com.project.bookstore.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKS")
@ToString
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;
	
	@Column(nullable = false )
	private String bookTitle;
	
	@Column(nullable = false )
	private String author;
	
	@Column(nullable = false,length = 10000 )
	private String description;
	
	@Column(nullable = false )
	private double price;
		
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_category",
	joinColumns = @JoinColumn(name="bookId"),
	inverseJoinColumns = @JoinColumn(name="categoryId"))
	private List<Category> categories=new ArrayList<>();

}
