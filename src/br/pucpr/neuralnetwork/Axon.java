package br.pucpr.neuralnetwork;

// Classe que representa um ax�nio
public class Axon {
	// Sinal de sa�da
	private int sign;
	
	// Obt�m o sinal do ax�nio
	public int getSign() {
		return sign;
	}

	// Define o sinal para o ax�nio
	public void setSign(int sign) {
		this.sign = sign;
	}
	
	// Transforma o sinal em um valor booleano
	public boolean signToBoolean() {
		if (sign == -1) {
			return false;
		}
		return true;
	}
}
