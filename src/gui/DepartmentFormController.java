package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

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

	private Department department;

	public void setDepartment(Department department) {
		this.department = department;
	}

	@FXML
	public void onBtnSaveAction() {
		System.out.println("onBtnSaveAction");
	}

	@FXML
	public void onBtnCancelAction() {
		System.out.println("onBtnCancelAction");
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

}
