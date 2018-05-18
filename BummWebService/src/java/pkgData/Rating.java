/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author schueler
 */
@XmlRootElement
public class Rating {
    private Article ratedArticle;
    private User userWhoRated;
    private LocalDate ratingDate;
    private String ratingComment;
    private int ratingValue;

    public Rating() {
    }

    public Rating(Article ratedArticle, User userWhoRated, LocalDate ratingDate, String ratingComment, int ratingValue) {
        this.ratedArticle = ratedArticle;
        this.userWhoRated = userWhoRated;
        this.ratingDate = ratingDate;
        this.ratingComment = ratingComment;
        this.ratingValue = ratingValue;
    }

    
    public Article getRatedArticle() {
        return ratedArticle;
    }

    public void setRatedArticle(Article ratedArticle) {
        this.ratedArticle = ratedArticle;
    }

    public User getUserWhoRated() {
        return userWhoRated;
    }

    public void setUserWhoRated(User userWhoRated) {
        this.userWhoRated = userWhoRated;
    }

    public LocalDate getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(LocalDate ratingDate) {
        this.ratingDate = ratingDate;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    
    @Override
    public String toString() {
        return "Rating{" + "ratedArticle=" + ratedArticle.getArtNr() + ", userWhoRated=" + userWhoRated.getUsername() + ", ratingDate=" + ratingDate + ", ratingComment=" + ratingComment + ", ratingValue=" + ratingValue + '}';
    }
    
    
    
    
}
