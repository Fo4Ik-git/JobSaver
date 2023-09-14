package com.fo4ik.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

    public static String getHtml(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements styleLinks = document.select("link[rel=stylesheet]");
            Elements navElements = document.select("nav.nav[aria-label=Primary]");
            Elements sectionElementsRight = document.select("section.right-rail");
            Elements sectionElementsJob = document.select("section.similar-jobs");
            Elements sectionJobAlert = document.select("section.job-alert-redirect-section");
            Elements similarPeople = document.select(".face-pile.flex");
            Elements elementsToRemove = document.select(".top-card-layout__cta-container");


            for (Element styleLink : styleLinks) {
                String styleUrl = styleLink.absUrl("href");
                Document styleDoc = Jsoup.connect(styleUrl).get();
            }
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
