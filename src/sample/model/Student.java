package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private SimpleStringProperty first_name;
    private SimpleStringProperty patronymic;
    private SimpleStringProperty last_name;
    private SimpleStringProperty date_of_birth;
    private SimpleIntegerProperty group_number;
    private SimpleIntegerProperty _id;

    public Student() {
        this.first_name = new SimpleStringProperty();
        this.last_name = new SimpleStringProperty();
        this.patronymic = new SimpleStringProperty();
        this.date_of_birth = new SimpleStringProperty();
        this.group_number = new SimpleIntegerProperty();
        this._id = new SimpleIntegerProperty();
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public SimpleStringProperty first_nameProperty() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public SimpleStringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public SimpleStringProperty last_nameProperty() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public String getDate_of_birth() {
        return date_of_birth.get();
    }

    public SimpleStringProperty date_of_birthProperty() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth.set(date_of_birth);
    }

    public int getGroup_number() {
        return group_number.get();
    }

    public SimpleIntegerProperty group_numberProperty() {
        return group_number;
    }

    public void setGroup_number(int group_number) {
        this.group_number.set(group_number);
    }

    public int get_id() {
        return _id.get();
    }

    public SimpleIntegerProperty _idProperty() {
        return _id;
    }

    public void set_id(int _id) {
        this._id.set(_id);
    }
}
