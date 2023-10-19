package UI;

import java.time.LocalDate;
import java.util.Scanner;

import controller.ControllerEntradaEstoque;
import controller.ControllerFornecedor;
import controller.ControllerItem;
import controller.ControllerLocalizacao;
import controller.ControllerSaidaEstoque;
import exeption.WmsException;
import model.EntradaEstoque;
import model.Fornecedor;
import model.Item;
import model.Localizacao;
import model.SaidaEstoque;

public class UI_Insercao {

	public static void gerar_entrada(Scanner sc) {

		try {
			System.out.printf("\nENTRADA DE ITENS\n\n");
			System.out.printf("Digite o codigo do item: ");
			int codigo_item = sc.nextInt();
			sc.nextLine();
			System.out.printf("Digite o CNPJ do fornecedor: ");
			String cnpj = sc.nextLine();
			System.out.printf("Digite o lote: ");
			String lote = sc.nextLine();
			System.out.printf("Digite a quantidade: ");
			int quantidade = sc.nextInt();
			sc.nextLine();
			System.out.printf("Digite a data de recebimento(DD/MM/AAAA): ");
			LocalDate data_recebimento = LocalDate.parse(sc.nextLine(), UI_Menus.fmt1);
			System.out.printf("Digite a o endereço que o item ira ficar no estoque(S C P N): ");
			String endereco = sc.nextLine();

			String[] numerosString = endereco.split(" ");

			Localizacao localizacao = null;

			if (numerosString.length == 4) {
				int[] numeros = new int[4];
				for (int i = 0; i < 4; i++) {
					numeros[i] = Integer.parseInt(numerosString[i]);
				}
				localizacao = new Localizacao(numeros[0], numeros[1], numeros[2], numeros[3]);
			}
			
			EntradaEstoque entradaEstoque = new EntradaEstoque(codigo_item, lote, quantidade, data_recebimento, cnpj);
			ControllerEntradaEstoque controllerEntradaEstoque = new ControllerEntradaEstoque();
			controllerEntradaEstoque.inserirEntrada(entradaEstoque, localizacao);

		} catch (WmsException e) {
			System.out.println(e.getMessage());
			System.out.printf("\nDeseja tentar realizar a entrada novamente?(S/N)\n");
			String texto = sc.next();
			System.out.println("");
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Insercao.gerar_entrada(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_inserir_registros(sc);
			}

		} catch (Exception e) {
			 System.out.println();
			 System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			gerar_entrada(sc);
		}
		System.out.printf("\nEntrada realizada com sucesso!\n");
		
		System.out.printf("\nDeseja gerar mais uma entrada?(S/N)");
		String texto = sc.next();
		if (texto.equalsIgnoreCase("S")) {
			UI_Menus.clearScreen();
			UI_Insercao.gerar_entrada(sc);
		} else {
			UI_Menus.clearScreen();
			System.out.println();
			UI_Menus.tela_inserir_registros(sc);
		}

	}
	
	public static void gerar_saida(Scanner sc) {
	    try {
	        System.out.println("\nSAÍDA DE ITENS\n");
	        System.out.print("Digite o código do item: ");
	        int codigo_item = sc.nextInt();
	        sc.nextLine();
	        System.out.print("Digite a quantidade: ");
	        int quantidade = sc.nextInt();
	        sc.nextLine();
	        System.out.print("Digite a data de saída (DD/MM/AAAA): ");
	        LocalDate dataSaida = LocalDate.parse(sc.nextLine(), UI_Menus.fmt1);
	        
	        System.out.printf("Digite a o endereço de origem do item(S C P N): ");
			String endereco = sc.nextLine();

			String[] numerosString = endereco.split(" ");

			Localizacao localizacao = null;
			
			if (numerosString.length == 4) {
				int[] numeros = new int[4];
				for (int i = 0; i < 4; i++) {
					numeros[i] = Integer.parseInt(numerosString[i]);
				}
				localizacao = new Localizacao(numeros[0], numeros[1], numeros[2], numeros[3]);
			}
	        
	        System.out.print("Digite o destino: ");
	        String destino = sc.nextLine();
	        
	        if (!ControllerLocalizacao.verificarExistenciaLocalizacao(localizacao)) {
	            throw new WmsException("\nA localização inserida não está cadastrada.");
	        }
	        
	        SaidaEstoque saidaEstoque = new SaidaEstoque(codigo_item, quantidade, dataSaida, ControllerLocalizacao.obterIdLocalizacao(localizacao), destino);

	        ControllerSaidaEstoque controllerSaidaEstoque = new ControllerSaidaEstoque();
	        controllerSaidaEstoque.inserirSaidaEstoque(saidaEstoque);

	    } catch (WmsException e) {
	        System.out.println(e.getMessage());
	        
	        System.out.printf("\nDeseja tentar realizar a saida novamente?(S/N)\n");
			String texto = sc.next();
			 System.out.println();
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Insercao.gerar_saida(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_inserir_registros(sc);
			}
	
	    } catch (Exception e) {
	    	 System.out.println();
	    	 System.out.println();
	        System.out.println("ERRO - Por favor, tente novamente, certifique-se de inserir os dados corretamente.");
	        sc.nextLine();
	        gerar_saida(sc);
	    }
	    System.out.println("\nSaída realizada com sucesso!");
	    
	    System.out.printf("\nDeseja gerar mais uma saida?(S/N)\n");
		String texto = sc.next();
		
		if(texto.equalsIgnoreCase("S")) {
			UI_Menus.clearScreen();
			UI_Insercao.gerar_saida(sc);
		}else {
			UI_Menus.clearScreen();
			System.out.println();
			UI_Menus.tela_inserir_registros(sc);
		}
	}
	
