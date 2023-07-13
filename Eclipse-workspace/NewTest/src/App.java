package src;

import ilog.concert.*;
import ilog.cplex.*;

public class App {

	public static void main(String[] args) {
		Reader reader = new Reader("data/sampleErrada.csv");
        reader.read();
        reader.printData();
        CplexSolver m = new CplexSolver();
        IloCplex n = m.solver(reader);
	}
}
