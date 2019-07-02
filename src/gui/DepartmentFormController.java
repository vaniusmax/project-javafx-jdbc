package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DBException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	@FXML
	private TextField txtFieldId;

	@FXML
	private TextField txtFieldName;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	@FXML
	private Label lblError;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	private Department department;
	
	private DepartmentService service;

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		
		if(department == null) {
			throw new IllegalStateException("Department was null");
		}
		if(service == null) {
			throw new IllegalStateException("service was null");
		}
		try {
			department = getFormData();
			service.saveOrUpdate(department);	
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} 
		catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}
		catch (DBException e) {
			Alerts.showAlert("Error", "Error to save or update", e.getMessage(), AlertType.ERROR	);
		}
		
	}
	
	private void notifyDataChangeListeners() {
		for(DataChangeListener listener: dataChangeListeners) {
			listener.onDataChanged();
		}
		
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		
		dataChangeListeners.add(listener);
		
	}

	private Department getFormData() {
		
		Department dep = new Department();
		
		ValidationException exception = new ValidationException("Validate Error!");
		
		dep.setId(Utils.tryParseToInt(txtFieldId.getText()));
		
		if(txtFieldName.getText() == null || txtFieldName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty.");
		}
		
		dep.setName(txtFieldName.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return dep;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtFieldId);
		Constraints.setTextFieldMaxLength(txtFieldName, 30);
	}

	public void updateFormData() {
		if (department == null) {
			throw new IllegalStateException("Error: Department was null");
		}
		
		txtFieldId.setText(String.valueOf(department.getId()));
		txtFieldName.setText(department.getName());
        
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")) {
			lblError.setText(errors.get("name"));
		}
		
	}

}
