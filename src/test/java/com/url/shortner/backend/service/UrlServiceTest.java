package com.url.shortner.backend.service;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.repository.IUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private IUrlRepository repository;
    private UrlService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UrlService(repository);
    }

    @Test
    void convertUrl() {

        String hashedFacebookURL="1dd1ac64";
        //when
        UrlDto urlDto = new UrlDto();
        urlDto.setLongUrl("http://facebook.com");
        String returnedURL = underTest.convertUrl(urlDto);

        //then.
        assertThat(returnedURL).contains(hashedFacebookURL);
    }

    @Test
    void decodeUrl() {
    }

    @Test
    void getShortUrlStatistics() {
    }

    @Test
    void getAllUrlList() {
    }
}