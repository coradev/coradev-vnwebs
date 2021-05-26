package com.coradev.vnwebs.repository;

import com.coradev.vnwebs.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query("select p from Post p where p.recommend = true ")
    List<Post> findTop(Pageable pageable);

    @Query("select p from Post p where p.title like ?1 or p.description like ?1")
    Page<Post> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id =?1")
    int updateViews(Long id);

    @Query("select function('date_format',p.updateTime, '%Y') as year from Post p group by function('date_format',p.updateTime, '%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select p from Post p where function('date_format',p.updateTime, '%Y') = ?1")
    List<Post> findByYear(String year);
}
