import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainSceneController {
    // add reset button
    // save button
    // load button
    // 
    
    public static ArrayList<String> wordsIntoArray(String file){
        ArrayList<String> legalWords = new ArrayList<String>();

        Scanner lineScanner = null;

        try{
            lineScanner = new Scanner(new FileReader("./lib/words.txt"));
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
            System.exit(0);
        }

        while(lineScanner.hasNextLine()){
            String line = lineScanner.nextLine();
            legalWords.add(line);
        }

        return legalWords;
    }

    @FXML
    private TextField tfTitle;

    @FXML
    void btnOKClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }

    
    @FXML
    private Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,enter,delete,loadButton,saveButton,resetButton;
    
    @FXML
    private TextField fileName,letter1,letter10,letter11,letter12,letter13,letter14,letter15,letter16,letter17,letter18,letter19,letter2,letter20,letter21,letter22,letter23,letter24,letter25,letter26,letter27,letter28,letter29,letter3,letter30,letter4,letter5,letter6,letter7,letter8,letter9;
    
    private int currentLetter = 0;
    ArrayList<TextField> letters = new ArrayList<TextField>();
    ArrayList<String> legalWords = new ArrayList<String>();
    ArrayList<String> lastGamePlayed = new ArrayList<>();
    ArrayList<String> guessedWords = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
    ArrayList<Integer> stats = new ArrayList<>();
    ArrayList<Button> letterButtons = new ArrayList<Button>();
    ArrayList<Button> guessedWordButtons = new ArrayList<>();
    Random rand = new Random();
    String actualWord;
    boolean isFinished = false;
    boolean fileLoaded = false;
    
    int getLetterNumber(){
        return guessedWords.size()*5 + currentLetter;
    }
    
    
    @FXML
    public void initialize(){
        letters.add(letter1);
        letters.add(letter2);
        letters.add(letter3);
        letters.add(letter4);
        letters.add(letter5);
        letters.add(letter6);
        letters.add(letter7);
        letters.add(letter8);
        letters.add(letter9);
        letters.add(letter10);
        letters.add(letter11);
        letters.add(letter12);
        letters.add(letter13);
        letters.add(letter14);
        letters.add(letter15);
        letters.add(letter16);
        letters.add(letter17);
        letters.add(letter18);
        letters.add(letter19);
        letters.add(letter20);
        letters.add(letter21);
        letters.add(letter22);
        letters.add(letter23);
        letters.add(letter24);
        letters.add(letter25);
        letters.add(letter26);
        letters.add(letter27);
        letters.add(letter28);
        letters.add(letter29);
        letters.add(letter30);
        letterButtons.add(a);
        letterButtons.add(b);
        letterButtons.add(c);
        letterButtons.add(d);
        letterButtons.add(e);
        letterButtons.add(f);
        letterButtons.add(g);
        letterButtons.add(h);
        letterButtons.add(i);
        letterButtons.add(j);
        letterButtons.add(k);
        letterButtons.add(l);
        letterButtons.add(m);
        letterButtons.add(n);
        letterButtons.add(o);
        letterButtons.add(p);
        letterButtons.add(q);
        letterButtons.add(r);
        letterButtons.add(s);
        letterButtons.add(t);
        letterButtons.add(u);
        letterButtons.add(v);
        letterButtons.add(w);
        letterButtons.add(x);
        letterButtons.add(y);
        letterButtons.add(z);
        
        legalWords = wordsIntoArray("./lib/words.txt");
        actualWord = legalWords.get(rand.nextInt(legalWords.size()));
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
    }

    String getCurrentGuess(){
        String currentGuess = letters.get(getLetterNumber()-5).getText() + letters.get(getLetterNumber()-4).getText() + letters.get(getLetterNumber()-3).getText() + letters.get(getLetterNumber()-2).getText()  + letters.get(getLetterNumber()-1).getText();
        currentGuess = currentGuess.toLowerCase();
        return currentGuess;
    }

    boolean legalGuess(String guess){
        if(!legalWords.contains(guess)){
            return false;
        }
        if(guess.length() < 5){
            return false;
        }
        return true;
    }

    void setData(String file, boolean loaded){
        fileName.setText(file);
        fileLoaded = loaded;
        if(fileLoaded == true){
            loadFile();
        }
    }

    void changeTileColors(ArrayList<String> tileColors){
        for(int i = getLetterNumber()-5; i < getLetterNumber(); i++){
            String tileColor = tileColors.get(i%5);
            if(tileColor == "green"){
                letters.get(i).setStyle("-fx-background-color: #c6f7c2;");
            }else if(tileColor == "yellow"){
                letters.get(i).setStyle("-fx-background-color: #fdf5a3;");
            }else{
                letters.get(i).setStyle("-fx-background-color: gray;");
            }
        }
    }

    void changeButtonColors(ArrayList<String> tileColors){
        for(int i = 0; i < 5; i++){
            String tileColor = tileColors.get(i);
            if(tileColor == "green"){
                guessedWordButtons.get(i).setStyle("-fx-background-color: #c6f7c2;");
            }else if(tileColor == "yellow" && guessedWordButtons.get(i).getStyle() != "-fx-background-color: #c6f7c2;"){
                guessedWordButtons.get(i).setStyle("-fx-background-color: #fdf5a3;");
            }else if(tileColor == "gray" && guessedWordButtons.get(i).getStyle() != "-fx-background-color: #c6f7c2;" && guessedWordButtons.get(i).getStyle() != "-fx-background-color: #fdf5a3;"){
                guessedWordButtons.get(i).setStyle("-fx-background-color: gray;");
                guessedWordButtons.get(i).setDisable(true);
            }
        }
    }

    ArrayList<String> getTileColors(String guess){
        ArrayList<String> tileColors = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            char letter = guess.charAt(i);
            if(letter == actualWord.charAt(i)){
                tileColors.add("green");
            }else if(actualWord.contains(String.valueOf(letter))){
                tileColors.add("yellow");
            }else{
                tileColors.add("gray");
            }
        }
        return tileColors;
    }

    boolean checkGuess(ArrayList<String> tileColors){
        for(int i = 0; i < 5; i++){
            if(tileColors.get(i) != "green"){
                return false;
            }
        }
        return true;
    }

    void showError(String errorText){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(errorText);
        alert.showAndWait();
    }

    void addScoreToFile(int score) throws IOException{
        // add score to file
        if(fileLoaded){
            FileWriter out = new FileWriter("./src/gameFiles/" + fileName.getText(), true);
            PrintWriter writer = new PrintWriter(out);
            writer.println(String.valueOf(score));
            writer.close();
            scores.add(score);
            stats = getPlayerStats(scores);
        }else{
            scores.add(score);
            stats = getPlayerStats(scores);
        }
    }

    boolean saveFile(String fileName, boolean isFinished){
        // if game is active reset game and add info to file.
        // if game is finished then add score to file.
        if(guessedWords.size() == 0){
            return true;
        }else if(isFinished == false){
            String words = actualWord;
            words += " ";
            PrintWriter writer;
            try{
                FileWriter out = new FileWriter("./src/gameFiles/"+fileName, true);
                writer = new PrintWriter(out);
            }catch(IOException e){
                System.out.println(e);
                return false;
            }
            for(int i = 0; i < guessedWords.size();i++){
                words += guessedWords.get(i);
                if(i < guessedWords.size()-1){
                    words += " ";
                }
            }
            writer.println(words);
            writer.close();
        }else if(isFinished == true){
            try{
                addScoreToFile(guessedWords.size());
            }catch(IOException e){
                System.out.println(e);
                return false;
            }
        }
        return true;
    }

    void resetGame(){
        // enable all buttons, clear all letters
        for(int i = 0; i < 30; i++){
            letters.get(i).setText("");
            letters.get(i).setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");
        }
        for(int i = 0; i < letterButtons.size(); i++){
            letterButtons.get(i).setStyle("-fx-background-color: #dddddd;");
            letterButtons.get(i).setDisable(false);
        }
        actualWord = legalWords.get(rand.nextInt(legalWords.size()));
        guessedWordButtons.clear();
        guessedWords.clear();
        currentLetter = 0;
    }
    
    boolean loadFile(){
        Scanner lineScanner = null;

        try{
            lineScanner = new Scanner(new FileReader("./src/gameFiles/" + fileName.getText()));
        }
        catch (FileNotFoundException e){
            showError("File not valid");
            return false;
        }
        resetGame();
        lastGamePlayed.clear();
        while(lineScanner.hasNextLine()){
            String line = lineScanner.nextLine();
            if(line.length() > 1 && !lineScanner.hasNextLine()){ // has to be the last line or no reading from it
                actualWord = line.substring(0, 5);
                for(int i = 6; i < line.length(); i = i + 6){
                    lastGamePlayed.add(line.substring(i, i+5));
                }
            }else if(line.length() == 1){
                scores.add(Integer.parseInt(line)); // convert line to int number
            }
        }
        stats = getPlayerStats(scores);
        for(int i = 0; i < lastGamePlayed.size(); i++){
            clickLettersFromWord(lastGamePlayed.get(i));
            try{
                btnEnter(null);
            }catch(Exception e){
                showError("Save file not valid");
            }
        }
        lineScanner.close();
        return true;
    }

    void clickLettersFromWord(String word){
        for(int i = 0; i < word.length();i++){
            if(word.charAt(i) == 'a'){
                btnA(null);
            }
            if(word.charAt(i) == 'b'){
                btnB(null);
            }
            if(word.charAt(i) == 'c'){
                btnC(null);
            }
            if(word.charAt(i) == 'd'){
                btnD(null);
            }
            if(word.charAt(i) == 'e'){
                btnE(null);
            }
            if(word.charAt(i) == 'f'){
                btnF(null);
            }
            if(word.charAt(i) == 'g'){
                btnG(null);
            }
            if(word.charAt(i) == 'h'){
                btnH(null);
            }
            if(word.charAt(i) == 'i'){
                btnI(null);
            }
            if(word.charAt(i) == 'j'){
                btnJ(null);
            }
            if(word.charAt(i) == 'k'){
                btnK(null);
            }
            if(word.charAt(i) == 'l'){
                btnL(null);
            }
            if(word.charAt(i) == 'm'){
                btnM(null);
            }
            if(word.charAt(i) == 'n'){
                btnN(null);
            }
            if(word.charAt(i) == 'o'){
                btnO(null);
            }
            if(word.charAt(i) == 'p'){
                btnP(null);
            }
            if(word.charAt(i) == 'q'){
                btnQ(null);
            }
            if(word.charAt(i) == 'r'){
                btnR(null);
            }
            if(word.charAt(i) == 's'){
                btnS(null);
            }
            if(word.charAt(i) == 't'){
                btnT(null);
            }
            if(word.charAt(i) == 'u'){
                btnU(null);
            }
            if(word.charAt(i) == 'v'){
                btnV(null);
            }
            if(word.charAt(i) == 'w'){
                btnW(null);
            }
            if(word.charAt(i) == 'x'){
                btnX(null);
            }
            if(word.charAt(i) == 'y'){
                btnY(null);
            }
            if(word.charAt(i) == 'z'){
                btnZ(null);
            }
        }
    }

    ArrayList<Integer> getPlayerStats(ArrayList<Integer> scores){
        int wins = 0;
        int currentStreak = 0;
        int maxStreak = 0;
        ArrayList<Integer> stats = new ArrayList<>();
        if(scores.size() == 0){
            stats.add(0);
            stats.add(0);
            stats.add(0);
            return stats;
        }
        for(int i = 0; i < scores.size(); i++){
            if(scores.get(i) <= 6){
                wins += 1;
                currentStreak += 1;
            }else{
                if(currentStreak > maxStreak){
                    maxStreak = currentStreak;
                }
                currentStreak = 0;
            }
        }
        if(maxStreak < currentStreak){
            maxStreak = currentStreak;
        }
        stats.add((wins*100)/scores.size());
        // might result in a error when it turns to float
        stats.add(currentStreak);
        stats.add(maxStreak);
        return stats;
    }

    @FXML
    void loadGame(ActionEvent event) {
        if(loadFile()){
            fileLoaded = true;
        }else{
            fileLoaded = false;
            showError("File not valid");
        }
    }
    
    @FXML
    void saveGame(ActionEvent event) {
        if(saveFile(fileName.getText(),isFinished)){
            resetGame();
        }else{
            showError("File not valid");
        }
    }

    @FXML
    void resetGame(ActionEvent event) {
        resetGame();
    }

    @FXML
    void btnDelete(ActionEvent event) {
        if(currentLetter > 0){
            currentLetter -= 1;
        }
        letters.get(getLetterNumber()).setText("");
        if(!guessedWordButtons.isEmpty()){
            guessedWordButtons.removeLast();
        }
    }

    @FXML
    void btnEnter(ActionEvent event) throws Exception{
        // game finishes deletes the loaded words and puts the game score in
        if(currentLetter == 5){
            String currentGuess = getCurrentGuess();
            if(legalGuess(currentGuess)){
                // guess is legal
                ArrayList<String> tileColors = getTileColors(currentGuess);
                changeTileColors(tileColors);
                changeButtonColors(tileColors);
                boolean isGuessCorrect = checkGuess(tileColors);
                guessedWords.add(currentGuess);
                if(isGuessCorrect){
                    // display winning words
                    System.out.println("you guessed right");
                    isFinished = true;
                    // switch controller
                    addScoreToFile(guessedWords.size());
                    FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("StatisticsScene.fxml"));
                    Parent root = (Parent)FXMLLoader.load();
                    StatisticsSceneController controller = FXMLLoader.<StatisticsSceneController>getController();
                    Stage stage = (Stage) enter.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    if(fileName.getText() == null){
                        controller.setData(stats, scores, "", actualWord, fileLoaded);
                    }else{
                        controller.setData(stats, scores, fileName.getText(), actualWord, fileLoaded);
                    }
                }
                currentLetter = 0;
                guessedWordButtons.clear();
            }else{
                showError("This word isn't valid");
            }
        }else{
            showError("There isn't enough letters");
        }
        if(guessedWords.size() == 6){
            isFinished = true;
            addScoreToFile(7); // lost if score is 7
            FXMLLoader FXMLLoader = new FXMLLoader(getClass().getResource("StatisticsScene.fxml"));
            Parent root = (Parent)FXMLLoader.load();
            StatisticsSceneController controller = FXMLLoader.<StatisticsSceneController>getController();
            if(fileName.getText() == null){
                controller.setData(stats, scores, "", actualWord, fileLoaded);
            }else{
                controller.setData(stats, scores, fileName.getText(), actualWord, fileLoaded);
            }
            Stage stage = (Stage) enter.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    void btnA(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("A");
                currentLetter += 1;
            }
            guessedWordButtons.add(a);
        }
    }

    @FXML
    void btnB(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("B");
                currentLetter += 1;
            }
            guessedWordButtons.add(b);
        }
    }

    @FXML
    void btnC(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("C");
                currentLetter += 1;
            }
            guessedWordButtons.add(c);
        }
    }

    @FXML
    void btnD(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("D");
                currentLetter += 1;
            }
            guessedWordButtons.add(d);
        }
    }


    @FXML
    void btnE(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("E");
                currentLetter += 1;
            }
            guessedWordButtons.add(e);
        }
    }


    @FXML
    void btnF(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("F");
                currentLetter += 1;
            }
            guessedWordButtons.add(f);
        }
    }

    @FXML
    void btnG(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("G");
                currentLetter += 1;
            }
            guessedWordButtons.add(g);
        }
    }

    @FXML
    void btnH(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("H");
                currentLetter += 1;
            }
            guessedWordButtons.add(h);
        }
    }

    @FXML
    void btnI(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("I");
                currentLetter += 1;
            }
            guessedWordButtons.add(i);
        }
    }

    @FXML
    void btnJ(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("J");
                currentLetter += 1;
            }
            guessedWordButtons.add(j);
        }
    }

    @FXML
    void btnK(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("K");
                currentLetter += 1;
            }
            guessedWordButtons.add(k);
        }
    }

    @FXML
    void btnL(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("L");
                currentLetter += 1;
            }
            guessedWordButtons.add(l);
        }
    }

    @FXML
    void btnM(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("M");
                currentLetter += 1;
            }
            guessedWordButtons.add(m);
        }
    }

    @FXML
    void btnN(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("N");
                currentLetter += 1;
            }
            guessedWordButtons.add(n);
        }
    }

    @FXML
    void btnO(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("O");
                currentLetter += 1;
            }
            guessedWordButtons.add(o);
        }
    }

    @FXML
    void btnP(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("P");
                currentLetter += 1;
            }
            guessedWordButtons.add(p);
        }
    }

    @FXML
    void btnQ(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("Q");
                currentLetter += 1;
            }
            guessedWordButtons.add(q);
        }
    }

    @FXML
    void btnR(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("R");
                currentLetter += 1;
            }
            guessedWordButtons.add(r);
        }
    }

    @FXML
    void btnS(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("S");
                currentLetter += 1;
            }
            guessedWordButtons.add(s);
        }
    }

    @FXML
    void btnT(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("T");
                currentLetter += 1;
            }
            guessedWordButtons.add(t);
        }
    }

    @FXML
    void btnU(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("U");
                currentLetter += 1;
            }
            guessedWordButtons.add(u);
        }
    }

    @FXML
    void btnV(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("V");
                currentLetter += 1;
            }
            guessedWordButtons.add(v);
        }
    }

    @FXML
    void btnW(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("W");
                currentLetter += 1;
            }
            guessedWordButtons.add(w);
        }
    }

    @FXML
    void btnX(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("X");
                currentLetter += 1;
            }
            guessedWordButtons.add(x);
        }
    }

    @FXML
    void btnY(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("Y");
                currentLetter += 1;
            }
            guessedWordButtons.add(y);
        }
    }

    @FXML
    void btnZ(ActionEvent event) {
        if(isFinished == false){
            if(currentLetter < 5){
                letters.get(getLetterNumber()).setText("Z");
                currentLetter += 1;
            }
            guessedWordButtons.add(z);
        }
    }
}
