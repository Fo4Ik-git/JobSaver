package com.fo4ik.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;


public class HtmlParser {

    private static Document document;

    private static void getDocument(String url) {
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getJobTitle(String url) {
        getDocument(url);
        Elements jobTitle = document.select(".top-card-layout__title");
        return jobTitle.text();
    }

    public static String getJobCompany(String url) {
        getDocument(url);
        Elements jobCompany = document.select(".topcard__org-name-link");
        return jobCompany.text();
    }

    public static String getHtml(String url) {
        try {
            getDocument(url);
            Elements headerElements = document.select("header");
            Elements navElements = document.select("nav.nav[aria-label=Primary]");
            Elements sectionElementsRight = document.select("section.right-rail");
            Elements sectionElementsJob = document.select("section.similar-jobs");
            Elements sectionJobAlert = document.select("section.job-alert-redirect-section");
            Elements similarPeople = document.select(".face-pile.flex");
            Elements globalAlerts = document.select("#artdeco-global-alerts-cls-offset");
            Elements elementsToRemove = document.select(".top-card-layout__cta-container");

            List<Elements> elementsList = List.of(headerElements, navElements, sectionElementsRight, sectionElementsJob, sectionJobAlert, similarPeople, globalAlerts, elementsToRemove);

            deleteElements(elementsList);

            return document.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void deleteElements(List<Elements> elements) {
        for (Elements element : elements) {
            for (Element el : element) {
                el.remove();
            }
        }
    }
}
