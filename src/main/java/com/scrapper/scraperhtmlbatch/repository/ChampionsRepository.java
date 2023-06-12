package com.scrapper.scraperhtmlbatch.repository;
import com.scrapper.scraperhtmlbatch.models.Champions;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface ChampionsRepository extends JpaRepository<Champions, String> {

}