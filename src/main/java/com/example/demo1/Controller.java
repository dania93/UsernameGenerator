package com.example.demo1;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button GenerateBtn;

    @FXML
    private Text textOfName;

    @FXML
    private Label label;

    final HttpClient httpClient = HttpClient.newHttpClient();

    private void generate() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.namefake.com/"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject obj = new JSONObject(response.body());
        textOfName.setText(obj.getString("username"));
    }

    private void popOver() throws NullPointerException, IllegalStateException {
        label.setText("copied");
        PopOver popover = new PopOver();
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.getChildren().add(label);
        popover.setAutoHide(true);
        popover.setContentNode(box);
        popover.show(textOfName);
    }

    private void clickToCopy() {
        textOfName.setOnMouseClicked(e -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(textOfName.getText()));
            clipboard.setContent(content);
            popOver();
        });
    }

    @FXML
    void processBtn() throws IOException, InterruptedException {
        generate();
        clickToCopy();
    }

}

