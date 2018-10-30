package com.example.poojan.upload_news;

public class news {
    private String webSectionName;
    private String webPublicationDate;
    private String webTitle;
    private String webTrailText;
    private String webUrl;
    private String byLine;
    private String thumbnail;


    /**
     * Constructs a new {@link news} object
     *
     * @param webSectionName     Section for the article
     * @param webPublicationDate Publication date for the article
     * @param webTitle           Title of the article
     * @param webTrailText       TrailText of the article
     * @param webUrl             Url of the article
     * @param byLine             Author of the article
     * @param thumbnail          Url to the thumbnail of the article
     */
    public news(String webSectionName, String webPublicationDate, String webTitle,
                       String webTrailText, String webUrl, String byLine,String thumbnail) {
        this.webSectionName = webSectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle; //headLine
        this.webTrailText = webTrailText;
        this.webUrl = webUrl;
        this.byLine = byLine;
        this.thumbnail = thumbnail;
    }

    public news(){}

    public String getSectionName() {
        return webSectionName;
    }

    public String getPublishedDate() {
        return webPublicationDate;
    }

    public String getTitle() {
        return webTitle;
    }

    public void setWebSectionName(String webSectionName) {
        this.webSectionName = webSectionName;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public void setWebTrailText(String webTrailText) {
        this.webTrailText = webTrailText;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTrailText() {
        return webTrailText;
    }

    public String getUrl() {
        return webUrl;
    }

    public String getAuthor() {
        return byLine;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
