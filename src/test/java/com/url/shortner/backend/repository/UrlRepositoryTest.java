package com.url.shortner.backend.repository;

import com.url.shortner.backend.entity.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    UrlRepository underTest;

    Url URL;
    String hashURL;
    String longUrl;

    @Value("${base_url}")
    private String baseURL;

    @BeforeEach
    void setUp() {
        longUrl = "https://google.com";
        hashURL ="c5cd9fc1";

        URL = new Url();
        URL.setLongUrl(longUrl);
        URL.setDateCreated(new Date());
        URL.setHashUrl(hashURL);

        underTest.save(URL);
    }


    //Test checks if short url already exist..
    @Test
    void existsByHashUrl() {

        //when
        boolean exists =  underTest.existsByHashUrl(hashURL);
        //then
        assertThat(exists).isTrue();

    }

    //Check if short url doesn't exist..
    @Test
    void shortUrlDoesNotExist(){

        boolean exists = underTest.existsByHashUrl("abcdefGG");
                //then
        assertThat(exists).isFalse();
    }

    @Test
    void findByLongUrl() {
        Url url = underTest.findByLongUrl(longUrl);

        String urlResult = url.getLongUrl();
        //then
    }

    @Test
    void existsByLongUrl() {
    }

    @Test
    void findByHashUrl() {
    }
}