package org.example.repository;

import org.example.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
    @Query("from Notes as n where n.userDtls.id =:uid")
    Page<Notes> findByNotesByUser(@Param("uid") int uid, Pageable p);  // return type Page, class Note, Custom Query inside param with usid and p for amount of data in each page

}

