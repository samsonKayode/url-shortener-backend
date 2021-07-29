package com.url.shortner.backend.repository;

import com.url.shortner.backend.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUrlRepository {

    public Boolean existsByHashUrl(String hashUrl);

    public Url findByLongUrl(String hashUrl);

    public Boolean existsByLongUrl(String longUrl);

    public Url findByHashUrl(String hashUrl);

    public Url save(Url url);

    public List<Url> findAll();

    public Page<Url> findAll(Pageable pageable);

}
