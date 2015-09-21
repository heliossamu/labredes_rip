
public class Node1 {
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2, 3};
	private static final int custo[][] = {{0, 1, 3, 7},
										  {1, 0, 1, 999},
										  {3, 1, 0, 2},
										  {7, 999, 2, 0}};
	
	private Servidor servidor10;
	private Servidor servidor12;
	private Cliente cliente;
	
	public Node1(){
		int porta = 2001;
		String add10 = "239.0.0.1";
		String add12 = "239.0.0.4";
		
		rt = new int[4][4];
		
		rtinit1();
		
		//um servidor pra cada vizinho...
		servidor10 = new Servidor(porta, add10, rt, 1, custo, vizinho);
		Thread t_servidor = new Thread(servidor10);
		t_servidor.start();
		
		servidor12 = new Servidor(porta, add12, rt, 1, custo, vizinho);
		Thread t_servidor2 = new Thread(servidor12);
		t_servidor2.start();
		
		//thread que trata o lado cliente do no
		//vai ficar esperando por alguma entrada do usuario
		cliente = new Cliente(rt, 1, vizinho);
		Thread t_cliente = new Thread(cliente);
		t_cliente.start();
		
		System.out.println("Nó 1 construido com sucesso!");
		
	}
	
	/* Este metodo irá inicializar a tabela de roteamente do nó 0*/
	public void rtinit1(){
		System.out.println("metodo 'rtinit1' chamado!");
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				rt[i][j] = 999;
			}
		}
		for(int i = 0; i < vizinho.length; i++){
			rt[1][i] = custo[1][i];
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate1(){
		rt = servidor10.updateTable();
		updateServer();
		rt = servidor12.updateTable();
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
		servidor10.updateCopyTable(rt);
		servidor12.updateCopyTable(rt);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Node1 node = new Node1();
		
		while(true){
			node.updateServer();
			node.rtupdate1();
			//System.out.println("tabela do no 1:");
			node.printTable();
			System.out.println();
			Thread.sleep(1000);
		}
	}
	
}
