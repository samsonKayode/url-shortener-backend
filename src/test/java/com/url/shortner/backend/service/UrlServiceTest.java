package com.url.shortner.backend.service;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import com.url.shortner.backend.repository.IUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private IUrlRepository repository;
    private UrlService underTest;

    private String hashedURL="1dd1ac64";

    @BeforeEach
    void setUp() {
        underTest = new UrlService(repository);
    }

    @Test
    void convertUrl() {

        //when
        UrlDto urlDto = new UrlDto();
        urlDto.setLongUrl("http://facebook.com");
        String returnedURL = underTest.convertUrl(urlDto);

        ArgumentCaptor<Url> urlArgumentCaptor = ArgumentCaptor.forClass(Url.class);
        verify(repository).save(urlArgumentCaptor.capture());

        Url URL = urlArgumentCaptor.getValue();
        //then
        assertThat(returnedURL).contains(URL.getHashUrl());

    }

    @Test
    void decodeUrl() {
        //when
        repository.findByHashUrl(hashedURL);
        //then
        verify(repository).findByHashUrl(hashedURL);
    }

    @Test
    void getShortUrlStatistics() {
        //when
        repository.findByHashUrl(hashedURL);
        //then
        verify(repository).findByHashUrl(hashedURL);
    }

    @Test
    void getAllUrlList() {
        //when
        repository.findAll();
        //then
        verify(repository).findAll();
    }
}