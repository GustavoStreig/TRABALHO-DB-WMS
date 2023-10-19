package UI;

import java.util.Scanner;

import controller.ControllerFornecedor;
import controller.ControllerItem;
import controller.ControllerLocalizacao;
import exeption.WmsException;
import model.Fornecedor;
import model.Item;
import model.Localizacao;

public class UI_Atualizacao {

	public static void atualizarFornecedor(Scanner sc) {

		try {

			ControllerFornecedor controllerFornecedor = new ControllerFornecedor();

			int contador = 1;
			System.out.println();
			for (Fornecedor fornecedor : controllerFornecedor.buscarFornecedores()) {
				System.out.println(contador + "- " + fornecedor.imprimirCnpjRazaoSocial());
				contador += 1;
			}

			System.out.printf("\n\n Digite o cnpj do fornecedor que deseja atualizar:");
			sc.nextLine();
			String cnpj = sc.nextLine();

			System.out.printf("Digite a razão social: ");
			String razao_social = sc.nextLine();
			System.out.printf("Digite o nome fantasia: ");
			String nome_fantasia = sc.nextLine();
			System.out.printf("Digite o endereço: ");
			String endereco = sc.nextLine();
			System.out.printf("Digite o ramo de atividade: ");
			String ramo = sc.nextLine();

			Fornecedor fornecedor = new Fornecedor(cnpj, nome_fantasia, razao_social, endereco, ramo);
			
			controllerFornecedor.atualizarFornecedor(fornecedor);
			UI_Menus.tela_atualizar_registros(sc);

		} catch (WmsException e) {
			System.out.println(e.getMessage());
			
			System.out.printf("\nDeseja tentar atualizar outro fornecedor?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Atualizacao.atualizarFornecedor(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_atualizar_registros(sc);
			}

		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			UI_Atualizacao.atualizarFornecedor(sc);
		}
	}
	
	public static void atualizarItem(Scanner sc) {

		try {
			
			ControllerItem controllerItem = new ControllerItem();
			
			int contador=1;
			System.out.println();
			for (Item item : controllerItem.buscarItens()) {
				System.out.println(contador + "- " + item.imprimirCodigoNome());
				contador += 1;
			}
			
			System.out.printf("\nDigite o codigo do item que deseja atualizar:");
			int codigo = sc.nextInt();	
			
			if(ControllerItem.verificarExistenciaCodigoItem(codigo)) {
					
			System.out.printf("Digite o nome do item: ");
			sc.nextLine();
			String nome = sc.nextLine();
			System.out.printf("Digite o tipo do item: ");
			String tipo = sc.nextLine();
			System.out.printf("Digite a descricao do item: ");
			String descricao = sc.nextLine();
			System.out.printf("Digite o volume do item(m2): ");
			Double volume = sc.nextDouble();
			
			Item item = new Item(codigo, nome, tipo, descricao, volume);
			
			controllerItem.atualizarItem(item);
			}else {
				throw new WmsException("\nO código inserido não está cadastrado.");
			}
			
			UI_Menus.tela_atualizar_registros(sc);
		} catch (WmsException e) {
			System.out.println(e.getMessage());
			
			System.out.printf("\nDeseja tentar atualizar outro item?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Atualizacao.atualizarItem(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_atualizar_registros(sc);
			}

		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			UI_Atualizacao.atualizarItem(sc);
		}
	}
	
	public static void atualizarLocalizacao(Scanner sc) {

		try {
		
			ControllerLocalizacao controllerLocalizacao = new ControllerLocalizacao();
			
			int contador=1;
			System.out.println();
			for (Localizacao localizacao : controllerLocalizacao.buscarLocalizacoes()) {
				System.out.println(contador + "- " + localizacao.toString());
				contador += 1;
			}
			
			Localizacao idLocalizacao = null;
			
			System.out.printf("\nDigite a localização que deseja atualizar(S C P N): ");
			sc.nextLine();
			String endereco = sc.nextLine();

			String[] numerosString = endereco.split(" ");
			
			if (numerosString.length == 4) {
				int[] numeros = new int[4];
				for (int i = 0; i < 4; i++) {
					numeros[i] = Integer.parseInt(numerosString[i]);
				}
				idLocalizacao = new Localizacao(numeros[0], numeros[1], numeros[2], numeros[3]);
			}
			
			System.out.printf("Digite o setor: ");
			int setor = sc.nextInt();
			System.out.printf("Digite o corredor: ");
			int corredor = sc.nextInt();
			System.out.printf("Digite a prateleira: ");
			int prateleira = sc.nextInt();
			System.out.printf("Digite o nivel: ");
			int nivel = sc.nextInt();
			
			Localizacao localizacao = null;
			localizacao = new Localizacao(setor, corredor, prateleira, nivel);
			
			controllerLocalizacao.atualizarLocalizacao(localizacao, controller.ControllerLocalizacao.obterIdLocalizacao(idLocalizacao));
			UI_Menus.tela_atualizar_registros(sc);
			
		} catch (WmsException e) {
			System.out.println(e.getMessage());
			
			System.out.printf("\nDeseja tentar atualizar outra localização?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Atualizacao.atualizarLocalizacao(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_atualizar_registros(sc);
			}

		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			UI_Atualizacao.atualizarLocalizacao(sc);
		}
	}

}
