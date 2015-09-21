public class Node0 {	
	private Cliente cliente;
	private Servidor servidor01;
	private Servidor servidor02;
	private Servidor servidor03;
	
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2, 3};
	
	private static final int custo[][] = {{0, 1, 3, 7},
										  {1, 0, 1, 999},
										  {3, 1, 0, 2},
										  {7, 999, 2, 0}};
	
	public Node0(){
		String add01 = "239.0.0.1"; //0 para 1
		String add02 = "239.0.0.3"; //0 para 2
		String add03 = "239.0.0.2"; //0 para 3
		int porta = 2000;
		
		rt = new int[4][4];
		
		rtinit0();
		
		//um servidor pra cada vizinho...
		servidor01 = new Servidor(porta, add01, rt, 0, custo, vizinho);
		Thread t_servidor = new Thread(servidor01);
		t_servidor.start();
		
		servidor02 = new Servidor(porta, add02, rt, 0, custo, vizinho);
		Thread t_servidor2 = new Thread(servidor02);
		t_servidor2.start();
		
		servidor03 = new Servidor(porta, add03, rt, 0, custo, vizinho);
		Thread t_servidor3 = new Thread(servidor03);
		t_servidor3.start();
		
		//thread que trata o lado cliente do no
		//vai ficar esperando por alguma entrada do usuario
		cliente = new Cliente(rt, 0, vizinho);
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
			rt[0][i] = custo[0][i];
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate0(){
		rt = servidor01.updateTable();
		updateServer();
		rt = servidor02.updateTable();
		updateServer();
		rt = servidor03.updateTable();
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
			servidor01.updateCopyTable(rt);
			servidor02.updateCopyTable(rt);
			servidor03.updateCopyTable(rt);
		}
	
	public static void main(String[] args) throws InterruptedException {
		Node0 node = new Node0();
		
		//atualizando tabela
		while(true){
			node.updateServer();
			node.rtupdate0();
			//ystem.out.println("tabela do no 0:");
			node.printTable();
			System.out.println();
			Thread.sleep(1000);
		}
		
	}

	//agora vai!
	
}
