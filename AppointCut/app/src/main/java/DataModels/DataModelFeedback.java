package DataModels;

public class DataModelFeedback {

    private String arrayDate, feedbackName, feedbackContent;
    private float feedbackRating;

    public DataModelFeedback (String arrayDate, String feedbackName, float feedbackRating, String feedbackContent) {
        this.arrayDate = arrayDate;
        this.feedbackName = feedbackName;
        this.feedbackRating = feedbackRating;
        this.feedbackContent = feedbackContent;
    }

    public String getArrayDate() {
        return arrayDate;
    }

    public void setArrayDate(String arrayDate) {
        this.arrayDate = arrayDate;
    }

    public String getFeedbackName() {
        return feedbackName;
    }

    public void setFeedbackName(String feedbackName) {
        this.feedbackName = feedbackName;
    }

    public float getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(float feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

}
