package cracker;


import javafx.beans.property.*;

public class Student {
    private final SimpleIntegerProperty sid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty course;

    public Student(int sid, String name, String email, String course){
        this.sid = new SimpleIntegerProperty(sid);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.course = new SimpleStringProperty(course);
    }

    public String getName(){
        return this.name.get();
    }

    public int getSid(){
        return this.sid.get();
    }

    public String getEmail(){
        return this.email.get();
    }

    public String getCourse(){
        return this.course.get();
    }

    public IntegerProperty sidProperty(){
        return sid;
    }
    public StringProperty nameProperty(){
        return name;
    }

    public StringProperty courseProperty(){
        return course;
    }

    public StringProperty emailProperty(){
        return email;
    }

}
