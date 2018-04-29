package com.benm.app;

import java.lang.Iterable;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

  @RequestMapping(
    method = RequestMethod.GET,
    value  = "/bookmark/count"
  )
  public long count_bookmarks() {
    return this.bookmarkRepository.count();
  }

  @RequestMapping(
    method = RequestMethod.GET,
    value  = "/bookmark/{id}"
  )
  Bookmark fetch_bookmark( @PathVariable Long id ) {
    return this.bookmarkRepository.findById( id ).orElse( new Bookmark() );
  }

  @RequestMapping(
    method = RequestMethod.GET,
    value  = "/bookmarks"
  )
  public Map<String, Object> fetch_bookmarks( HttpServletResponse response ) {
    response.setHeader( "X-Total-Count", "" + this.bookmarkRepository.count() );

    Map<String,Object> results = new HashMap<String, Object>();
    results.put( "bookmarks", this.bookmarkRepository.findAll() );

    return results;
  }

  @RequestMapping(
    method   = RequestMethod.POST,
    value    = "/bookmark",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public Map<String, Object> add_bookmark_form(
    @RequestParam( value = "name" ) String name,
    @RequestParam( value = "url"  ) String url
  ) {
    return this.save_bookmark(
      new Bookmark( name, url )
    );
  }

  @RequestMapping(
    method = RequestMethod.POST,
    value  = "/bookmark"
  )
  public Map<String, Object> add_bookmark( @RequestBody Bookmark input ) {
    return this.save_bookmark( input );
  }

  @RequestMapping(
    method   = RequestMethod.PUT,
    value    = "/bookmark",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public Map<String, Object> update_bookmark_form(
    @RequestParam( value = "id"   ) Long id,
    @RequestParam( value = "name" ) String name,
    @RequestParam( value = "url"  ) String url
  ) {
    return this.save_bookmark(
      new Bookmark( id, name, url )
    );
  }

  @RequestMapping(
    method = RequestMethod.PUT,
    value = "/bookmark"
  )
  public Map<String, Object> update_bookmark( @RequestBody Bookmark input ) {
    return this.save_bookmark( input );
  }

  public Map<String, Object> save_bookmark( Bookmark input ) {
    int rows_affected = 0;
    Bookmark saved_bookmark = this.bookmarkRepository.save( input );
    if ( saved_bookmark != null && saved_bookmark.id != null ) {
      rows_affected = 1;
    }

    Map<String,Object> results = new HashMap<String, Object>();
    results.put( "rows_affected", rows_affected );
    results.put( "result", saved_bookmark );

    return results;
  }

  @RequestMapping(
    method = RequestMethod.DELETE,
    value  = "/bookmark/{id}"
  )
  public Map<String, Object> delete_bookmark_path( @PathVariable Long id ) {
    return this.delete_bookmark( id );
  }

  @RequestMapping(
    method = RequestMethod.DELETE,
    value = "/bookmark"
  )
  public Map<String, Object> delete_bookmark_json( @RequestBody Bookmark input ) {
    return this.delete_bookmark( input.id );
  }

  public Map<String, Object> delete_bookmark( Long id ) {
    int rows_affected = 0;
    if ( id != null && this.bookmarkRepository.existsById( id ) ) {
      try {
        this.bookmarkRepository.deleteById( id );
        rows_affected = 1;
      }
      catch ( Exception e ) {
        System.err.println( "Error while deleting record #" + id + ": " + e );
      }
    }

    Map<String,Object> results = new HashMap<String, Object>();
    results.put( "rows_affected", rows_affected );

    return results;
  }
}
