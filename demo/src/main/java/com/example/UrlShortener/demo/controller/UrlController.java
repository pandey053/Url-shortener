package com.example.UrlShortener.demo.controller;

import com.example.UrlShortener.demo.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UrlController {

    @Autowired
    public UrlService service ;

    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {

        //Rate Limiter
        if(!service.isAllowed(code))
        {
            System.out.println("Blocked");
            response.getWriter().write("Too many request");
            return ;
        }
        System.out.println("Allowed");
        //Analytics
        service.incrementCount(code);

        //fetch url and redirect
        String url = service.getLongUrl(code);
        if(url == null)
        {
            response.getWriter().write("Invalid url");
        }
        response.sendRedirect(url);
    }

    @PostMapping("/longUrl")
    public String shorten(@RequestBody String url)
    {
        url = url.replace("\"", "").trim();
        System.out.println(url) ;
        return service.shortenUrl(url) ;
    }

    @GetMapping("/analytics/{code}")
    public String analytics(@PathVariable String code)
    {
        System.out.println("Analytics API Hit!!!");
        try
        {
            String count = service.getCountOfUrl(code) ;
            return "Clicks: " + count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Error occurred";
        }
    }


}
