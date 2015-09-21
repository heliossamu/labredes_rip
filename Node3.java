
public class Node3 {
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2, 3};
	private static final int custo[][] = {{0, 1, 3, 7},
			  {1, 0, 1, 999},
			  {3, 1, 0, 2},
			  {7, 999, 2, 0}};
	
	private Servidor servidor30;
	private Servidor servidor32;
	private Cliente cliente;
	
	public Node3(){
		int porta = 2003;
		String add30 = "239.0.0.2";
		String add32 = "239.0.0.5";
		
		rt = new int[4][4];
		
		rtinit3();
		
		//um servidor pra cada vizinho...
		servidor30 = new Servidor(porta, add30, rt, 1, custo, vizinho);
		Thread t_servidor = new Thread(servidor30);
		t_servidor.start();
		
		servidor32 = new Servidor(porta, add32, rt, 1, custo, vizinho);
		Thread t_servidor2 = new Thread(servidor32);
		t_servidor2.start();
		
		//thread que trata o lado cliente do no
		//vai ficar enviando a tabela rt para seus vizinhos
		cliente = new Cliente(rt, 1, vizinho);
		Thread t_cliente = new Thread(cliente);
		t_cliente.start();
		
		System.out.println("Nó 3 construido com sucesso!");
		
	}
	
	/* Este metodo irá inicializar a tabela de roteamente do nó 0*/
	public void rtinit3(){
		System.out.println("metodo 'rtinit3' chamado!");
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				rt[i][j] = 999;
			}
		}
		for(int i = 0; i < vizinho.length; i++){
			rt[3][i] = custo[3][i];
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate3(){
		rt = servidor30.updateTable();
		updateServer();
		rt = servidor32.updateTable();
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
		servidor30.updateCopyTable(rt);
		servidor32.updateCopyTable(rt);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Node3 node = new Node3();
		
		while(true){
			node.updateServer();
			node.rtupdate3();
			//System.out.println("tabela do no 1:");
			node.printTable();
			System.out.println();
			Thread.sleep(1000);
		}
		
	}
	
}
