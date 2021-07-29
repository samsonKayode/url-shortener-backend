package com.url.shortner.backend.repository;

import com.url.shortner.backend.entity.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IUrlRepositoryTest {

    @Autowired
    IUrlRepository underTest;

    private Url URL;
    private String hashURL;
    private String longUrl;


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
        Url url = underTest.findHashUrlByLongUrl(longUrl);
        String urlResult = url.getLongUrl();
        //then
        assertThat(urlResult).isEqualTo(longUrl);
    }

    @Test
    void existsByLongUrl() {
        boolean exists =  underTest.existsByLongUrl(longUrl);
        //then
        assertThat(exists).isTrue();
    }

    @Test
    void findByHashUrl() {
        Url url = underTest.findByHashUrl(hashURL);
        String urlResult = url.getHashUrl();
        //then
        assertThat(urlResult).isEqualTo(hashURL);
    }

    @Test
    void save() {
        //given
        longUrl = "https://edition.cnn.com";
        hashURL ="f6714805";
        URL = new Url();
        URL.setLongUrl(longUrl);
        URL.setDateCreated(new Date());
        URL.setHashUrl(hashURL);

        Url savedData = underTest.save(URL);

        assertThat(savedData.getHashUrl()).isEqualTo(hashURL);
    }

    @Test
    void findAll() {

        List<Url> resultList = underTest.findAll();

        int listSize = resultList.size();
        System.out.println("LIST SIZE=====>"+listSize);
        assertThat(listSize).isEqualTo(1);

    }
}