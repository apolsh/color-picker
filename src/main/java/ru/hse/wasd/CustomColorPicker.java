package ru.hse.wasd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomColorPicker
{
  @FXML
  public ColorTextfieldController redInput;
  @FXML
  public ColorTextfieldController greenInput;
  @FXML
  public ColorTextfieldController blueInput;
  @FXML
  public Rectangle colorSample;
  @FXML
  public ToggleGroup colorMode;

  ObjectProperty<java.awt.Color> color = new SimpleObjectProperty<>(java.awt.Color.black);

  boolean isHex = false;



  @FXML
  public void initialize() {

    EventHandler<javafx.event.ActionEvent> inputActionHandler = new EventHandler<javafx.event.ActionEvent>()
    {
      @Override
      public void handle(javafx.event.ActionEvent event)
      {
        fillSample();
      }
    };

    redInput.setPropertyOnAction(inputActionHandler);
    greenInput.setPropertyOnAction(inputActionHandler);
    blueInput.setPropertyOnAction(inputActionHandler);

    colorMode.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      RadioButton selectedBtn = (RadioButton) newValue;
      isHex = selectedBtn.getText().equals("hex");
      redInput.setIsHex(isHex);
      greenInput.setIsHex(isHex);
      blueInput.setIsHex(isHex);
    });
  }

  private void fillSample(){
    int red = redInput.getColorInt();
    int green = greenInput.getColorInt();
    int blue = blueInput.getColorInt();

    javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(red, green, blue, 1);

    colorSample.setFill(fxColor);

  }

  public java.awt.Color getColor()
  {
    return color.get();
  }

  public ObjectProperty<java.awt.Color> colorProperty()
  {
    return color;
  }
}
