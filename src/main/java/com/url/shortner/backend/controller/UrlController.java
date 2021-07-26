package com.url.shortner.backend.controller;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import com.url.shortner.backend.service.UrlService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    @Autowired
    UrlService service;

    //encode api..
    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("/encode")
    public String convertUrl(@RequestBody UrlDto urlDto){
        return service.convertUrl(urlDto);
    }

    //Decode hashed Url
    @ApiOperation(value = "Decodes hashed url", notes = "Validates hashed url and returns the original URL")
    @GetMapping("/decode/{hashUrl}")
    public String decodeUrl(@PathVariable final String hashUrl){

        return service.decodeUrl(hashUrl);
    }

    //Get short url statistic..
    @GetMapping("/statistic/{short_url}")
    @ApiOperation(value = "Get basic stats", notes = "Retrieves all the basic statistics of short url")
    public Url getStatistic(@PathVariable final String short_url){
        return service.getShortUrlStatistics(short_url);
    }

    //Get the list of all the available urls

    @GetMapping("/list")
    @ApiOperation(value = "Url List", notes = "Retrieves the list of all the URLs")
    public List<Url> listOfAllUrls(){
        return service.getAllUrlList();
    }





}
