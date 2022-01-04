package com.example.redis.springbootrediscache.controller;

import com.example.redis.springbootrediscache.dto.request.URLRequest;
import com.example.redis.springbootrediscache.dto.response.URLResponse;
import com.example.redis.springbootrediscache.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class URLController {

    @Autowired
    private URLService urlService;

    @PostMapping("/longtoshorturl")
    public URLResponse longToShortURL(@RequestBody URLRequest request) {
        return new URLResponse(urlService.longToShort(request.getUrl()));
    }

    @PostMapping("/shorttolongurl")
    public URLResponse shortToLongURL(@RequestBody URLRequest request) {
        return new URLResponse(urlService.shortToLong(request.getUrl()));
    }

}
