package com.example.sebinefrancis.addsonglyrics;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Sebine Francis on 27/08/2017.
 */

public class Songs {
    public final String title;
    public final String link;
    public final String lyrics;
    public final String category;
    private static final String ns = null;

    private Songs(String title, String lyrics, String link,String category) {
        this.title = title;
        this.lyrics = lyrics;
        this.link = link;
        this.category = category;
    }


    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    public static Songs readSongs(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "song");
        String title = null;
        String summary = null;
        String link = "";
        String category = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
                if(title!=null)
                title=title.trim();
            } else if (name.equals("lyrics")) {
                summary = readSummary(parser);
                ////if(summary!=null)
               // summary=summary.trim();
            } else if (name.equals("link")) {
                link = readLink(parser);
                if(link!=null)
                link=link.trim();
            } else if (name.equals("category")) {
                category = readCategory(parser);
                if(category!=null)
                category=category.trim();
            } else {
                skip(parser);
            }
        }
        return new Songs(title, summary, link,category);
    }

    // Processes title tags in the feed.
    private static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    public static String readCategories(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "categories");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "categories");
        return title;
    }

    // Processes link tags in the feed.
    private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    // Processes summary tags in the feed.
    private static String readSummary(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "lyrics");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "lyrics");
        return summary;
    }
    // Processes summary tags in the feed.
    private static String readCategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "category");
        String category = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return category;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
          }
