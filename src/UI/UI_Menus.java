package UI;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import controller.ControllerEntradaEstoque;
import controller.ControllerFornecedor;
import controller.ControllerItem;
import controller.ControllerItemEstoque;
import controller.ControllerLocalizacao;
import controller.ControllerSaidaEstoque;

public class UI_Menus {

	static DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static void tela_inicializacao(Scanner sc) {
		System.out.println("#########################################################");
		System.out.println("#  WMS SISTEMA DE GERENCIAMENTO DE ARMAZENS LOGISTICOS 	#");
		System.out.println("#							#");
		System.out.println("#		TOTAL DE REGISTROS EXISTENTES           #");
		System.out.println("#		1 - ITEM ESTOQUE:          " + ControllerItemEstoque.contarRegistrosEstoque()+ "  		#");
		System.out.println("#		2 - ENTRADA ESTOQUE:       " + ControllerEntradaEstoque.contarRegistrosEntradaEstoque()+ "  		#");
		System.out.println("#		3 - SAIDA ESTOQUE:         " + ControllerSaidaEstoque.contarRegistrosSaidasEstoque() + "  		#");
		System.out.println("#		4 - ITEM:                  " + ControllerItem.contarRegistroslItens() + "  		#");
		System.out.println("#		5 - FORNECEDOR:            " + ControllerFornecedor.contarRegistrosFornecedores() + "  		#");
		System.out.println("#		6 - LOCALIZAÇÕES ESTOQUE:  " + ControllerLocalizacao.contarRegistrosLocalizacoes() + "  		#");
		System.out.println("#							#");
		System.out.println("#	CRIADO POR: GUSTAVO STREIG			#");
		System.out.println("#							#");
		System.out.println("#	DISCIPLINA: BANCO DE DADOS 2023/2		#");
		System.out.println("#	PROFESSOR: HOWARD ROATTI			#");
		System.out.println("#							#");
		System.out.println("#########################################################");
		sc.nextLine();
		menu_principal(sc);
	}

