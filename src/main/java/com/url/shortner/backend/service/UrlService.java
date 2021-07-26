package com.url.shortner.backend.service;

import com.google.common.hash.Hashing;
import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import com.url.shortner.backend.exceptions.InvalidUrlException;
import com.url.shortner.backend.exceptions.NoDataFoundException;
import com.url.shortner.backend.repository.UrlRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class UrlService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UrlRepository repository;

    @Value("${base_url}")
    private String baseUrl;

    //convert long url to short..

    public String convertUrl(UrlDto url) {
        String hashUrl = null;
        String longUrl = url.getLongUrl();

        //Allowing only http and https custom schemes
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(longUrl)) {

            //confirm if url already exist..
            if (repository.existsByLongUrl(longUrl)) {
                Url URL = repository.findByLongUrl(url.getLongUrl());
                hashUrl = baseUrl + URL.getHashUrl();

                return hashUrl;
            } else {

                Url urlEntity = new Url();
                urlEntity.setLongUrl(longUrl);
                urlEntity.setDateCreated(new Date());

                hashUrl = Hashing.murmur3_32().hashString(url.getLongUrl(), StandardCharsets.UTF_8).toString();
                urlEntity.setHashUrl(hashUrl);

                Url urlResult = repository.save(urlEntity);
                logger.info("Short url result {}", baseUrl + hashUrl);
                return baseUrl + hashUrl;
            }
        } else {
            throw new InvalidUrlException();
        }

    }

    //Retrieve url data..

    public String decodeUrl(String hashUrl) {

        //confirm if the hash provided exist...
        if (repository.existsByHashUrl(hashUrl)) {
            Url url = repository.findByHashUrl(hashUrl);

            return url.getLongUrl();
        } else {

            logger.info("No data found for {}", hashUrl);
            throw new NoDataFoundException();
        }

    }

    //Statisticss..
    public Url getShortUrlStatistics(String shortUrl) {
        if (repository.existsByHashUrl(shortUrl)) {
            Url url = repository.findByHashUrl(shortUrl);

            return url;
        } else {

            logger.info("No data found for {}", shortUrl);
            throw new NoDataFoundException();
        }
    }

    //list of all the urls..
    public List<Url> getAllUrlList() {

        List<Url> listUrl = repository.findAll();
        if (listUrl.size() >= 1) {
            return listUrl;
        } else {
            throw new RuntimeException("Url list is empty");
        }
    }

}
