package com.url.shortner.backend.service;

import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

public interface IUrlService {

    public String convertUrl(UrlDto url);

    public List<Url> getAllUrlList();

    public Url getShortUrlStatistics(String shortUrl);

    public String decodeUrl(String hashUrl);

    public Boolean verifyShortUrl(String shortUrl);

    public RedirectView redirectURL(String hashUrl);

    public Page<Url> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection);


}
