package com.url.shortner.backend.service;

import com.url.shortner.backend.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository repository;
    UrlService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UrlService(repository);
    }

    @Test
    void convertUrl() {
        //when
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