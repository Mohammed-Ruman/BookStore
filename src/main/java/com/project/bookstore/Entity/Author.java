package com.project.bookstore.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "authors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer authorId;
	
	@Column(name = "author_name", nullable = false)
	private String authorName;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Book> books=new ArrayList<>();
	
	
	
	
	
	
}
