package com.url.shortner.backend.controller;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import com.url.shortner.backend.service.IUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @MockBean
    IUrlService urlService;
    @Autowired
    private MockMvc mockMvc;
    private final String longUrl = "http://google.com";

    private final String hashedURL = "58f3ae21";

    @Test
    void convertUrl() throws Exception {

        String jsonString = "{\"longUrl\":\"http://google.com\"}";

        UrlDto urlDto = new UrlDto("http://google.com");
        when(urlService.convertUrl(urlDto)).thenReturn(hashedURL);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/encode")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(hashedURL));
    }

    @Test
    void decodeUrl() throws Exception {

        when(urlService.decodeUrl(hashedURL)).thenReturn(longUrl);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/decode/" + hashedURL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(longUrl));
    }

    @Test
    void getStatistic() throws Exception {

        Url URL = new Url(1, longUrl, new Date(), hashedURL);

        when(urlService.getShortUrlStatistics(hashedURL)).thenReturn(URL);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/statistic/" + hashedURL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longUrl").value(longUrl))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hashUrl").value(hashedURL));
    }

    @Test
    void listOfAllUrls() throws Exception {
        String cnnUrl="https://edition.cnn.com";
        String cnnHash="f6714805";
        Url firstURL = new Url(1, longUrl, new Date(), hashedURL);
        Url secondURL = new Url(2, cnnUrl, new Date(), cnnHash);

        List<Url> urlList = new ArrayList<>();

        urlList.add(firstURL);
        urlList.add(secondURL);

        when(urlService.getAllUrlList()).thenReturn(urlList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].longUrl").value(longUrl))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hashUrl").value(hashedURL))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].longUrl").value("https://edition.cnn.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hashUrl").value("f6714805"));
    }

    @Test
    void redirectUrl() throws Exception {

        //expected status will be 302 since the page will be redirected..

        when(urlService.redirectURL(hashedURL)).thenReturn(new RedirectView(longUrl));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/"+hashedURL))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(longUrl));
    }
}