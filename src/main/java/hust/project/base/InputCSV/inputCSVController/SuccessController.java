package hust.project.base.InputCSV.inputCSVController;

import hust.project.base.InputCSV.inputCSVView.SuccessView;

public class SuccessController {
    public static void showSuccess(String header, String message) {
        SuccessView.displaySuccessMessage(header, message);
    }
}
