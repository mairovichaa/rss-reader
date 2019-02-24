package com.amairovi.service;

import com.amairovi.model.Feed;
import com.amairovi.utility.FeedStubServer;
import com.amairovi.utility.Main;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FeedLoaderServiceTest {
    private FeedLoaderService feedLoaderService;

    private static FeedStubServer feedStubServer;
    private static int port = 8080;

    @BeforeAll
    static void beforeAll() {
        feedStubServer = new FeedStubServer(port);
        feedStubServer.start();
    }

    @AfterAll
    static void afterAll() {
        feedStubServer.stop();
    }

    @BeforeEach
    void setup() {
        feedLoaderService = new FeedLoaderService();
    }

    @Nested
    class Load {
        @Test
        void when_malformed_url_then_return_empty() {
            Feed feed = new Feed();
            feed.setHref("href");

            Optional<SyndFeed> syndFeed = feedLoaderService.load(feed);

            assertFalse(syndFeed.isPresent());
        }

        @Test
        void when_not_real_url_then_return_empty() {
            Feed feed = new Feed();
            feed.setHref("http://some-made-up-url");

            Optional<SyndFeed> syndFeed = feedLoaderService.load(feed);

            assertFalse(syndFeed.isPresent());
        }

        @Test
        void when_atom_returns_then_load_and_parse_successfully() throws IOException, FeedException {
            Feed feed = new Feed();
            feed.setHref(feedStubServer.getAtomUrl());

            Optional<SyndFeed> result = feedLoaderService.load(feed);

            SyndFeed expected = loadFeedFromFile("atom.xml");
            assertTrue(result.isPresent());
            assertEquals(expected, result.get());
        }

        @Test
        void when_rss_returns_then_load_and_parse_successfully() throws IOException, FeedException {
            Feed feed = new Feed();
            feed.setHref(feedStubServer.getRssUrl());

            Optional<SyndFeed> result = feedLoaderService.load(feed);

            SyndFeed expected = loadFeedFromFile("rss.xml");
            assertTrue(result.isPresent());
            assertEquals(expected, result.get());
        }

        private SyndFeed loadFeedFromFile(String name) throws IOException, FeedException {
            URL url = Main.class.getResource(name);
            try (XmlReader xmlReader = new XmlReader(url)) {
                return new SyndFeedInput().build(xmlReader);
            }
        }

    }

}