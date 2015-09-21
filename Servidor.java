

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Servidor implements Runnable{
	private int[][] table_copy;
	private int port;
	private String address;
	private int count;
	private int noDeQuem;
	private int custos[][];
	private int vizinhos[];
	private int noOrigem;

	//construtor base, recebe a porta e o endereco do servidor
	public Servidor(int port, String address, int[][] tableOrigem, int noOrigem, int[][] custos, int[] vizinhos){
		this.port = port;
		this.address = address;
		this.custos = custos;
		this.noOrigem = noOrigem;
		this.vizinhos = vizinhos;
		table_copy = tableOrigem;
		count = 0;
	}
	
	public int[][] updateTable(){
		return table_copy;
	}
	
	public void updateCopyTable(int[][] table){
		table_copy = table;
	}
	
	public int[][] getIntegerTable(String table){
		int mat[][] = new int[4][4];
		String[] resultado = table.split("\\s");
		
		int j = 0, k = 0;
		int buff;
		
		for(int i = 0; i < resultado.length-1; i++){
			buff = Integer.parseInt(resultado[i]);
			mat[j][k] = buff;
			k++;
			if(k > 3){
				j++;
				k = 0;
			}
		}
		return mat;
	}
	
	
	public int less(int[][] mata, int[][] matb, int i, int j){
		if(i == j){
			return 0;
		}else{
			if(mata[i][j] < matb[i][j]){
				return mata[i][j];
			}else{
				return matb[i][j];
			}
			
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				MulticastSocket mcs = new MulticastSocket(port);
				InetAddress grp = InetAddress.getByName(address);
				mcs.joinGroup(grp);
				byte rec[] = new byte[1000];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				mcs.receive(pkg);
				String data = new String(pkg.getData());
				//System.out.println("Dados recebidos:" + data);
				
				int d[][] = getIntegerTable(data);
				int min;
				int buff_min;
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 4; j++){
						if(i == j){
							table_copy[i][j] = 0;
						}else{
							min = 999;
							for(int k = 0; k < vizinhos.length; k++){
								if(vizinhos[k] != i){
									//buff_min = custos[i][k] + d[k][j];
									buff_min = custos[i][k] + less(table_copy, d, k, j);
									
									if(buff_min < min){
										min = buff_min;
									}
								}
							}
							table_copy[i][j] = min;
						}
					}
				}
				
				
				
				System.out.println(count++);
			} catch (Exception e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}
}
