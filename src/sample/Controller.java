package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sample.model.Datasource;
import sample.model.Student;

import java.io.IOException;
import java.util.Optional;

public class Controller {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<Student> studentTable;

    public void initialize(){
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        studentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void listStudents(){
        Task<ObservableList<Student>> task = new Task<ObservableList<Student>>() {
            @Override
            protected ObservableList<Student> call() throws Exception {
                return FXCollections.observableArrayList(Datasource.getInstance().queryStudents());
            }
        };

        studentTable.itemsProperty().bind(task.valueProperty());

        new Thread(task).start();
    }

    @FXML
    public void showInsertDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Добавление студента");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("insert.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        Student insertedStudent = new Student();

        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            insertedStudent = controller.processResults();
            studentTable.getSelectionModel().select(insertedStudent);
        }

        insertStudent(insertedStudent);

    }

    private void insertStudent(Student student){
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return Datasource.getInstance().insertStudent(student);
            }
        };

        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()) {
                studentTable.refresh();
            }
        });

        new Thread(task).start();
    }

    @FXML
    public void deleteStudent(){

        Student student = studentTable.getSelectionModel().getSelectedItem();

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return Datasource.getInstance().deleteStudent(student.get_id());
            }
        };

        task.setOnSucceeded(e -> {
            if (task.valueProperty().get()){
                studentTable.refresh();
            }
        });

        new Thread(task).start();
    }

}
