package com.benm.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue( strategy=GenerationType.IDENTITY )
    public Long id;
    public String name;
    public String url;

    protected Bookmark() {}

    public Bookmark( String name, String url ) {
        this.name = name;
        this.url  = url;
    }

    public Bookmark( Long id, String name, String url ) {
        this.id   = id;
        this.name = name;
        this.url  = url;
    }

    @Override
    public String toString() {
      return String.format(
        "Bookmark[id=%d, name='%s', url='%s']",
        id, name, url
      );
    }
}
