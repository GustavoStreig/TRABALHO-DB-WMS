package UI;

import java.util.Scanner;

import controller.ControllerFornecedor;
import controller.ControllerItem;
import controller.ControllerLocalizacao;
import exeption.WmsException;
import model.Fornecedor;
import model.Item;
import model.Localizacao;

public class UI_Remocao {

	public static void removerFornecedor(Scanner sc) {

		try {
		
			ControllerFornecedor controllerFornecedor = new ControllerFornecedor();
			
			int contador=1;
			System.out.println();
			for (Fornecedor fornecedor : controllerFornecedor.buscarFornecedores()) {
				System.out.println(contador + "- " + fornecedor.imprimirCnpjRazaoSocial());
				contador += 1;
			}
			
			System.out.printf("\nDigite o cnpj do fornecedor que deseja remover:");
			sc.nextLine();
			String cnpj = sc.nextLine();		
			
			System.out.printf("\nTem certeza que deseja remover esse fornecedor?(S/N)\n");
			String confirmacao = sc.next();
			
			if(confirmacao.equalsIgnoreCase("S")) {
				controllerFornecedor.removerFornecedor(cnpj);
				UI_Menus.tela_remover_registros(sc);
			}else {
				UI_Menus.tela_remover_registros(sc);
			}
			
		} catch (WmsException e) {
			System.out.println(e.getMessage());
			System.out.printf("\nDeseja tentar remover outro fornecedor?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Remocao.removerFornecedor(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_remover_registros(sc);
			}

		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			removerFornecedor(sc);;
		}
	}

	public static void removerItem(Scanner sc) {

		try {
		
			ControllerItem controllerItem = new ControllerItem();
			
			int contador=1;
			System.out.println();
			for (Item item : controllerItem.buscarItens()) {
				System.out.println(contador + "- " + item.imprimirCodigoNome());
				contador += 1;
			}
			
			System.out.printf("\nDigite o codigo do item que deseja remover:");
			int codigo = sc.nextInt();		
			
			System.out.printf("\nTem certeza que deseja remover esse item?(S/N)\n");
			String confirmacao = sc.next();
			
			if(confirmacao.equalsIgnoreCase("S")) {
				controllerItem.removerItem(codigo);
				UI_Menus.tela_remover_registros(sc);
			}else {
				UI_Menus.tela_remover_registros(sc);
			}
			
			controllerItem.removerItem(codigo);
			UI_Menus.tela_remover_registros(sc);
		
		} catch (WmsException e) {
			System.out.println(e.getMessage());
	
			System.out.printf("\nDeseja tentar remover outro item?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Remocao.removerItem(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_remover_registros(sc);
			}
		

		} catch (Exception e) {
			sc.next();
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			removerItem(sc);
		}
	}
	
	public static void removerLocalizacao(Scanner sc) {

		try {
		
			ControllerLocalizacao controllerLocalizacao = new ControllerLocalizacao();
			
			int contador=1;
			System.out.println();
			for (Localizacao localizacao : controllerLocalizacao.buscarLocalizacoes()) {
				System.out.println(contador + "- " + localizacao.toString());
				contador += 1;
			}
			
			Localizacao localizacao = null;
			
			System.out.printf("\nDigite a o endereço que deseja excluir(S C P N): ");
			sc.nextLine();
			String endereco = sc.nextLine();

			String[] numerosString = endereco.split(" ");
			
			if (numerosString.length == 4) {
				int[] numeros = new int[4];
				for (int i = 0; i < 4; i++) {
					numeros[i] = Integer.parseInt(numerosString[i]);
				}
				localizacao = new Localizacao(numeros[0], numeros[1], numeros[2], numeros[3]);
			}
			
			 if (!ControllerLocalizacao.verificarExistenciaLocalizacao(localizacao)) {
		            throw new WmsException("\nA localização inserida não está cadastrada.");
		        }
		        
			System.out.printf("\nTem certeza que deseja remover essa localização?(S/N)\n");
			String confirmacao = sc.next();
			
			if(confirmacao.equalsIgnoreCase("S")) {
				controllerLocalizacao.removerLocalizacao(controller.ControllerLocalizacao.obterIdLocalizacao(localizacao));
				UI_Menus.tela_remover_registros(sc);
			}else {
				UI_Menus.tela_remover_registros(sc);
			}
			
		} catch (WmsException e) {
			System.out.println(e.getMessage());
			
			System.out.printf("\nDeseja tentar remover outra localização?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Remocao.removerLocalizacao(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_remover_registros(sc);
			}

		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			removerLocalizacao(sc);;
		}
	}
}
