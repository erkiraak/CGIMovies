package com.erkiraak.movies.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.erkiraak.movies.entity.Genre;
import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.repository.GenreRepository;
import com.erkiraak.movies.repository.MovieRepository;

@Component
public class TmdbDatabase {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final WebClient webClient;

    private final String nowPlayingMoviesUrl = "https://api.themoviedb.org/3/movie/now_playing";
    private final String topMoviesUrl = "https://api.themoviedb.org/3/movie/top_rated";
    private final String genresUrl = "https://api.themoviedb.org/3/genre/movie/list";
    private final String apiKey = "?api_key=d1f678c65049085bbfb08c950042a9cc";
    private final String regionLanguage = "&language=en-US&region=EE";


    public TmdbDatabase(MovieRepository movieRepository, GenreRepository genreRepository, WebClient webClient) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.webClient = webClient;
    }

    
    public void fetchTopMoviesFromTmdb() {
        for (int i = 1; i <= 10; i++) {
            String page = "&page=" + i;
            String fullUrl = topMoviesUrl + apiKey + regionLanguage + page;
            try {
                String resultString = webClient.get()
                        .uri(fullUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                JSONObject resultJson = new JSONObject(resultString);
                JSONArray results = resultJson.getJSONArray("results");
                saveMoviesToDatabase(results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Movie> fetchNowPlayingMoviesFromTmdb() {
        String fullUrl = nowPlayingMoviesUrl + apiKey + regionLanguage;
        String resultString = "";
        try {
            resultString = webClient.get()
                    .uri(fullUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resultJson = new JSONObject(resultString);
        JSONArray results = resultJson.getJSONArray("results");
        return saveMoviesToDatabase(results);
    }

    public List<Movie> saveMoviesToDatabase(JSONArray results) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJson = results.getJSONObject(i);
            Movie movie = new Movie(
                    movieJson.getInt("id"),
                    movieJson.getString("title"),
                    movieJson.getString("original_title"),
                    movieJson.getString("original_language"),
                    movieJson.getString("overview"),
                    movieJson.getDouble("vote_average"),
                    movieJson.getInt("vote_count"),
                    movieJson.getString("release_date"),
                    movieJson.getString("poster_path"));

            movieRepository.save(movie);
            movies.add(movie);
        }
        return movies;
    }

    public void fetchGenresFromTmdb() {
        String fullUrl = genresUrl + apiKey;
        String resultString = "";
        try {
            resultString = webClient.get()
                    .uri(fullUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject resultJson = new JSONObject(resultString);
        JSONArray results = resultJson.getJSONArray("genres");

        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);
            
            Genre genre = new Genre(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name")
                    );

                    genreRepository.save(genre);
        }
    }
}
