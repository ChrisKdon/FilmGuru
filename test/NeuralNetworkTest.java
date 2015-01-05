import com.monikle.neuro.FeedForwardNetwork;
import com.monikle.neuro.NeuralNetwork;
import com.monikle.neuro.TrainerConfiguration;
import com.monikle.neuro.TrainingData;

public class NeuralNetworkTest {
	public static void main(String[] args) {
		NeuralNetwork network = new FeedForwardNetwork(2, 5, 1, 0.1, 0.9);

		TrainingData trainingData = new TrainingData();

		trainingData.add(new double[] {0, 0}, new double[] {0});
		trainingData.add(new double[] {0, 1}, new double[] {0});
		trainingData.add(new double[] {1, 0}, new double[] {0});
		trainingData.add(new double[] {1, 1}, new double[] {1});

		trainingData.add(new double[] {0, 0}, new double[] {0});
		trainingData.add(new double[] {0, 1}, new double[] {1});
		trainingData.add(new double[] {1, 0}, new double[] {1});
		trainingData.add(new double[] {1, 1}, new double[] {0});

		TrainerConfiguration config = TrainerConfiguration.create(trainingData)
				.setMaxEpochs(10000)
				.setAcceptableError(0.001)
				.setValidationAmount(0.5)
				.setShuffleTrainingData(false);

		network.train(config);

		System.out.println(network.run(1, 1));
		System.out.println(network.run(0, 1));
	}
}
