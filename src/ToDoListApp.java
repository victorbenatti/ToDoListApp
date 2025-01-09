import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class ToDoListApp extends Application {
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("To-Do List");

        //Criando o título do programa
        Label title = new Label("To-Do List");
        title.getStyleClass().add("title");

        //Campo de entrada para nova tarefa
        TextField taskInput = new TextField();
        taskInput.setPromptText("Adicione uma nova tarefa");
        taskInput.getStyleClass().add("text-field");

        //Botão para add tarefa
        Button addButton = new Button("Adicionar tarefa");
        addButton.getStyleClass().add("button");

        //Botão para remover tarefa
        Button removeButton = new Button("Remover tarefa");
        removeButton.getStyleClass().add("remove-button");

        Button checkButton = new Button("Check");
        checkButton.getStyleClass().add("check-button");

        //Lista para exibir tarefas
        ListView<String> taskList = new ListView<>();
        taskList.getStyleClass().add("list-view");

        taskList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.contains("[Feito]")) {
                        setStyle("-fx-text-fill: gray; -fx-font-style: italic; -fx-strikethrough: true;");
                    } else {
                        setStyle(""); // Remove estilos para tarefas normais
                    }
                }
            }
        });


        //Evento do botão para adicionar tarefa
        addButton.setOnAction(e -> {
            String task = taskInput.getText();
            if(!task.isEmpty()){
                taskList.getItems().add(task); // Adiciona a tarefa na lista
                taskInput.clear(); // Limpa o campo de entrada
            }
        });

        //Evento do botão para remover tarefa
        removeButton.setOnAction(e -> {
            String selectedTask = taskList.getSelectionModel().getSelectedItem();
            if(selectedTask != null){
                taskList.getItems().remove(selectedTask);
            }
        });

        //Evento do botão check: tarefa feita
        checkButton.setOnAction(e -> {
            String selectedTask = taskList.getSelectionModel().getSelectedItem();
            if(selectedTask != null){
                //Remove a tarefa original
                taskList.getItems().remove(selectedTask);

                //Adiciona a tarefa com o estilo riscado
                String styledTask = selectedTask + " [Feito]";
                taskList.getItems().add(styledTask);
            }
        });

        //Vbox para o título
        VBox titleLayout = new VBox(title);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPrefHeight(100);

        //Vbox para o conteúdo
        VBox contentLayout = new VBox(10, taskInput, addButton, taskList, removeButton, checkButton);
        //contentLayout.setAlignment(Pos.TOP_CENTER);

        //Layout principal
        VBox mainLayout = new VBox(titleLayout, contentLayout);

        Scene scene = new Scene(mainLayout, 600, 800);
        primaryStage.setScene(scene);

        //Adicionando style.css
        System.out.println("Tentando carregar o CSS...");
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
        System.out.println("CSS carregado com sucesso!");

        Font font = Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Regular.ttf").toExternalForm(), 36);
        if (font != null) {
            System.out.println("Fonte carregada com sucesso: " + font.getName());
            title.setFont(font); // Aplica a fonte diretamente ao título
        } else {
            System.out.println("Erro ao carregar a fonte.");
        }

        //title.setStyle("-fx-font-family: 'Verdana'; -fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
