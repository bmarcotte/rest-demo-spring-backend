package com.benm.app;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
}
