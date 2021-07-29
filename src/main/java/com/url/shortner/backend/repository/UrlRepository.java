package com.url.shortner.backend.repository;

import com.url.shortner.backend.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<Url, Integer>, IUrlRepository {

    public Boolean existsByHashUrl(String hashUrl);

    public Url findByLongUrl(String hashUrl);

    public Boolean existsByLongUrl(String longUrl);

    public Url findByHashUrl(String hashUrl);

}
