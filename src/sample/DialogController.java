package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Student;

import java.time.LocalDate;


public class DialogController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField patronymicField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private TextField groupField;

    @FXML
    private DatePicker deadlinePicker;

    public Student processResults() {
        String firstName = firstNameField.getText().trim();
        String patronymic = patronymicField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();
        int group = Integer.parseInt(groupField.getText().trim());



//        LocalDate deadlineValue = deadlinePicker.getValue();

        Student student = new Student();
        student.setFirst_name(firstName);
        student.setPatronymic(patronymic);
        student.setLast_name(lastName);
        student.setDate_of_birth(dateOfBirth);
        student.setGroup_number(group);


        return student;
    }
}