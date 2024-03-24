package com.erkiraak.movies;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.erkiraak.movies.entity.Movie;
import com.erkiraak.movies.repository.MovieRepository;
import com.erkiraak.movies.util.RoomInitializer;
import com.erkiraak.movies.util.SessionInitializer;
import com.erkiraak.movies.util.TmdbDatabase;

@SpringBootApplication
public class MoviesApplication {

	@SuppressWarnings("unused")
	@Autowired
	private TmdbDatabase tmdbNowPlayingMovies;
	@Autowired
	private RoomInitializer roomInitializer;
	@Autowired
	private SessionInitializer sessionInitializer;

	private static final Logger log = LoggerFactory.getLogger(MoviesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Bean
	public CommandLineRunner startupCommandLineRunner(MovieRepository repository) {
		return (args) -> {
			// create rooms
			roomInitializer.createRooms(3);

			// get currently playing movies
			List<Movie> nowPlayingMovies = tmdbNowPlayingMovies.fetchNowPlayingMoviesFromTmdb();
			
			// get top rated movies
			tmdbNowPlayingMovies.fetchTopMoviesFromTmdb();
			
			//generate sessions for movies
			sessionInitializer.generateSessions(nowPlayingMovies, 5, 25);
						


			// fetch all Movies
			log.info("Movies found with findAll():");
			log.info(String.valueOf(repository.findAll().size()));
			repository.findAll().size();
			log.info("--------------------");

			
		};
	}

}
