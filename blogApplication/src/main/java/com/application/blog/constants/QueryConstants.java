package com.application.blog.constants;

public class QueryConstants {

	public static final String SEARCH_POSTS_BY_TITLE_CONTAINING= "select * from posts where post_title like %:keyword%";
	
}
