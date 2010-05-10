package br.pucpr.neuralnetwork;

// Classe que representa um ponto de treinamento
public class Point {
	// Valores de entrada
	private int[] vals;
	// Sa�da esperada
	private int output;
	
	// M�todo construtor
	// aceita os par�metros:
	// * obs1 = tipo de obst�culo na posi��o 1
	// * obs2 = tipo de obst�culo na posi��o 2
	// * output = sa�da esperada
	public Point(int obs1, int obs2, int output) {
		vals = new int[3];
		vals[0] = obs1;
		vals[1] = obs2;
		// Bias para quando as entradas forem zero
		vals[2] = 1;
		this.output = output;
	}

	// Valores de entrada
	public int[] getVals() {
		return vals;
	}

	// Sa�da esperada
	public int getOutput() {
		return output;
	}	
}