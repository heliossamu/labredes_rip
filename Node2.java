

public class Node2 {
	private Cliente cliente;
	private Servidor servidor20;
	private Servidor servidor21;
	private Servidor servidor23;
	
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2, 3};
	private static final int custo[][] = {{0, 1, 3, 7},
			  {1, 0, 1, 999},
			  {3, 1, 0, 2},
			  {7, 999, 2, 0}};
	
	public Node2(){
		String add20 = "239.0.0.3";
		String add21 = "239.0.0.4";
		String add23 = "239.0.0.5"; 
		int porta = 2002;

		rt = new int[4][4];
		
		rtinit2();
		
		//um servidor pra cada vizinho...
		servidor20 = new Servidor(porta, add20, rt, 2, custo, vizinho);
		Thread t_servidor = new Thread(servidor20);
		t_servidor.start();
		
		servidor21 = new Servidor(porta, add21, rt, 2, custo, vizinho);
		Thread t_servidor2 = new Thread(servidor21);
		t_servidor2.start();
		
		servidor23 = new Servidor(porta, add23, rt, 2, custo, vizinho);
		Thread t_servidor3 = new Thread(servidor23);
		t_servidor3.start();
		
		//thread que trata o lado cliente do no
		//vai ficar enviando a tabela para os vizinhos
		cliente = new Cliente(rt, 2, vizinho);
		Thread t_cliente = new Thread(cliente);
		t_cliente.start();
		
		System.out.println("Nó 2 construido com sucesso!");
	}
	
	/* Este metodo irá inicializar a tabela de roteamente do nó 0*/
	public void rtinit2(){
		System.out.println("metodo 'rtinit2' chamado!");
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				rt[i][j] = 999;
			}
		}
		for(int i = 0; i < vizinho.length; i++){
			rt[2][i] = custo[2][i];
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate2(){
		rt = servidor20.updateTable();
		updateServer();
		rt = servidor21.updateTable();
		updateServer();
		rt = servidor23.updateTable();
		updateServer();
	}
	
	public void printTable(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(rt[i][j] < 10){
					System.out.print("  " + rt[i][j] + " ");
				}else if(rt[i][j] < 100){
					System.out.print(" " + rt[i][j] + " ");
				}else
					System.out.print("" + rt[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//atualiza a tabela copia armazenada no servidor
		public void updateServer(){
			servidor20.updateCopyTable(rt);
			servidor21.updateCopyTable(rt);
			servidor23.updateCopyTable(rt);
		}
	
	public static void main(String[] args) throws InterruptedException {
		Node2 node = new Node2();
		
		while(true){
			node.updateServer();
			node.rtupdate2();
			//ystem.out.println("tabela do no 0:");
			node.printTable();
			System.out.println();
			Thread.sleep(1000);
		}
		
	}
	
}
