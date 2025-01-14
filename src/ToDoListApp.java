import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.kordamp.ikonli.javafx.FontIcon;

public class ToDoListApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List");

        // Criando título
        Text title = new Text("To-Do List");

        Font font = Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 36);
        if (font != null) {
            title.setFont(font);
        } else {
            System.out.println("Erro ao carregar a fonte.");
        }
        title.getStyleClass().add("title");

        // Campo de entrada para nova tarefa
        TextField taskInput = new TextField();
        taskInput.setPromptText("Adicione uma nova tarefa");
        taskInput.getStyleClass().add("text-field");

        // Botão para adicionar tarefa
        Button addButton = new Button();
        Text addButtonText = new Text("Adicionar tarefa");
        addButtonText.setFont(Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 14));
        addButton.setGraphic(addButtonText);
        addButton.getStyleClass().add("add-button");
        addButtonText.getStyleClass().add("add-button-text");

        // Lista para exibir tarefas
        ListView<String> taskList = new ListView<>();
        taskList.getStyleClass().add("list-view");

        // Definição do layout das células da lista
        taskList.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // GridPane para layout da célula
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(1); // Espaçamento horizontal
                    gridPane.setVgap(5);  // Espaçamento vertical, se necessário

                    Text taskText = new Text(item);
                    taskText.getStyleClass().add("task-text");
                    taskText.setWrappingWidth(250);

                    // Botão para checar tarefa feita
                    Button checkButton = new Button();

                    //Criando ícone de "Check"
                    FontIcon checkIcon = new FontIcon("fas-check");
                    checkIcon.setIconSize(20);
                    checkIcon.setIconColor(Color.GREEN);
                    checkButton.setGraphic(checkIcon);
                    checkButton.getStyleClass().add("check-button");

                    // Evento do botão "Check"
                    checkButton.setOnAction(e -> {
                        if (!item.contains("[Feito]")) {
                            int index = getIndex();
                            taskList.getItems().set(index, item + " [Feito]");

                            gridPane.getChildren().remove(checkButton);
                        }
                    });

                    //Botão para remover tarefa
                    Button removeButton = new Button();

                    //Criando ícone "Remover" tarefa
                    FontIcon removeIcon = new FontIcon("fas-times");
                    removeIcon.setIconSize(22);
                    removeIcon.setIconColor(Color.RED);
                    removeButton.setGraphic(removeIcon);
                    removeButton.getStyleClass().add("remove-button");

                    // Evento do botão "Remover"
                    removeButton.setOnAction(e -> {
                        taskList.getItems().remove(item);
                    });

                    // Estilo do texto riscado para tarefas feitas
                    if (item.contains("[Feito]")) {
                        taskText.setStyle("-fx-fill: gray; -fx-font-style: italic;");
                        taskText.setStrikethrough(true);
                    }

                    // Adicionando os elementos ao GridPane
                    gridPane.add(taskText, 0, 0); // Texto na coluna 0, linha 0
                    gridPane.add(checkButton, 1, 0); // Botão "Check" na coluna 1, linha 0
                    gridPane.add(removeButton, 2, 0); // Botão "Remover" na coluna 2, linha 0

                    // Ajustando alinhamento das células
                    GridPane.setHgrow(taskText, Priority.ALWAYS);

                    // Configurando a célula
                    setText(null);
                    setGraphic(gridPane);
                }
            }
        });

        // Evento do botão para adicionar tarefa
        addButton.setOnAction(e -> {
            String task = taskInput.getText();
            if (!task.isEmpty()) {
                taskList.getItems().add(task);
                taskInput.clear();
            }
        });

        // Layout do título
        VBox titleLayout = new VBox(title);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPrefHeight(100);

        //Créditos de desenvolvimento
        Text credits = new Text("Developed by Victor Benatti");
        credits.setFont(Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 14));
        credits.getStyleClass().add("credits");

        VBox creditsLayout = new VBox(credits);

        // Layout do conteúdo principal
        VBox contentLayout = new VBox(10, taskInput, addButton, taskList, creditsLayout);
        contentLayout.setAlignment(Pos.CENTER_LEFT);

        // Layout principal
        VBox mainLayout = new VBox(titleLayout, contentLayout);

        Scene scene = new Scene(mainLayout, 600, 800);
        primaryStage.setScene(scene);

        // Adicionando style.css
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}