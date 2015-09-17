
public class Node1 {
	private int porta;
	private String grupoA;
	private int rt[][]; //tabela de roteamento
	private static final int vizinho[] = {0, 1, 2};
	private static final int custo[] = {1, 0, 1};
	
	
	public Node1(){
		porta = 2001;
		grupoA = "239.0.0.1"; //0, 1 e 2
		rt = new int[4][4];
		
		
		//grupo com os nos 0, 1 e 2
		Servidor servidor = new Servidor(porta, grupoA);
		Thread t_servidor = new Thread(servidor);
		t_servidor.start();
		
		//thread que trata o lado cliente do no
		//vai ficar esperando por alguma entrada do usuario
		Cliente cliente = new Cliente();
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
			rt[1][ vizinho[i] ] = custo[i];
		}
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				System.out.print("" + rt[i][j] + " - ");
			}
			System.out.println();
		}
	}
	
	/*Atualiza a tabela de roteamento*/
	public void rtupdate1(){
		//TODO
	}
	
	public static void main(String[] args) {
		Node1 node = new Node1();
		node.rtinit1();
		
	}
	
}
