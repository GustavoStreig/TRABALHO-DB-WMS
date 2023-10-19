package application;

import java.util.Scanner;

import UI.UI_Menus;

public class Program {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		UI_Menus.tela_inicializacao(scanner);
		UI_Menus.clearScreen();

	}

}
