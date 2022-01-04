package com.example.redis.springbootrediscache.service;

import com.example.redis.springbootrediscache.model.URLMapping;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public interface URLMappingService {

    URLMapping getByTinyURL(String tinyURL);

    URLMapping save(URLMapping urlMapping);

    URLMapping findByURL(String url);

}
