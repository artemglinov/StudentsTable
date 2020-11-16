package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
    public static final String DB_NAME = "students_database.db";

    /*
         Здесь должен быть указан путь к БД. В моём случае это E:\JavaProjects\StudentsTable\src\students_database.db.
     */

    public static final String CONNECTION_STRING = "jdbc:sqlite:E:\\JavaProjects\\StudentsTable\\src\\" + DB_NAME;

    public static final String TABLE_STUDENTS = "Students";

    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PATRONYMIC = "patronymic";
    public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    public static final String COLUMN_GROUP = "group_number";
    public static final String COLUMN_ID = "_id";

    public static final String QUERY_STUDENTS = "SELECT * FROM " + TABLE_STUDENTS;

    public static final String CHECK_STUDENT_BY_NAME = QUERY_STUDENTS + " WHERE (" + COLUMN_FIRST_NAME +
            " = ?) AND (" + COLUMN_PATRONYMIC + " = ?) AND (" + COLUMN_LAST_NAME + " = ?)";

    public static final String CHECK_STUDENT_BY_ID = QUERY_STUDENTS + " WHERE " + COLUMN_ID + " = ?";

    public static final String INSERT_STUDENT = "INSERT INTO " + TABLE_STUDENTS +
            " (" + COLUMN_FIRST_NAME + ", " + COLUMN_PATRONYMIC + ", " +
            COLUMN_LAST_NAME + ", " + COLUMN_DATE_OF_BIRTH + ", " +
            COLUMN_GROUP + ") VALUES(?, ?, ?, ?, ?)";

    public static final String DELETE_STUDENT = "DELETE FROM " + TABLE_STUDENTS +
            " WHERE " + COLUMN_ID + " = ?";

    private Connection conn;

    private PreparedStatement queryStudents;

    private PreparedStatement insertStudent;

    private PreparedStatement deleteStudent;

    private PreparedStatement checkStudentName;

    private PreparedStatement checkStudentId;

    private static Datasource instance = new Datasource();

    private Datasource(){

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            queryStudents = conn.prepareStatement(QUERY_STUDENTS);
            insertStudent = conn.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS);
            deleteStudent = conn.prepareStatement(DELETE_STUDENT);
            checkStudentName = conn.prepareStatement(CHECK_STUDENT_BY_NAME);
            checkStudentId = conn.prepareStatement(CHECK_STUDENT_BY_ID);

            return true;

        } catch (SQLException e){
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if(queryStudents != null){
                queryStudents.close();
            }

            if (insertStudent != null){
                insertStudent.close();
            }

            if (deleteStudent != null){
                deleteStudent.close();
            }

            if (checkStudentName != null){
                checkStudentName.close();
            }

            if (checkStudentId != null){
                checkStudentId.close();
            }
        } catch (SQLException e){
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public List<Student> queryStudents(){
        try{
            ResultSet results = queryStudents.executeQuery();

            List<Student> students = new ArrayList<>();

            while (results.next()){
                Student student = new Student();
                student.set_id(results.getInt(COLUMN_ID));
                student.setFirst_name(results.getString(COLUMN_FIRST_NAME));
                student.setPatronymic(results.getString(COLUMN_PATRONYMIC));
                student.setLast_name(results.getString(COLUMN_LAST_NAME));
                student.setDate_of_birth(results.getString(COLUMN_DATE_OF_BIRTH));
                student.setGroup_number(results.getInt(COLUMN_GROUP));

                students.add(student);
            }

            return students;

        } catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }


    public boolean insertStudent(Student student) throws SQLException{

        String firstName = student.getFirst_name();
        String patronymic = student.getPatronymic();
        String lastName = student.getLast_name();
        String dateOfBirth = student.getDate_of_birth();
        int group = student.getGroup_number();

        /*
               Проверка наличия студента в таблице по ФИО.
         */
        checkStudentName.setString(1, firstName);
        checkStudentName.setString(2, patronymic);
        checkStudentName.setString(3, lastName);

        ResultSet results = checkStudentName.executeQuery();

        if(results.next()){
            System.out.println("Student is already present in the table");
            return false;
        } else {

                insertStudent.setString(1, firstName);
                insertStudent.setString(2, patronymic);
                insertStudent.setString(3, lastName);
                insertStudent.setString(4, dateOfBirth);
                insertStudent.setInt(5, group);
                int affectedRows = insertStudent.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Couldn't insert student!");
                }

            ResultSet generatedKeys = insertStudent.getGeneratedKeys();
            if (generatedKeys.next()){
                return true;
            } else {
                throw new SQLException("Couldn't get _id for artist");
            }
        }
    }

    public boolean deleteStudent(int id) throws SQLException{

        checkStudentId.setInt(1, id);

        ResultSet results = checkStudentId.executeQuery();

        if (!results.next()) {
            System.out.println("Student does not exist in the table");
            return false;
        } else {
                deleteStudent.setInt(1, id);

                int affectedRows = deleteStudent.executeUpdate();

                if (affectedRows != 1) {
                    throw new SQLException("Couldn't delete student!");
                }
            return true;
        }
    }
}
