package com.fo4ik.jobsaver.engine;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class HtmlParser {

    private String jobTitle, jobCompany, jobFolder, urlToHtml, urlToCss;
    private String url;

    public HtmlParser(String url) {
        try {
            this.url = url;
            parseHtml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseJobFolder(String websiteName) {
        String jobFolder = "data/" + jobTitle + "-" + jobCompany + "-" + websiteName;
        File folder = new File(jobFolder);
        if (folder.exists()) {
            int i = 1;
            while (folder.exists()) {
                folder = new File(jobFolder + i);
                i++;
            }
            jobFolder = jobFolder + "-" + i;
        }
        return folder.getPath();
    }


    private String getElementText(String cssSelector) {
        try {
            Document document = Jsoup.connect(url)
                    .cookies(new HashMap<>())
                    .get();
            Elements elements = document.select(cssSelector);
            return elements.text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteElements(Document document, List<Elements> elementsList) {
        for (Elements elements : elementsList) {
            for (Element element : elements) {
                element.remove();
            }
        }

    }

    private void downloadCss(URL url) throws IOException {
        try {
            File css = new File(urlToCss);
            FileUtils.copyURLToFile(url, new File(String.valueOf(css)));
        } catch (Exception e) {
            System.out.println("Error while download css: " + e.getMessage());
        }
    }

    private String getWebsiteName() {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host != null) {
                host = host.startsWith("www.") ? host.substring(4) : host;
                return host;
            }
        } catch (URISyntaxException e) {
            System.out.println("Error while get website name: " + e.getMessage());
        }
        return null;
    }

    private void parseHtml() {
        switch (getWebsiteName()) {
            case "linkedin.com":
                parseLinkedIn();
                break;
            default:
                parseDefault();
                break;
        }
    }

    private void parseLinkedIn() {
        this.jobTitle = getElementText(".top-card-layout__title");
        this.jobCompany = getElementText(".topcard__org-name-link");
        this.jobFolder = parseJobFolder("linkedin");
        this.urlToHtml = jobFolder + "/job.html";
        this.urlToCss = jobFolder + "/css.css";
        try {

            Document doc = Jsoup.connect(url).get();

            Elements headerElements = doc.select("header");
            Elements navElements = doc.select("nav.nav[aria-label=Primary]");
            Elements sectionElementsRight = doc.select("section.right-rail");
            Elements sectionElementsJob = doc.select("section.similar-jobs");
            Elements sectionJobAlert = doc.select("section.job-alert-redirect-section");
            Elements similarPeople = doc.select(".face-pile.flex");
            Elements globalAlerts = doc.select("#artdeco-global-alerts-cls-offset");
            Elements elementsToRemove = doc.select(".top-card-layout__cta-container");
            List<Elements> elementsList = List.of(headerElements, navElements, sectionElementsRight, sectionElementsJob, sectionJobAlert, similarPeople, globalAlerts, elementsToRemove);
            deleteElements(doc, elementsList);


            Elements linkElements = doc.select("link[rel=stylesheet]");
            downloadCss(new URL(linkElements.attr("href")));

            //change linkElement to local css
            linkElements.attr("href", urlToCss);


            FileUtils.writeStringToFile(new File(urlToHtml), doc.html(), "UTF-8");

        } catch (Exception e) {
            System.out.println("Error while get html: " + e.getMessage());
        }
    }

    private void parseDefault() {
        try {
            Document doc = Jsoup.connect(url).get();

            //try to get JobTitle by id or class
            Element jobTitleElement = doc.selectFirst("#job");
            if (jobTitleElement != null) {
                this.jobTitle = jobTitleElement.text();
            } else {
                jobTitleElement = doc.selectFirst(".job");

                if (jobTitleElement != null) {
                    this.jobTitle = jobTitleElement.text();
                }
            }

            //try to get JobCompany by id or class
            Element jobCompanyElement = doc.selectFirst("#jobCompany");
            if (jobCompanyElement != null) {
                this.jobCompany = jobCompanyElement.text();
            } else {
                jobCompanyElement = doc.selectFirst(".job-company");
                if (jobCompanyElement != null) {
                    this.jobCompany = jobCompanyElement.text();
                }
            }

            //Catch if jobTitle or jobCompany is null
            if (jobTitle == null) {
                this.jobTitle = "Not found";
            }
            if (jobCompany == null) {
                this.jobCompany = "Not found";
            }

            this.jobFolder = parseJobFolder(getWebsiteName());

            //Get css
            Elements styleElements = doc.select("style");
            if (!styleElements.isEmpty()) {
                this.urlToCss = null;
            } else {
                Elements linkElements = doc.select("link[rel=stylesheet]");
                System.out.println(linkElements.attr("href"));
                this.urlToCss = jobFolder + "/css.css";
                downloadCss(new URL(linkElements.attr("href")));
            }

            //Save html
            this.urlToHtml = jobFolder + "/job.html";
            FileUtils.writeStringToFile(new File(urlToHtml), doc.html(), "UTF-8");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getJobFolder() {
        return jobFolder;
    }

    public void setJobFolder(String jobFolder) {
        this.jobFolder = jobFolder;
    }

    public String getUrlToHtml() {
        return urlToHtml;
    }

    public void setUrlToHtml(String urlToHtml) {
        this.urlToHtml = urlToHtml;
    }

    public String getUrlToCss() {
        return urlToCss;
    }

    public void setUrlToCss(String urlToCss) {
        this.urlToCss = urlToCss;
    }
}
