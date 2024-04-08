package com.erkiraak.movies.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final Logger logger;

    private final String nowPlayingMoviesUrl = "https://api.themoviedb.org/3/movie/now_playing";
    private final String topMoviesUrl = "https://api.themoviedb.org/3/movie/top_rated";
    private final String genresUrl = "https://api.themoviedb.org/3/genre/movie/list";
    private final String apiKey = "?api_key=d1f678c65049085bbfb08c950042a9cc";
    private final String regionLanguage = "&language=en-US&region=EE";

    public TmdbDatabase(MovieRepository movieRepository, GenreRepository genreRepository, WebClient webClient,
            Logger logger) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.webClient = webClient;
        this.logger = logger;
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
            JSONObject json = results.getJSONObject(i);
            Movie movie = new Movie();

            movie.setId(json.getInt("id"));
            movie.setTitle(json.getString("title"));
            movie.setTitleOriginal(json.getString("original_title"));
            movie.setLanguage(json.getString("original_language"));
            movie.setOverview(json.getString("overview"));
            movie.setGenres(parseGenres(json.getJSONArray("genre_ids").toList()));
            movie.setAverageRating(json.getDouble("vote_average"));
            movie.setVoteCount(json.getInt("vote_count"));
            movie.setReleaseDate(LocalDate.parse(json.getString("release_date")));
            
            if (!json.isNull("poster_path")) {
                movie.setPosterURL("https://image.tmdb.org/t/p/original" + json.getString("poster_path"));
            } else {
                movie.setPosterURL("/img/poster.png");
            }
            
            if (!json.isNull("backdrop_path")) {
                movie.setBackDropURL("https://image.tmdb.org/t/p/original" + json.getString("backdrop_path"));
            } else {
                movie.setBackDropURL("/img/backdrop.jpg");
            }
            

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

            Genre genre = new Genre();
            genre.setId(jsonObject.getInt("id"));
            genre.setName(jsonObject.getString("name"));
            
            try {
                genreRepository.save(genre);
            } catch (DataIntegrityViolationException e) {
                logger.info(e.getMessage());
            }
        }
    }

    private List<Genre> parseGenres(List<Object> genres) {
        List<Genre> genreList = new ArrayList<>();
        for (Object genreId : genres) {
            if (!(genreId instanceof Integer)) {
                continue;
            }

            Integer id = (Integer) genreId;
            Optional<Genre> optionalGenre = genreRepository.findById(id);

            if (optionalGenre.isPresent()) {
                Genre genre = optionalGenre.get();
                genreList.add(genre);

            } else {
                logger.info("No genre found for id " + genreId);
            }

        }
        return genreList;
    }

}
