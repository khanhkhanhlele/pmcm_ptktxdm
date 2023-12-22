package hust.project.base.InputCSV.inputCSVController;

import hust.project.base.InputCSV.inputCSVView.InvalidView;

public class InvalidController {
    public static void showError(String header, String message) {
        InvalidView.displayErrorMessage(header, message);
    }
}
