package com.amairovi.cli.formatter;

import com.amairovi.dto.FeedInfo;

public class FeedConfigsFormatter {
    private final static String PATTERN =
            "Feed configs%n" +
                    "id: %s%n" +
                    "name: %s%n" +
                    "link: %s%n" +
                    "filename: %s%n" +
                    "amount of elements polled at once: %s%n" +
                    "poll period in ms: %s%n";

    public String format(FeedInfo feedInfo) {
        return String.format(PATTERN,
                ifNullThenUsePlaceholder(feedInfo.getId()),
                ifNullThenUsePlaceholder(feedInfo.getName()),
                ifNullThenUsePlaceholder(feedInfo.getLink()),
                ifNullThenUsePlaceholder(feedInfo.getFilename()),
                ifNullThenUsePlaceholder(feedInfo.getAmountOfElementsAtOnce()),
                ifNullThenUsePlaceholder(feedInfo.getPollPeriodInMs())
        );
    }

    public String ifNullThenUsePlaceholder(Object object) {
        return object == null ? "-" : object.toString();
    }
}