	public static void cadastrar_item(Scanner sc) {

		try {
			System.out.printf("\nCADASTRAR ITEM\n\n");
			System.out.printf("Digite o Codigo do item: ");
			int codigo = sc.nextInt();
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
			ControllerItem controllerItem = new ControllerItem();
			controllerItem.inserirItem(item);

		} catch (WmsException e) {

			System.out.println(e.getMessage());
			
			System.out.printf("\nDeseja tentar cadastrar o item novamente?(S/N)\n");
			String texto = sc.next();
			 System.out.println();
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Insercao.cadastrar_item(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_inserir_registros(sc);
			}

		} catch (Exception e) {
			 System.out.println();
			 System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			sc.nextLine();
			cadastrar_item(sc);
		}
		System.out.printf("\nItem cadastrado com sucesso!\n");
		sc.nextLine();
		System.out.printf("\nDeseja cadastrar mais um item?(S/N)\n");
		String texto = sc.next();
		if(texto.equalsIgnoreCase("S")) {
			UI_Menus.clearScreen();
			UI_Insercao.cadastrar_item(sc);
		}else {
			UI_Menus.clearScreen();
			System.out.println();
			UI_Menus.tela_inserir_registros(sc);
		}
	}

	public static void cadastrar_fornecedor(Scanner sc) {

		try {
			System.out.printf("\nCADASTRAR FORNECEDOR\n\n");
			System.out.printf("Digite o CNPJ: ");
			sc.nextLine();
			String cpnj = sc.nextLine();
			System.out.printf("Digite a razão social: ");
			String razao_social = sc.nextLine();
			System.out.printf("Digite o nome fantasia: ");
			String nome_fantasia = sc.nextLine();
			System.out.printf("Digite o endereço: ");
			String endereco = sc.nextLine();
			System.out.printf("Digite o ramo de atividade: ");
			String ramo = sc.nextLine();

			Fornecedor fornecedor = new Fornecedor(cpnj, nome_fantasia, razao_social, endereco, ramo);

			ControllerFornecedor controllerFornecedor = new ControllerFornecedor();
			controllerFornecedor.inserirFornecedor(fornecedor);

		} catch (WmsException e) {
			System.out.println(e.getMessage());
			System.out.printf("\nDeseja tentar cadastrar o fornecedor novamente?(S/N)\n");
			String texto = sc.next();
			
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Insercao.cadastrar_fornecedor(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_inserir_registros(sc);
			}

		} catch (Exception e) {
			 System.out.println();
			 System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			cadastrar_fornecedor(sc);
		}
		System.out.printf("\nFornecedor cadastrado com sucesso!\n");
		
		System.out.printf("\nDeseja cadastrar mais um fornecedor?(S/N)\n");
		String texto = sc.next();
		if(texto.equalsIgnoreCase("S")) {
			UI_Menus.clearScreen();
			UI_Insercao.cadastrar_fornecedor(sc);
		}else {
			UI_Menus.clearScreen();
			System.out.println();
			UI_Menus.tela_inserir_registros(sc);
		}
	
	}

	public static void cadastrar_localizacao(Scanner sc) {

		try {
			System.out.printf("\nCADASTRAR LOCALIZACAO DE ESTOQUE\n\n");
			System.out.printf("Digite o setor: ");
			int setor = sc.nextInt();
			System.out.printf("Digite o corredor: ");
			int corredor = sc.nextInt();
			System.out.printf("Digite a prateleira: ");
			int prateleira = sc.nextInt();
			System.out.printf("Digite o nivel: ");
			int nivel = sc.nextInt();

			Localizacao localizacao = new Localizacao(setor, corredor, prateleira, nivel);
			ControllerLocalizacao controllerLocalizacao = new ControllerLocalizacao();
			controllerLocalizacao.inserirLocalizacao(localizacao);

		} catch (WmsException e) {
			System.out.println(e.getMessage());
			System.out.printf("\nDeseja tentar cadastrar uma localização novamente?(S/N)\n");
			String texto = sc.next();
			 
			if(texto.equalsIgnoreCase("S")) {
				UI_Menus.clearScreen();
				UI_Insercao.cadastrar_localizacao(sc);
			}else {
				UI_Menus.clearScreen();
				System.out.println();
				UI_Menus.tela_inserir_registros(sc);
			}

		} catch (Exception e) {
			 System.out.println();
			 System.out.println();
			System.out.println("ERRO - Por favor tente novamente, certifique-se de inserir os dados corretamente.");
			sc.nextLine();
			sc.nextLine();
			cadastrar_localizacao(sc);
		}
		System.out.printf("\nLocalização cadastrada com sucesso!\n");
		
		System.out.printf("\nDeseja cadastrar mais uma localização?(S/N)\n");
		String texto = sc.next();
		
		if(texto.equalsIgnoreCase("S")) {
			UI_Menus.clearScreen();
			UI_Insercao.cadastrar_localizacao(sc);
		}else {
			UI_Menus.clearScreen();
			System.out.println();
			UI_Menus.tela_inserir_registros(sc);
		}
		
	}

}
