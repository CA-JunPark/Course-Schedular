package application;

import javafx.scene.control.Button;

public class CourseButton extends Button {
    private String CourseCode;
    private String Section;
    private String CourseTitle;
    private String Time;
    private String[] date;
    private String prof;
    private int credit;
    private String Description;

    public CourseButton(String CourseCode, String Section, String CourseTitle,
                        String Time, String[] date,
                        String prof, int credit, String Description){
        super();
        this.CourseCode = CourseCode;
        this.Section = Section;
        this.CourseTitle = CourseTitle;
        this.Time = Time;
        this.date = date;
        this.prof = prof;
        this.credit = credit;
        this.Description = Description;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }


    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