	public static void menu_principal(Scanner sc) {
		System.out.println();
		System.out.println("###########################################");
		System.out.println("# 	      MENU PRINCIPAL    	  #");
		System.out.println("###########################################");
		System.out.println("#					  #");
		System.out.println("# 	  1 - RELATÓRIOS		  #");
		System.out.println("# 	  2 - INSERIR REGISTROS  	  #");
		System.out.println("# 	  3 - REMOVER REGISTROS  	  #");
		System.out.println("# 	  4 - ATUALIZAR REGISTROS 	  #");
		System.out.println("#					  #");
		System.out.println("# 	  5 - SAIR 			  #");
		System.out.println("#					  #");
		System.out.println("###########################################");
		
		try {
		System.out.print("\nDigite o opção que deseja: ");
		int opcao = sc.nextInt();
		System.out.print("\n");
		switch (opcao) {
		
		case 1:
			clearScreen();
			UI_Menus.tela_relatorios(sc);;
			break;
			
		case 2:
			clearScreen();
			UI_Menus.tela_inserir_registros(sc);
			break;
			
		case 3:
			clearScreen();
			UI_Menus.tela_remover_registros(sc);
			break;
		
		case 4:
			clearScreen();
			UI_Menus.tela_atualizar_registros(sc);
			break;

		default:
			System.out.printf("\nValor inválido, por favor digite novamente!\n");
			sc.nextLine();
			menu_principal(sc);
		}
		} catch (Exception e) {
			System.out.printf("\nERRO - Por favor tente novamente, certifique-se de inserir o número corretamente!\n\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}

	}
	
	public static void tela_relatorios(Scanner sc) {
		System.out.println("#########################################");
		System.out.println("#  		RELATÓRIOS    	        #");
		System.out.println("#########################################");
		System.out.println("#  1 - TOTAL DE ITENS POR FORNECEDORES	#");
		System.out.println("#  2 - ITENS NO ESTOQUE E ENDERECO	#");
		System.out.println("#				        #");
		System.out.println("#  3 - FORNECEDORES CADASTRADOS     	#");
		System.out.println("#  4 - ITENS CADASTRADOS 	   	#");
		System.out.println("#  5 - LOCALIDADES CADASTRADAS		#");
		System.out.println("#					#");
		System.out.println("#  6 - SAIR 			        #");
		System.out.println("#########################################");
		
		try {
		System.out.print("\nDigite o opção que deseja: ");
		int opcao = sc.nextInt();

		switch (opcao) {

		case 1:
			clearScreen();
			UI_Relatorio.buscar_itens_fornecedor(sc);
			break;

		case 2:
			clearScreen();
			UI_Relatorio.buscar_itens_endereco(sc);;
			break;


		case 3:
			clearScreen();
			UI_Relatorio.buscar_fornecedor(sc);
			break;

		case 4:
			clearScreen();
			UI_Relatorio.buscar_itens(sc);
			break;
		
		case 5:
			clearScreen();
			UI_Relatorio.buscar_localizacao(sc);;
			break;
			
		case 6:
			clearScreen();
			UI_Menus.menu_principal(sc);
			break;

		default:
			System.out.printf("\nValor inválido, por favor digite novamente!\n");
			sc.nextLine();
			tela_relatorios(sc);;
		}
		} catch (Exception e) {
			System.out.printf("\nERRO - Por favor tente novamente, certifique-se de inserir o número corretamente!\n\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}

	}
	
	public static void tela_inserir_registros(Scanner sc) {
		System.out.println("#########################################");
		System.out.println("# 	    INSERIR REGISTROS    	#");
		System.out.println("#########################################");
		System.out.println("#  1 - GERAR ENTRADA		        #");
		System.out.println("#  2 - GERAR SAIDA		        #");
		System.out.println("#					#");
		System.out.println("#  3 - CADASTRAR ITEM		        #");
		System.out.println("#  4 - CADASTRAR FORNECEDOR 	        #");
		System.out.println("#  5 - INSERIR LOCALIZACAO DE ESTOQUE   #");
		System.out.println("#				        #");
		System.out.println("#  6 - SAIR 			        #");
		System.out.println("#########################################");
		
		try{
		System.out.print("\nDigite o opção que deseja: ");
		int opcao = sc.nextInt();
		
		switch (opcao) {

		case 1:
			clearScreen();
			UI_Insercao.gerar_entrada(sc);
			break;
		
		case 2:
			clearScreen();
			UI_Insercao.gerar_saida(sc);;
			break;
			
		case 3:
			clearScreen();
			UI_Insercao.cadastrar_item(sc);
			break;

		case 4:
			clearScreen();
			UI_Insercao.cadastrar_fornecedor(sc);
			break;

		case 5:
			clearScreen();
			UI_Insercao.cadastrar_localizacao(sc);
			break;

		case 6:
			clearScreen();
			UI_Menus.menu_principal(sc);
			break;

		default:
			System.out.printf("\nValor inválido, por favor digite novamente!\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
		} catch (Exception e) {
			System.out.printf("\nERRO - Por favor tente novamente, certifique-se de inserir o número corretamente!\n\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
	}
	
	public static void tela_remover_registros(Scanner sc) {
		System.out.println("#########################################");
		System.out.println("#           REMOVER REGISTROS    	#");
		System.out.println("#########################################");
		System.out.println("#				        #");
		System.out.println("#  1 - EXCLUIR ITEM		        #");
		System.out.println("#  2 - EXCLUIR FORNECEDOR 	        #");
		System.out.println("#  3 - EXLCUIR LOCALIZACAO DE ESTOQUE 	#");
		System.out.println("#					#");
		System.out.println("#  4 - SAIR 			        #");
		System.out.println("#########################################");
		
		try {
		System.out.print("\nDigite o opção que deseja: ");
		int opcao = sc.nextInt();

		switch (opcao) {

		case 1:
			clearScreen();
			UI_Remocao.removerItem(sc);
			break;

		case 2:
			clearScreen();
			UI_Remocao.removerFornecedor(sc);
			break;

		case 3:
			clearScreen();
			UI_Remocao.removerLocalizacao(sc);
			break;

		case 4:
			clearScreen();
			UI_Menus.menu_principal(sc);
			break;

		default:
			System.out.printf("\nValor inválido, por favor digite novamente!\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
		} catch (Exception e) {
			System.out.printf("\nERRO - Por favor tente novamente, certifique-se de inserir o número corretamente!\n\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
	}
	
	public static void tela_atualizar_registros(Scanner sc) {
		System.out.println("#########################################");
		System.out.println("# 	   ATUALIZAR REGISTROS    	#");
		System.out.println("#########################################");
		System.out.println("#				        #");
		System.out.println("#  1 - ATUALIZAR ITEM		        #");
		System.out.println("#  2 - ATUALIZAR FORNECEDOR 	        #");
		System.out.println("#  3 - ATUALIZAR LOCALIZACAO DE ESTOQUE #");
		System.out.println("#				        #");
		System.out.println("#  4 - SAIR 			        #");
		System.out.println("#########################################");
		
		try {
		System.out.print("\nDigite o opção que deseja: ");
		int opcao = sc.nextInt();
		
		switch (opcao) {

		case 1:
			clearScreen();
			UI_Atualizacao.atualizarItem(sc);
			break;

		case 2:
			clearScreen();
			UI_Atualizacao.atualizarFornecedor(sc);
			break;

		case 3:
			clearScreen();
			UI_Atualizacao.atualizarLocalizacao(sc);
			break;

		case 4:
			clearScreen();
			UI_Menus.menu_principal(sc);
			break;

		default:
			System.out.printf("\nValor inválido, por favor digite novamente!\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
		} catch (Exception e) {
			System.out.printf("\nERRO - Por favor tente novamente, certifique-se de inserir o número corretamente!\n\n");
			sc.nextLine();
			tela_inserir_registros(sc);
		}
	}

}