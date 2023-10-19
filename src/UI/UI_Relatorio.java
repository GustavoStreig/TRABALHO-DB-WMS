package UI;

import java.util.Scanner;

import controller.ControllerFornecedor;
import controller.ControllerItem;
import controller.ControllerItemEstoque;
import controller.ControllerLocalizacao;
import exeption.WmsException;
import model.Fornecedor;
import model.Item;
import model.ItemEstoque;
import model.Localizacao;

public class UI_Relatorio {

	public static void buscar_itens_endereco(Scanner sc) {

		try {

			ControllerItemEstoque controllerItemEstoque = new ControllerItemEstoque();

			int contador = 1;
			
			System.out.println();
			for (ItemEstoque itemEstoque : controllerItemEstoque.buscarItensEstoque()) {
				System.out.println(contador + "- " + itemEstoque.toString());
				contador += 1;
			}
			System.out.println();
			UI_Menus.tela_relatorios(sc);
			
		} catch (WmsException e) {
	
			System.out.println(e.getMessage());
			UI_Menus.tela_relatorios(sc);
			
		} catch (Exception e) {
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			buscar_fornecedor(sc);
		}
	}

	public static void buscar_itens_fornecedor(Scanner sc) {

		try {
			System.out.printf("\nDigite o CNPJ do fornecedor que deseja buscar os itens:");
			sc.nextLine();
			String cnpj = sc.nextLine();
			ControllerItem controllerItem = new ControllerItem();

			int contador = 1;
			System.out.println();
			for (Item item : controllerItem.buscarItensPorFornecedor(cnpj)) {
				System.out.println(contador + "- " + item.toString());
				contador += 1;
			}
			
			System.out.println();
			UI_Menus.tela_relatorios(sc);
		
		} catch (WmsException e) {
			System.out.println(e.getMessage());
			System.out.printf("Deseja tentar inserir outro CNPJ?(S/N)\n");
			String texto = sc.next();
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Relatorio.buscar_itens_fornecedor(sc);
			}else {
				UI_Menus.clearScreen();
				UI_Menus.tela_relatorios(sc);
			}

		} catch (Exception e) {
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			buscar_fornecedor(sc);
		}
	}

	public static void buscar_itens(Scanner sc) {
		int contador = 1;
		System.out.println();
		ControllerItem controllerItem = new ControllerItem();
		for (Item item : controllerItem.buscarItens()) {
			System.out.println(contador + "- " + item.toString());
			contador += 1;
		}
		System.out.println();
		UI_Menus.tela_relatorios(sc);
	}

	public static void buscar_fornecedor(Scanner sc) {
		int contador = 1;
		System.out.println();
		ControllerFornecedor controllerFornecedor = new ControllerFornecedor();
		for (Fornecedor fornecedor : controllerFornecedor.buscarFornecedores()) {
			System.out.println(contador + "- " + fornecedor.toString());
			contador += 1;
		}
		System.out.println();
		UI_Menus.tela_relatorios(sc);
	}

	public static void buscar_localizacao(Scanner sc) {
		int contador = 1;
		System.out.println();
		ControllerLocalizacao controllerLocalizacao = new ControllerLocalizacao();
		for (Localizacao localizacao : controllerLocalizacao.buscarLocalizacoes()) {
			System.out.println(contador + "- " + localizacao.toString());
			contador += 1;
		}
		System.out.println();
		UI_Menus.tela_relatorios(sc);
	}

}
