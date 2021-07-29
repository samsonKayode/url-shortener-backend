package com.url.shortner.backend.repository;

import com.url.shortner.backend.entity.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Integer>, IUrlRepository {

    public Boolean existsByHashUrl(String hashUrl);

    public Url findHashUrlByLongUrl(String hashUrl);

    public Boolean existsByLongUrl(String longUrl);

    public Url findByHashUrl(String hashUrl);

    public Page<Url> findByLongUrlContains (Pageable pageable, String longUrl);
}
