package com.example.redis.springbootrediscache.repository;

import com.example.redis.springbootrediscache.model.URLMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface URLMappingRepository extends JpaRepository<URLMapping, Integer> {

    @Query("select s from URLMapping s where s.url = :url")
    URLMapping findByURL(@Param("url") String url);

    @Query("select s from URLMapping s where s.tinyURL = :tinyURL")
    URLMapping findByTinyURL(@Param("tinyURL") String tinyURL);

}
