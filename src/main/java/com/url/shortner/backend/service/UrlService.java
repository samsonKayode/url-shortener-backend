package com.url.shortner.backend.service;

import com.google.common.hash.Hashing;
import com.url.shortner.backend.dto.UrlDto;
import com.url.shortner.backend.entity.Url;
import com.url.shortner.backend.exceptions.InternalServerException;
import com.url.shortner.backend.exceptions.InvalidUrlException;
import com.url.shortner.backend.exceptions.NoDataFoundException;
import com.url.shortner.backend.repository.IUrlRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class UrlService implements IUrlService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IUrlRepository repository;

    @Value("${base_url}")
    private String baseUrl;

    public UrlService(IUrlRepository repository) {
        this.repository = repository;
    }

    //convert long url to short..

    @Override
    public String convertUrl(UrlDto url) {
        String hashUrl;
        String longUrl = url.getLongUrl();

        try {
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

                    repository.save(urlEntity);
                    logger.info("Short url result {}", baseUrl + hashUrl);
                    return baseUrl + hashUrl;
                }
            } else {
                throw new InvalidUrlException();
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
            throw new InternalServerException();
        }

    }

    //Retrieve url data..

    @Override
    public String decodeUrl(String hashUrl) {

        try {
            //confirm if the hash provided exist...
            if (repository.existsByHashUrl(hashUrl)) {
                Url url = repository.findByHashUrl(hashUrl);

                return url.getLongUrl();
            } else {

                logger.info("No data found for {}", hashUrl);
                throw new NoDataFoundException();
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
            throw new InternalServerException();
        }
    }

    @Override
    public Boolean verifyShortUrl(String shortUrl) {
        return repository.existsByHashUrl(shortUrl);
    }

    @Override
    public RedirectView redirectURL(String hashUrl) {

        try {
            RedirectView redirectView = new RedirectView();
            if (repository.existsByHashUrl(hashUrl)) {
                Url url = repository.findByHashUrl(hashUrl);

                redirectView.setUrl(url.getLongUrl());
                return redirectView;
            } else {
                logger.info("No data found for {}", hashUrl);
                throw new NoDataFoundException();
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
            throw new InternalServerException();
        }
    }

    @Override
    public Page<Url> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        Page<Url> urlPage = repository.findAll(pageable);
        return urlPage;
    }

    //Statistics..
    @Override
    public Url getShortUrlStatistics(String shortUrl) {

        try {
            if (repository.existsByHashUrl(shortUrl)) {

                return repository.findByHashUrl(shortUrl);
            } else {

                logger.info("No data found for {}", shortUrl);
                throw new NoDataFoundException();
            }
        } catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
            throw new InternalServerException();
        }
    }

    //list of all the urls..
    @Override
    public List<Url> getAllUrlList() {

        try{
            List<Url> listUrl = repository.findAll();
            if (listUrl.size() >= 1) {
                return listUrl;
            } else {
                throw new RuntimeException("Url list is empty");
            }
        }catch (Exception exception) {
            logger.error(exception.getLocalizedMessage());
            throw new InternalServerException();
        }
    }

}
