package com.fo4ik.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


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
            Elements navElements = document.select("nav.nav[aria-label=Primary]");
            Elements sectionElementsRight = document.select("section.right-rail");
            Elements sectionElementsJob = document.select("section.similar-jobs");
            Elements sectionJobAlert = document.select("section.job-alert-redirect-section");
            Elements similarPeople = document.select(".face-pile.flex");
            Elements globalAlerts = document.select("#artdeco-global-alerts-cls-offset");
            Elements elementsToRemove = document.select(".top-card-layout__cta-container");

            for (Element navElement : navElements) {
                navElement.remove();
            }
            for (Element sectionElement : sectionElementsRight) {
                sectionElement.remove();
            }
            for (Element sectionElement : sectionElementsJob) {
                sectionElement.remove();
            }
            for (Element sectionElement : sectionJobAlert) {
                sectionElement.remove();
            }
            for (Element elementToRemove : elementsToRemove) {
                elementToRemove.remove();
            }
            for (Element globalAlert : globalAlerts) {
                globalAlert.remove();
            }
            for (Element similarPeoples : similarPeople) {
                similarPeoples.remove();
            }

            return document.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
