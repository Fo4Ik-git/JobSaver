package com.fo4ik.engine;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HtmlParser {

    private static final int NUM_THREADS = 5;

    private static ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    public static String getJobTitle(String url) {
        return getElementText(url, ".top-card-layout__title");
    }

    public static String getJobCompany(String url) {
        return getElementText(url, ".topcard__org-name-link");
    }

    public static String getHtml(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements headerElements = document.select("header");
            Elements navElements = document.select("nav.nav[aria-label=Primary]");
            Elements sectionElementsRight = document.select("section.right-rail");
            Elements sectionElementsJob = document.select("section.similar-jobs");
            Elements sectionJobAlert = document.select("section.job-alert-redirect-section");
            Elements similarPeople = document.select(".face-pile.flex");
            Elements globalAlerts = document.select("#artdeco-global-alerts-cls-offset");
            Elements elementsToRemove = document.select(".top-card-layout__cta-container");

            List<Elements> elementsList = List.of(headerElements, navElements, sectionElementsRight, sectionElementsJob, sectionJobAlert, similarPeople, globalAlerts, elementsToRemove);

            deleteElements(document, elementsList);

            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getElementText(String url, String cssSelector) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select(cssSelector);
            return elements.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void deleteElements(Document document, List<Elements> elementsList) {
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                element.remove();
            }
        }
    }

    public static void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
