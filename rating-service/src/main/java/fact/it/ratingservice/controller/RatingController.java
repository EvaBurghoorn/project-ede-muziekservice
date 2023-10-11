package fact.it.ratingservice.controller;

import fact.it.ratingservice.dto.RatingRequest;
import fact.it.ratingservice.dto.RatingResponse;
import fact.it.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createRating
            (@RequestBody RatingRequest ratingRequest){
        ratingService.createRating(ratingRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RatingResponse> getAllRatings(){
        return ratingService.getAllRatings();
    }
}