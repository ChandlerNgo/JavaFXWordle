import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class StatisticsSceneController {

    @FXML
    private TextField txtCurrentStreak, txtMaxStreak, txtPlayed, txtWinPercentage, fileName;

    @FXML
    private Label title, statistics;

    @FXML
    private Button saveButton, resetButton;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private VBox labels;

    double labelWidth = 205;
    ArrayList<Integer> userStats = new ArrayList<>();
    ArrayList<Label> listofLabels = new ArrayList<>();
    ArrayList<Integer> userScores = new ArrayList<>();
    boolean isLoaded = false;
    int lastScored;

    void setData(ArrayList<Integer> stats, ArrayList<Integer> scores, String fileData, String answer, boolean loaded) {
        userStats = stats;
        // labelWidth = labels.getLayoutBounds().getWidth();
        userScores = scores;
        txtPlayed.setText(String.valueOf(userScores.size()));
        txtWinPercentage.setText(String.valueOf(userStats.get(0)));
        txtCurrentStreak.setText(String.valueOf(userStats.get(1)));
        txtMaxStreak.setText(String.valueOf(userStats.get(2)));
        if (fileData != "") {
            fileName.setText(fileData);
        }
        statistics.setText("Statistics: the actual word is " + answer);
        isLoaded = loaded;

        ArrayList<Integer> values = new ArrayList<>();
        values.add(0);
        values.add(0);
        values.add(0);
        values.add(0);
        values.add(0);
        values.add(0);
        for (int i = 0; i < userScores.size(); i++) {
            values.set(userScores.get(i) - 1, values.get(i) + 1);
            if (i == userScores.size() - 1) {
                listofLabels.get(userScores.get(i) - 1).setStyle("-fx-background-color: green");
            }
        }

        double maxScored = values.get(0);
        for (int i = 1; i < 6; i++) {
            if (maxScored < values.get(i)) {
                maxScored = values.get(i);
            }
        }

        for (int i = 0; i < 6; i++) {
            listofLabels.get(i).setPrefWidth(labelWidth * (values.get(i) / maxScored));
            listofLabels.get(i).setText(values.get(i).toString());

        }
    }

    @FXML
    public void initialize() {
        // labelWidth = labels.getLayoutBounds().getWidth();
        if (userScores.size() == 0 || userStats.size() == 0) {
            txtPlayed.setText(String.valueOf(0));
            txtWinPercentage.setText(String.valueOf(0));
            txtCurrentStreak.setText(String.valueOf(0));
            txtMaxStreak.setText(String.valueOf(0));
        } else {
            txtPlayed.setText(String.valueOf(userScores.size()));
            txtWinPercentage.setText(String.valueOf(userStats.get(0)));
            txtCurrentStreak.setText(String.valueOf(userStats.get(1)));
            txtMaxStreak.setText(String.valueOf(userStats.get(2)));
        }
        Image img = new Image("saveicon.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(25);
        view.setFitWidth(25);
        saveButton.setGraphic(view);
        Image img2 = new Image("reseticon.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(25);
        view2.setFitWidth(25);
        resetButton.setGraphic(view2);
        listofLabels.add(label1);
        listofLabels.add(label2);
        listofLabels.add(label3);
        listofLabels.add(label4);
        listofLabels.add(label5);
        listofLabels.add(label6);
    }

    @FXML
    void resetGame(ActionEvent event) throws Exception {
        // save the current file name
        // move back to the other controller
        // call a method in the main to save the file name
        FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent root = (Parent) FXMLLoader.load();
        MainSceneController controller = FXMLLoader.<MainSceneController>getController();
        Stage stage = (Stage) resetButton.getScene().getWindow();
        if (fileName.getText() == null || fileName.getText() == "") {
            controller.setData(fileName.getText(), false); // set "isloaded" in here too
        } else {
            controller.setData(fileName.getText(), isLoaded); // set "isloaded" in here too
        }
        stage.setScene(new Scene(root));
    }

    @FXML
    void saveGame(ActionEvent event) throws IOException {
        // write score to file
        // recalculate the stats and update it
        if (fileName.getText() != null) {
            FileWriter out = new FileWriter("./src/gameFiles/" + fileName.getText(), true);
            PrintWriter writer = new PrintWriter(out);
            for (int i = 0; i < userScores.size(); i++) {
                writer.println(String.valueOf(userScores.get(i)));
            }
            writer.close();
            userScores.clear();
            Scanner lineScanner = null;

            try {
                lineScanner = new Scanner(new FileReader("./src/gameFiles/" + fileName.getText()));
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }

            while (lineScanner.hasNextLine()) {
                String line = lineScanner.nextLine();
                if (line.length() == 1) {
                    userScores.add(Integer.parseInt(line)); // convert line to int number
                }
            }

            userStats = getPlayerStats(userScores);

            txtPlayed.setText(String.valueOf(userScores.size()));
            txtWinPercentage.setText(String.valueOf(userStats.get(0)));
            txtCurrentStreak.setText(String.valueOf(userStats.get(1)));
            txtMaxStreak.setText(String.valueOf(userStats.get(2)));

            System.out.println("game saved");

            ArrayList<Integer> values = new ArrayList<>();

            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);
            values.add(0);

            for (int i = 0; i < userScores.size(); i++) {
                values.set(userScores.get(i) - 1, values.get(userScores.get(i) - 1) + 1);
                if (i == userScores.size() - 1) {
                    listofLabels.get(userScores.get(i) - 1).setStyle("-fx-background-color: green");
                }
            }

            double maxScored = values.get(0);
            for (int i = 1; i < 6; i++) {
                if (maxScored < values.get(i)) {
                    maxScored = values.get(i);
                }
            }

            for (int i = 0; i < 6; i++) {
                listofLabels.get(i).setPrefWidth(labelWidth * (values.get(i) / maxScored));
                listofLabels.get(i).setText(values.get(i).toString());
            }
        }
    }

    ArrayList<Integer> getPlayerStats(ArrayList<Integer> scores) {
        int wins = 0;
        int currentStreak = 0;
        int maxStreak = 0;
        ArrayList<Integer> stats = new ArrayList<>();
        if (scores.size() == 0) {
            stats.add(0);
            stats.add(0);
            stats.add(0);
            return stats;
        }
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) <= 6) {
                wins += 1;
                currentStreak += 1;
            } else {
                if (currentStreak > maxStreak) {
                    maxStreak = currentStreak;
                }
                currentStreak = 0;
            }
        }
        if (maxStreak < currentStreak) {
            maxStreak = currentStreak;
        }
        stats.add((wins * 100) / scores.size());
        // might result in a error when it turns to float
        stats.add(currentStreak);
        stats.add(maxStreak);
        return stats;
    }
}