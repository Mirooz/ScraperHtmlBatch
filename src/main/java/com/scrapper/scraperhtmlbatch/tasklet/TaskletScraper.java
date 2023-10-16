package com.scrapper.scraperhtmlbatch.tasklet;

import com.scrapper.scraperhtmlbatch.jobs.WebsiteReader;
import com.scrapper.scraperhtmlbatch.utils.Champion;
import com.scrapper.scraperhtmlbatch.utils.ChampionScraper;
import com.scrapper.scraperhtmlbatch.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskletScraper implements Tasklet {

    private final WebsiteReader websiteReader;
    private final ChampionScraper championScraper;

    private static final Logger logger = Logger.getLogger(TaskletScraper.class);
    public TaskletScraper(WebsiteReader websiteReader,ChampionScraper championScraper){
        this.championScraper = championScraper;
        this.websiteReader = websiteReader;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext)  {
        List<Champion> championList = championScraper.scrapeChampionsLink();
        WebsiteReader reader = websiteReader;
        reader.setWebsiteBaseUrl(Utils.extractBaseUrl(championScraper.getWebsiteUrl()));
        reader.setChampionList(championList);
        reader.setCurrentIndex(0);
        logger.info("Getting URL...");
        return RepeatStatus.FINISHED;
    }
}