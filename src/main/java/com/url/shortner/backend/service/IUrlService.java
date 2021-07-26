package com.url.shortner.backend.service;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;

import java.util.List;

public interface IUrlService {

    public String convertUrl(UrlDto url);

    public List<Url> getAllUrlList();

    public Url getShortUrlStatistics(String shortUrl);

    public String decodeUrl(String hashUrl);


}
