package com.example.UrlShortener.demo.service;

import com.example.UrlShortener.demo.entity.UrlMapping;
import com.example.UrlShortener.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {


    @Autowired
    public UrlRepository url_repo ;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String shortenUrl(String longUrl)
    {
        String code = generateCode() ;
        UrlMapping url = new UrlMapping() ;
        url.setShortCode(code) ;
        url.setLongUrl(longUrl) ;
        url.setCreatedAt(LocalDateTime.now()) ;

        url_repo.save(url) ;
        System.out.println("Code from browser = [" + code + "]");
        return code ;
    }

    long totalTime = 0, countRequests = 0 ;
    public String getLongUrl(String code)
    {
        long startTime = System.currentTimeMillis() ;
        String cachedUrl = redisTemplate.opsForValue().get(code) ;
        if(cachedUrl != null)
        {
            System.out.println("Redis HIT: Data already present in cache") ;
            return cachedUrl ;

        }
        System.out.println("Redis MISS: Fetch from DB");

        Optional<UrlMapping> optional = url_repo.findById(code);
        if(optional.isEmpty()) {
            return null;
        }
        String url =  optional.get().getLongUrl();

        redisTemplate.opsForValue().set(code, url);
        return url ;
    }
    public String generateCode()
    {
        return UUID.randomUUID().toString().substring(0, 6) ;
    }

    public void incrementCount(String code)
    {
         redisTemplate.opsForValue().increment(code + ":count") ;
    }

    public String getCountOfUrl(String code)
    {
        return redisTemplate.opsForValue().get(code + ":count") ;
    }

    public boolean isAllowed(String code)
    {
        code = code.trim().toLowerCase() ;
        String redisKey = "rate:" + code ;

        Long count = redisTemplate.opsForValue().increment(redisKey);

        if(count == 1)
        {
            redisTemplate.expire(redisKey, 120, TimeUnit.SECONDS);
        }

        return count <= 5;
    }
}
