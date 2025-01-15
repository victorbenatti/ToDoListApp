import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import org.kordamp.ikonli.javafx.FontIcon;

public class ToDoListApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To-Do List");

        // Criando título
        Text toText = new Text("To");
        toText.getStyleClass().add("to-text");

        Text doListText = new Text("-Do List");
        doListText.getStyleClass().add("do-list-text");

        //Criando fonte importada Google Fonts
        Font font = Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 36);

        if (font != null) {
            toText.setFont(font);
            doListText.setFont(font);
        } else {
            System.out.println("Erro ao carregar fonte");
        }

        // Criando TextFlow ("To" + "-Do List")
        TextFlow titleFlow = new TextFlow(toText, doListText);
        titleFlow.setTextAlignment(TextAlignment.CENTER);
        titleFlow.getStyleClass().add("title-flow");

        // Campo de entrada para nova tarefa
        TextField taskInput = new TextField();
        taskInput.setPromptText("Adicione uma nova tarefa");
        taskInput.getStyleClass().add("text-field");

        // Botão para adicionar tarefa
        Button addButton = new Button();
        FontIcon addIcon = new FontIcon("fas-plus");
        addIcon.setIconSize(20);
        addIcon.setIconColor(Color.WHITE);
        addButton.setGraphic(addIcon);
        addButton.getStyleClass().add("add-button");

        // Alinhar horizontalmente o campo de texto e o botão
        HBox inputLayout = new HBox(10);
        inputLayout.getChildren().addAll(taskInput, addButton);
        inputLayout.setAlignment(Pos.CENTER_LEFT);

        // Configuração para que o TextField cresça para preencher o espaço disponível
        HBox.setHgrow(taskInput, Priority.ALWAYS);
        taskInput.setMaxWidth(Double.MAX_VALUE);

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
                    gridPane.setHgap(0); // Espaçamento horizontal
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

                    // Botão para remover tarefa
                    Button removeButton = new Button();

                    // Criando ícone "Remover" tarefa
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

        // Evento de pressionar enter e a tarefa ser adicionada
        taskInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    String task = taskInput.getText();
                    if (!task.isEmpty()) {
                        taskList.getItems().add(task);
                        taskInput.clear();
                    }
                    break;
                default:
                    break;
            }
        });

        // Layout do título
        VBox titleLayout = new VBox(titleFlow);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPrefHeight(100);

        // Créditos de desenvolvimento
        Text credits = new Text("Developed by Victor Benatti");
        credits.setFont(Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 14));
        credits.getStyleClass().add("credits");

        // Link de acesso ao GitHub
        Hyperlink creditsLink = new Hyperlink("GitHub");
        creditsLink.setFont(Font.loadFont(getClass().getResource("/resources/fonts/Exo2-Bold.ttf").toExternalForm(), 18));
        creditsLink.getStyleClass().add("credits-link");
        creditsLink.setOnAction(e -> {
            try {
                // Abre o link no navegador
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/victorbenatti"));
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        // Layout dos créditos
        VBox creditsLayout = new VBox(credits, creditsLink);
        creditsLayout.setAlignment(Pos.CENTER);
        creditsLayout.setPadding(new Insets(10, 0, 0, 0));

        // Layout do conteúdo principal
        VBox contentLayout = new VBox(10, inputLayout, taskList, creditsLayout);
        contentLayout.setAlignment(Pos.CENTER_LEFT);

        // Layout principal do programa
        VBox mainLayout = new VBox(titleLayout, contentLayout);

        Scene scene = new Scene(mainLayout, 600, 800);
        primaryStage.setScene(scene);

        // Adicionando styles.css
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}