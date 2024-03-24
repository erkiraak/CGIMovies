package com.erkiraak.movies.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id
    private int id;
    private String title;
    private String titleOriginal;
    private String language;
    @Column(length = 3000)
    private String overview;
    private double averageRating;
    private int voteCount;
    private LocalDate releaseDate;
    private String posterURL;

    protected Movie() {
    }

    public Movie(int id, String title, String titleOriginal, String language, String overview, double averageRating,
            int voteCount, String releaseDate, String posterPath) {
        this.id = id;
        this.title = title;
        this.titleOriginal = titleOriginal;
        this.language = language;
        this.overview = overview;
        this.averageRating = averageRating;
        this.voteCount = voteCount;
        this.releaseDate = LocalDate.parse(releaseDate);
        this.posterURL = "https://image.tmdb.org/t/p/original" + posterPath;

    }

    @Override
    public String toString() {
        return title + "(" + releaseDate.getYear() + ")" + " - " + (overview.length() < 100 ? overview
                : overview.substring(0, 100));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOriginal() {
        return titleOriginal;
    }

    public void setTitleOriginal(String titleOriginal) {
        this.titleOriginal = titleOriginal;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
    
}
