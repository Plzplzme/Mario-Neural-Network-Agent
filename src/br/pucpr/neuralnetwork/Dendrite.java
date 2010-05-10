package br.pucpr.neuralnetwork;

// Classe que representa um dendrito
public class Dendrite {
	// Valor para o dendrito
	private int value;

	// M�todo construtor
	// aceita como par�metro:
	// * value = valor para o dendrito
	public Dendrite(int value) {
		this.value = value;
	}

	// Obt�m o valor atual do dendrito
	public int getValue() {
		return value;
	}
}
