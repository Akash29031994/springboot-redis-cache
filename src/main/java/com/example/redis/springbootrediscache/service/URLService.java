package com.example.redis.springbootrediscache.service;

import com.example.redis.springbootrediscache.model.URLMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class URLService {

    @Autowired
    private URLMappingService urlMappingService;

    @Autowired
    private URLGeneratorService urlGeneratorService;

    @Cacheable(value = "URLMappingLong", key = "#url")
    public String longToShort(String url) {
        URLMapping urlMapping = urlMappingService.findByURL(url);
        if (urlMapping == null) {
            urlMapping = new URLMapping();
            urlMapping.setUrl(url);
            urlMapping.setDate(new Date());
            urlMapping.setTinyURL(urlGeneratorService.getNewURL(url));
            urlMapping =  urlMappingService.save(urlMapping);
        }
        try {
            System.out.println("urlMapping shortToLong " + new ObjectMapper().writeValueAsString(urlMapping));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlMapping.getTinyURL();
    }

    @Cacheable(value = "URLMappingShort", key = "#url")
    public String shortToLong(String url) {
        URLMapping urlMapping = urlMappingService.getByTinyURL(url);
        try {
            System.out.println("urlMapping shortToLong " + new ObjectMapper().writeValueAsString(urlMapping));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (urlMapping == null)
            return "Not Found";
        else
            return urlMapping.getUrl();
    }

}