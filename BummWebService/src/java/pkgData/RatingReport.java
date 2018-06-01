/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDate;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.reportedRating);
        hash = 13 * hash + Objects.hashCode(this.userWhoReported);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RatingReport other = (RatingReport) obj;
        if (!Objects.equals(this.reportedRating, other.reportedRating)) {
            return false;
        }
        if (!Objects.equals(this.userWhoReported, other.userWhoReported)) {
            return false;
        }
        return true;
    }

    
    
    
    
    
    
    
}
