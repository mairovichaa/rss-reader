package com.amairovi;

import com.amairovi.dao.FeedDao;
import com.amairovi.dao.FeedPersistenceStore;
import com.amairovi.dto.FeedBriefInfo;
import com.amairovi.model.Feed;
import com.amairovi.service.*;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

public class Core {
    private final static long DEFAULT_POLL_PERIOD_MS = 60 * 1000;

    private final FeedDao feedDao;
    private final FeedPersistenceStore feedPersistenceStore;

    private final FeedLoaderService feedLoaderService;
    private final FeedFileService feedFileService;
    private final ScheduleService scheduleService;
    private final FeedService feedService;
    private final FeedFormatter feedFormatter;

    public Core() {
        feedPersistenceStore = new FeedPersistenceStore("data");
        feedDao = new FeedDao(feedPersistenceStore);

        feedLoaderService = new FeedLoaderService();
        feedFileService = new FeedFileService();
        feedFormatter = new FeedFormatter();
        LoadTaskFactory loadTaskFactory = new LoadTaskFactory(feedFileService, feedLoaderService, feedFormatter);

        ScheduledExecutorService scheduledExecutorService = newSingleThreadScheduledExecutor();
        scheduleService = new ScheduleService(scheduledExecutorService);

        feedService = new FeedService(feedDao, scheduleService, loadTaskFactory);
    }

    public void createFeed(String url, long pollPeriod) {
        feedService.createFeed(url, pollPeriod);
    }

    public void createFeed(String url) {
        feedService.createFeed(url, DEFAULT_POLL_PERIOD_MS);
    }

    public void setFeedSurveyPeriod(int id, long pollPeriod) {
        Feed feed = feedService.findById(id);
        feedService.setFeedSurveyPeriod(feed, pollPeriod);
    }

    public void setFeedFilename(int id, String filename) {
        Feed feed = feedService.findById(id);
        feedService.setFeedFilename(feed, filename);
    }

    public void setFeedAmountOfElementsAtOnce(int id, int amountOfElementsAtOnce) {
        Feed feed = feedService.findById(id);
        feedService.setFeedAmountOfElementsAtOnce(feed, amountOfElementsAtOnce);
    }

    public void hideProperty(int id, String propertyName) {
        Feed feed = feedService.findById(id);
        feedService.hideProperty(feed, propertyName);
    }

    public void showProperty(int id, String propertyName) {
        Feed feed = feedService.findById(id);
        feedService.showProperty(feed, propertyName);
    }

    public void enablePoll(int id) {
        throw new RuntimeException("Not implemented");
    }

    public void disablePoll(int id) {
        Feed feed = feedService.findById(id);
        feedService.disablePoll(feed);
    }

    public List<FeedBriefInfo> list() {
        return feedDao.findAll()
                .stream()
                .map(FeedBriefInfo::new)
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        throw new RuntimeException("Not implemented");

    }

    public String describe(int id) {
        throw new RuntimeException("Not implemented");
    }

    public void redirectFeedTo(int id, String filename) {
        throw new RuntimeException("Not implemented");

    }
}