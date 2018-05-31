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
public class RatingReport {
    private Rating reportedRating;
    private User userWhoReported;
    private LocalDate reportDate;

    public RatingReport() {
        
    }

    public RatingReport(Rating reportedRating, User userWhoReported, LocalDate reportDate) {
        this.reportedRating = reportedRating;
        this.userWhoReported = userWhoReported;
        this.reportDate = reportDate;
    }

    public Rating getReportedRating() {
        return reportedRating;
    }

    public void setReportedRating(Rating reportedRating) {
        this.reportedRating = reportedRating;
    }

    public User getUserWhoReported() {
        return userWhoReported;
    }

    public void setUserWhoReported(User userWhoReported) {
        this.userWhoReported = userWhoReported;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return "RatingReport{" + "reportedRating=" + reportedRating.getRatedArticle() + ", userWhoReported=" + userWhoReported.getUsername() + ", reportDate=" + reportDate + '}';
    }

    
    
    
    
    
    
    
}
