package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.Datasource;

/*
    Пользовательский интерфейс JavaFX приложения позволяет с помощью кнопок
    выводить список студентов, добавлять их и удалять (удаление производится на основе выбранной
    строки таблицы, причём метод, реализующий удаление в классе sample.model.Datasource, основывается на ID
    строки). Добавление и удаление не обновляют таблицу автоматически, чтобы
    продемонстрировать вывод списка с помощью отдельной кнопки.
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listStudents();
        primaryStage.setTitle("Студенты");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        if(!Datasource.getInstance().open()){
            System.out.println("FATAL ERROR: Couldn't connect to database");
            Platform.exit();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

