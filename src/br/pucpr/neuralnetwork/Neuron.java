package br.pucpr.neuralnetwork;

// Classe que representa um neur�nio
public class Neuron {
	// Dendritos
	private Dendrite[] dendrites;
	// Ax�nio
	private Axon axon;
	// Pesos
	private float[] weights;
	// Pesos padr�es
	private float[] defaultWeights;
	// Tipo da fun��o de ativa��o
	private String activationType;
	// Constante de aprendizado
	private float constantLearning;

	// M�todo construtor
	// aceita como p�rametros:
	// * numberDendrites = N�mero de dendritos
	// * defaultWeights = Pesos padr�o
	// * activationType = tipo da fun��o de ativa��o
	public Neuron(int numberDendrites, float[] defaultWeights, String activationType) {
		// Define os pesos padr�es
		this.defaultWeights = defaultWeights;
		
		// Se n�o for definida o tipo da fun��o de ativa��o,
		// ent�o define como sign
		if (activationType == null) {
			this.activationType = "sign";
		}
		
		// Pesos
		weights = new float[numberDendrites];
		// Dendritos
		dendrites = new Dendrite[numberDendrites];
		
		// Cria valores iniciais rand�micos para os pesos
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Util.random(-1, 1);
		}
		
		// Constante de aprendizado
		constantLearning = (float)0.0001;
		// Ax�nio
		axon = new Axon();
	}
	
	// Soma os dendritos com os seus pesos
	// m�dia ponderada
	public float sumDendrites() {
		float sum = 0;
		
		if (defaultWeights != null && defaultWeights.length > 0) {
			for (int j = 0; j < defaultWeights.length; j++) {
				weights[j] = defaultWeights[j];
			}
		}
		
		for (int i = 0; i < weights.length; i++) {
			sum += dendrites[i].getValue() * weights[i];
		}
		return sum;
	}
	
	// M�todo de ativa��o
	// dependendo da soma e do tipo de ativa��o
	// envia o sinal -1 ou 1 para o ax�nio
	public void activation() {
		float sum = sumDendrites();

		axon.setSign(1);
		if (sum < 0) {
			axon.setSign(-1);
		}
	}
	
	// Treina o neur�nio, afim de obter os melhores pesos para os dendritos
	// aceita como par�metros:
	// * numberTimes = n�mero de �pocas
	// * percentageCorrect = percentual correto para o treinamento ser considerado bem sucedido
	// * examplesTraining = exemplos para o treinamento
	public void train(int numberTimes, int percentageCorrect, Point[] examplesTraining) {
		System.out.println("Treinamento iniciado...");
		
		// Quantidade de treinamento verificado
		int totalVerified = 0;
		// Quantidade de treinamento verificado errado
		int totalVerifiedWrong = 0;
		// Quantidade de treinamento verificado correto
		int totalVerifiedCorrect = 0;
		
		// Repete at� o n�mero de �pocas ser satisfeito
		for (int i = 0; i < numberTimes; i++) {
			// Percorre todos os exemplos de treinamento
			for (int j = 0; j < examplesTraining.length; j++) {
				// Obt�m a sa�da adivinhada pelo neur�nio
				int result = guess(examplesTraining[j]);
				// Calcula o fator de mudan�a do peso baseado no erro
				// erro = sa�da desejada - sa�da adivinhada
				// multiplica pela constante de aprendizado
				float weightChange = constantLearning * (examplesTraining[j].getOutput() - result);
				// Ajusta os pesos baseado no fator de mudan�a * a entrada
				for (int k = 0; k < weights.length; k++) {
					weights[k] += weightChange * examplesTraining[j].getVals()[k];
				}
				
				// Contabiliza se o valor adivinhado foi correto ou n�o
				if (result == examplesTraining[j].getOutput()) {
					totalVerifiedCorrect++;
				} else {
					totalVerifiedWrong++;
				}
			}
		}
		
		// Quantidade de treinamentos verificados
		totalVerified = totalVerifiedWrong + totalVerifiedCorrect;
		float totalPercentageCorrect = ((100 * totalVerifiedCorrect ) / totalVerified);
		
		System.out.println("Treinamento terminado...");
		System.out.println("Resultados obtidos:");
		System.out.println(" - N�mero de �pocas executadas: " + numberTimes);
		
		String indicativeSuccess = "bem sucedido";
		
		if (totalPercentageCorrect < percentageCorrect) {
			indicativeSuccess = "mal sucedido";
		}
		
		System.out.println(" - Indicativo de sucesso: Treinamento " + indicativeSuccess);
		System.out.println(" - Porcentagem correto: " + totalPercentageCorrect);
		System.out.println(" - Pesos:");
		
		if (defaultWeights != null && defaultWeights.length > 0) {
			for (int j = 0; j < defaultWeights.length; j++) {
				weights[j] = defaultWeights[j];
			}
		}
		
		for (int i = 0; i < weights.length; i++) {
			System.out.println("   - Peso " + i + ": " + weights[i]);	
		}
	}
	
	// M�todo que adivinha a sa�da esperada
	// baseada nas entradas, de acordo com os pesos
	// calculados
	public int guess(Point point) {
		// Define os valores para os dendritos
		setDendrites(point.getVals());
		// Faz a ativa��o
		activation();
		// Sinal de ativa��o
		return axon.getSign();
	}

	// Define os valores para os dendritos
	// de acordo com um array de valores
	public void setDendrites(int[] vals) {
		for (int i = 0; i < vals.length; i++) {
			dendrites[i] = new Dendrite(vals[i]);
		}
	}

	// Obt�m a constante de aprendizado
	public float getConstantLearning() {
		return constantLearning;
	}

	// Define uma nova constante de aprendizado
	public void setConstantLearning(float constantLearning) {
		this.constantLearning = constantLearning;
	}

	// Obt�m o ax�nio
	public Axon getAxon() {
		return axon;
	}

	// Obt�m o tipo da fun��o de ativa��o usada
	public String getActivationType() {
		return activationType;
	}
}
