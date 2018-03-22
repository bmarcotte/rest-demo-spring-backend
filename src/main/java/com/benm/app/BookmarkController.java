package com.benm.app;

import java.lang.Iterable;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/rest" )
public class BookmarkController {
	private final BookmarkRepository bookmarkRepository;

	@Autowired
	BookmarkController( BookmarkRepository bookmarkRepository ) {
		this.bookmarkRepository = bookmarkRepository;
	}

	@RequestMapping( method = RequestMethod.GET, value = "/bookmark/count" )
	public long readBookmark() {
		return this.bookmarkRepository.count();
	}

	@RequestMapping( method = RequestMethod.GET, value = "/bookmark/{bookmarkId}" )
	Bookmark readBookmark( @PathVariable Long bookmarkId ) {
		return this.bookmarkRepository.findById( bookmarkId ).orElse( new Bookmark() );
	}

	@RequestMapping( method = RequestMethod.GET, value = "/bookmarks" )
	public Map<String, Object> readBookmarks( HttpServletResponse response ) {
		response.setHeader( "X-Total-Count", "" + this.bookmarkRepository.count() );

		Map<String,Object> results = new HashMap<String, Object>();
		results.put( "bookmarks", this.bookmarkRepository.findAll() );

		return results;
	}
}
