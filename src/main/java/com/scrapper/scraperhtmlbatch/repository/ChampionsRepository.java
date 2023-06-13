package com.scrapper.scraperhtmlbatch.repository;
import com.scrapper.scraperhtmlbatch.models.Champions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionsRepository extends JpaRepository<Champions, String> {

}