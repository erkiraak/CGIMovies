package com.erkiraak.movies;

import java.util.List;

import org.slf4j.Logger;
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

	@Autowired
	private TmdbDatabase tmdbNowPlayingMovies;
	@Autowired
	private RoomInitializer roomInitializer;
	@Autowired
	private SessionInitializer sessionInitializer;
	@Autowired
	private Logger logger;
	
	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Bean
	public CommandLineRunner startupCommandLineRunner(MovieRepository repository) {
		return (args) -> {
			// logger.info("Getting data");
			// // create rooms
			// roomInitializer.createRooms(3);
			
			// // get list of genres
			// tmdbNowPlayingMovies.fetchGenresFromTmdb();

			// // get currently playing movies
			// List<Movie> nowPlayingMovies = tmdbNowPlayingMovies.fetchNowPlayingMoviesFromTmdb();
			
			// // get top rated movies
			// // tmdbNowPlayingMovies.fetchTopMoviesFromTmdb();
			
			// //generate sessions for movies
			// sessionInitializer.generateSessions(nowPlayingMovies, 1);
						
		};
	}

}
