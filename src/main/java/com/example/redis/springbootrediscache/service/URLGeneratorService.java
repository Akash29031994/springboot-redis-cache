package com.example.redis.springbootrediscache.service;

import com.example.redis.springbootrediscache.dto.request.Range;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class URLGeneratorService {

    private static final int CHAR_COUNT = 62;
    private static final char[]  arr = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    @Autowired
    private Range range;

    @Autowired
    private SharedRangeService srs;

    @Value("#{range.start}")
    private long pointer;

    private synchronized void resetRange() {
        range = srs.getUniqueRange();
        try {
            System.out.println("Range " + new ObjectMapper().writeValueAsString(range));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pointer = range.getStart();
    }

    @CachePut(value="URLMappingLong", key = "#original")
    public synchronized String getNewURL(String original) {
        try {
            String log = String.format("Range start %s end %s\nPointer %s", range.getStart(), range.getEnd(), pointer);
            System.out.println(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(pointer > range.getEnd())
            resetRange();
        long count = pointer;
        StringBuilder sNewUrl = new StringBuilder();
        while (count > 0) {
            sNewUrl.append(arr[(int) count % CHAR_COUNT]);
            count /= CHAR_COUNT;
        }
        while (sNewUrl.length() < 7) sNewUrl.append('a');
        //save to DB
        pointer++;
        return "https://tinyurl/" + sNewUrl.reverse().toString();
    }

}

