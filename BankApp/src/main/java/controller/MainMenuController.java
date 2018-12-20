package controller;

import java.io.IOException;
import java.util.Scanner;

import views.MenuOptions;

public class MainMenuController {
	MenuOptions options;
	private int stop;
	
	public MainMenuController(){
		options = new MenuOptions();
		stop = options.getEndCondition();
	}
	public void selectOption(int selection) {
		boolean isVerified = false;
		Login login = new Login();
		if (selection == 1) {
			RegistrationController registrationController = new RegistrationController();
			registrationController.call();
		} else if (selection == 2) {
			while (!isVerified) isVerified = login.call(2);
		} else if (selection == 3) {
			while (!isVerified) isVerified = login.call(3);
		} else if (selection == 4) {
			while (!isVerified) isVerified = login.call(4);
		}
		else if (selection == stop) System.out.println("Shutting down...");
		else System.out.println("No option selected!");
	}
	public void begin() {
		int selection = -1;
		while (selection != stop) {
			
			if (selection == stop) selectOption(stop);
			else selection = -1;
			
			try {
				selection = options.display();
				if (options.inBounds(selection)) selectOption(selection);
				else continue;
			} catch (IOException e) {
				System.out.println("Failed to execute selectOption(selection = options.display())");
				e.printStackTrace();
			}
		}
	}
}
