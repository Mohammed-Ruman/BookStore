package com.project.bookstore.Payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookApiResponse {
	private String bookTitle;
	private String authorName;
	private String description;
	private Integer numberOfCopiesSold;

}
