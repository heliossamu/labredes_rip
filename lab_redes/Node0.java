import java.util.Scanner;


public class Node0 {
	private int porta;
	private String grupoA;
	private String grupoB;
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2, 3};
	private static final int custo[] = {0, 1, 3, 7};
	
	public Node0(){
		porta = 2000;
		grupoA = "239.0.0.1"; //0, 1 e 2
		grupoB = "239.0.0.2"; //0, 2 e 3
		rt = new int[4][4];
		
		//grupo com os nos 0, 1 e 2
		Servidor servidor = new Servidor(porta, grupoA);
		Thread t_servidor = new Thread(servidor);
		t_servidor.start();
		
		//grupo com os nos 0, 2 e 3
		Servidor servidor2 = new Servidor(porta, grupoB);
		Thread t_servidor2 = new Thread(servidor2);
		t_servidor2.start();
		
		//thread que trata o lado cliente do no
		//vai ficar esperando por alguma entrada do usuario
		Cliente cliente = new Cliente();
		Thread t_cliente = new Thread(cliente);
		t_cliente.start();
		
		System.out.println("Nó 0 construido com sucesso!");
	}
	
	/* Este metodo irá inicializar a tabela de roteamente do nó 0*/
	public void rtinit0(){
		System.out.println("metodo 'rtinit0' chamado!");
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				rt[i][j] = 999;
			}
		}
		
		for(int i = 0; i < vizinho.length; i++){
			rt[0][ vizinho[i] ] = custo[i];
		}
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				System.out.print("" + rt[i][j] + " - ");
			}
			System.out.println();
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate0(){
		//TODO
	}
	
	public static void main(String[] args) {
		Node0 node = new Node0();
		node.rtinit0();
	}
	
}