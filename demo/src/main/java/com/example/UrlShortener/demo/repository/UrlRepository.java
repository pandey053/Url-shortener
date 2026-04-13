package com.example.UrlShortener.demo.repository;

import com.example.UrlShortener.demo.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlMapping, String> {
}
