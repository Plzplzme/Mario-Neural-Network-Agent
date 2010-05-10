package ch.idsia.ai.agents.controllers;

import br.pucpr.neuralnetwork.Neuron;
import br.pucpr.neuralnetwork.Point;
import ch.idsia.ai.agents.Agent;
import ch.idsia.mario.engine.sprites.Mario;
import ch.idsia.mario.environments.Environment;

// Classe que representa um agente inteligente que utiliza rede neural
public class NeuralNetworkAgent extends BasicAIAgent implements Agent {
	
	// Neur�nio
	private Neuron neuron;
	// Pontos para treinamento
	private Point[] examplesTraining;
	
	public NeuralNetworkAgent() {
		super("NeuralNetworkAgent");
		
		// Inst�ncia um neur�nio
		// Com 3 dendritos, obst�culo 1, obst�culo 2 e 
		// bias para quando as primeiras entradas forem zero
		// neuron = new Neuron(3, null, "sign");
		
		// Com pesos padr�es
		float[] defaultWeights = new float[3];
		defaultWeights[0] = (float)0.12533271;
		defaultWeights[1] = (float)-0.15752053;
		defaultWeights[2] = (float)-0.22475332;
		neuron = new Neuron(3, defaultWeights, "sign");
		
		// Cria os pontos para o treinamento
		setExamplesTraining();
		
		// Realiza o treinamento
		neuron.train(300, 43, examplesTraining);
		
		reset();
	}
	
	public void reset() {
		action = new boolean[Environment.numberOfButtons];
		action[Mario.KEY_RIGHT] = true;
	}
	
	public boolean[] getAction() {
		// Atrav�s dos pesos calculados no treinamento
		// tenta jogar Mario sozinho
		// Define o valor para os dendritos baseado nos obst�culos do jogo
		int[] vals = new int[2];
		vals[0] = mergedObservation[11][13];
		vals[1] = mergedObservation[11][12];
		neuron.setDendrites(vals);
		
		// Realiza ativa��o do neur�nio, afim de obter o sinal de sa�da
		neuron.activation();
		
		// De acordo com o neur�nio faz o Mario pular ou n�o
		if (neuron.getAxon().signToBoolean()) {
			if (isMarioAbleToJump) {
				action[Mario.KEY_JUMP] = true;	
			}
		} else {
			action[Mario.KEY_JUMP] = false;
		}
		
		return action;
	}
	
	// Cria os pontos para o treinamento
	public void setExamplesTraining() {
		examplesTraining = new Point[6];
		examplesTraining[0] = new Point(0, 0, -1);
		examplesTraining[1] = new Point(-10, 0, 1);
		examplesTraining[2] = new Point(20, 0, 1);
		examplesTraining[3] = new Point(-10, -10, 1);
		examplesTraining[4] = new Point(2, 0, 1);
		examplesTraining[5] = new Point(-11, 0, 1);
	}
}