package com.example.redis.springbootrediscache.service;

import com.example.redis.springbootrediscache.model.URLMapping;
import com.example.redis.springbootrediscache.repository.URLMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLMappingServiceIMPL implements URLMappingService {

    @Autowired
    private URLMappingRepository urlMappingRepository;

    @Override
    public URLMapping getByTinyURL(String tinyURL) {
        return urlMappingRepository.findByTinyURL(tinyURL);
    }

    @Override
    public URLMapping save(URLMapping urlMapping) {
        return urlMappingRepository.saveAndFlush(urlMapping);
    }

    @Override
    public URLMapping findByURL(String url) {
        return urlMappingRepository.findByURL(url);
    }

}
