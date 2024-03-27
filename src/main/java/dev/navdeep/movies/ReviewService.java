package dev.navdeep.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review addReview(String body, String imbdId) {
        Review review = reviewRepository.insert(new Review(body));

        // updating the empty array field called "review IDs" inside movie obj
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imbdId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        return review;
    }
}
