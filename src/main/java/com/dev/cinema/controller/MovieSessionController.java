package com.dev.cinema.controller;

import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.impl.mapper.MovieSessionMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final MovieSessionMapper movieSessionMapper;

    @Autowired
    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/create")
    public void create(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.add(movieSessionMapper.toEntity(movieSessionRequestDto));
    }

    @GetMapping("/all")
    public List<MovieSessionResponseDto> findAvailableSessions(
            @RequestParam Long movieId, @RequestParam @DateTimeFormat(pattern =
                    "yyyy-MM-dd") LocalDate date) {
        List<MovieSession> availableSessions = movieSessionService
                .findAvailableSessions(movieId, date);
        return availableSessions.stream()
                .map(movieSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/update")
    public void update(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.update(movieSessionMapper.toEntity(movieSessionRequestDto));
    }

    @DeleteMapping("/remove")
    public void remove(@RequestBody MovieSessionRequestDto movieSessionRequestDto) {
        movieSessionService.remove(movieSessionMapper.toEntity(movieSessionRequestDto));
    }
}