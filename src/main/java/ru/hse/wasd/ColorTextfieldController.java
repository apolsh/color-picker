package ru.hse.wasd;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ColorTextfieldController extends AnchorPane
{
  @FXML
  public TextField colorValue;
  @FXML
  public Label labelValue;
  StringProperty labelText = new SimpleStringProperty("Цвет:");
  BooleanProperty isHex = new SimpleBooleanProperty(false);
  private ObjectProperty<EventHandler<ActionEvent>> propertyOnAction = new SimpleObjectProperty<EventHandler<ActionEvent>>();
  //private boolean isHex = true;

  public ColorTextfieldController()
  {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ColorTextfield.fxml"));
      loader.setController(this);
      loader.setRoot(this);
      loader.load();

      colorValue.setText("0");
      labelValue.setText(labelText.getValue());
    } catch (IOException exc) {
      // handle exception
    }
  }

  @FXML
  public void handleColorValueChange(javafx.event.ActionEvent actionEvent)
  {
    System.out.println(colorValue.getText());
  }

  @FXML
  public void initialize()
  {

    // listen for checkbox changes
    colorValue.textProperty().addListener((observable, oldValue, newValue) -> {
      if (getIsHex()) {
        hexValueHandler(newValue, oldValue);
      } else {
        decValueHandler(newValue, oldValue);
      }
    });

    colorValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue)
      {
        propertyOnAction.get().handle(new ActionEvent());
      }
    });

    colorValue.setOnAction(event -> propertyOnAction.get().handle(new ActionEvent()));
  }

  ;

  private void decValueHandler(String newValue, String oldValue)
  {
    try{
      int intNewValue = Integer.parseInt(newValue);
      if(intNewValue > 255){
        colorValue.setText(String.valueOf(255));
      }else if(intNewValue < 0){
        colorValue.setText(String.valueOf(0));
      }
    }catch(NumberFormatException e){
      colorValue.setText(oldValue);
    }
  }

  private void hexValueHandler(String newValue, String oldValue)
  {
    try{
      int intNewValue = Integer.parseInt(newValue, 16);
      if(intNewValue > 255){
        colorValue.setText("ff");
      }else if(intNewValue < 0){
        colorValue.setText(String.valueOf(0));
      }
    }catch(NumberFormatException e){
      colorValue.setText(oldValue);
    }
  }

  public String getLabelText()
  {
    return labelText.get();
  }

  public void setLabelText(String labelText)
  {
    this.labelText.set(labelText);
    this.labelValue.setText(labelText);
  }

  public StringProperty labelTextProperty()
  {
    return labelText;
  }

  public EventHandler<ActionEvent> getPropertyOnAction()
  {
    return propertyOnAction.get();
  }

  public ObjectProperty<EventHandler<ActionEvent>> propertyOnActionProperty()
  {
    return propertyOnAction;
  }

  public void setPropertyOnAction(EventHandler<ActionEvent> propertyOnAction)
  {
    this.propertyOnAction.set(propertyOnAction);
  }

  public int getColorInt(){
    if(getIsHex()){
      return Integer.parseInt(colorValue.getText(),16);
    }else{
      return Integer.parseInt(colorValue.getText());
    }
  }

  public boolean getIsHex()
  {
    return isHex.get();
  }

  public BooleanProperty isHexProperty()
  {
    return isHex;
  }

  public void setIsHex(boolean isHex)
  {
    if(getIsHex() != isHex){
      this.isHex.set(isHex);
      if(isHex){
        colorValue.setText(Integer.toHexString(Integer.parseInt(colorValue.getText())));
      }else{
        colorValue.setText(String.valueOf(Integer.parseInt(colorValue.getText(), 16)));
      }

    }

  }
}
