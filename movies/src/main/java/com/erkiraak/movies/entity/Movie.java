package com.erkiraak.movies.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;


@Entity
public class Movie {

    @Id
    private int id;
    private String title;
    private String titleOriginal;
    private String language;
    
    @Column(length = 3000)
    private String overview;
    
    @ManyToMany
    private List<Genre> genres;
    private double averageRating;
    private int voteCount;
    private LocalDate releaseDate;
    private String posterURL;
    private String backDropURL;

    
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

    public String getGenresAsString() {
        if (genres.size() == 0) {
            return "";
        }
        
        return genres.stream().map(Genre::getName).collect(Collectors.joining(", "));
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOriginal() {
        return (titleOriginal != title)? titleOriginal : null;
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

    public String getAverageRating() {
        return String.format("%.1f", averageRating);
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getBackDropURL() {
        return backDropURL;
    }

    public void setBackDropURL(String backDropURL) {
        this.backDropURL = backDropURL;
    }
    

}
