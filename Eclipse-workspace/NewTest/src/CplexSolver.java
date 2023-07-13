package src;

import ilog.concert.*;
import ilog.cplex.*;

public class CplexSolver {
	public CplexSolver() {}
	
	private static double convertToBoolean(IloNumVar variable, IloCplex cplex) throws IloException {
	    double value = cplex.getValue(variable);
	    return value;
	}
	
	private static double getMaxProduct(double[][] array, double[][] res, int numPecas) {
		double maxProduct = 0.0;

	    for (int i = 0; i < array.length; i++) {
	    	for(int j = 0; j <array[i].length; j++) {
	    		
	    		
	    		double product = array[i][j] * res[i][0];
	    		
	    		if (product > maxProduct) {
	    			maxProduct = product;
	    		}
	    	}
	    }
	    maxProduct -= 15;
	    if(maxProduct < 0) { maxProduct = 0;}
	    maxProduct = maxProduct * 53.5/numPecas;
	    return maxProduct;
	}
	
	private static int getOneValues(double[][] array) {
		int aux = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i][0] == 1)
				aux++;
		}
		return aux;
	}
	
	public IloCplex solver(Reader read) {
		try {
			//Variables and Data
			IloCplex cplex = new IloCplex();
			
			double[][] precoArray = read.convertToDoubleArray(read.getPreco());
			double[][] tempoArray = read.convertToDoubleArray(read.getTempo());
			double[][] restricaoArray = read.convertToDoubleArray(read.getRestricao());
			
			int numPecas = precoArray.length;
			int numFornecedores = precoArray[0].length;
			int required = getOneValues(restricaoArray);
			
			IloNumVar[][] x = new IloNumVar[numPecas][numFornecedores];
			
			for (int i = 0; i < numPecas; i++) {
                for (int j = 0; j < numFornecedores; j++) {
                    x[i][j] = cplex.boolVar("x_" + i + "_" + j);
                }
            }	
			
			// Objective function
			IloNumExpr objective = cplex.constant(0);
			
			
			for(int i = 0; i < numPecas; i++) {
				for(int j = 0; j < numFornecedores; j++) {
					IloNumExpr term1 = cplex.prod(x[i][j], restricaoArray[i][0] * precoArray[i][j]);
					IloNumExpr term2 = cplex.prod(x[i][j], getMaxProduct(tempoArray, restricaoArray, required));
					objective = cplex.sum(objective, cplex.sum(term1, term2));
				}
			}
			
			cplex.addMinimize(objective);
			
			//Restricoes
			
			for (int i = 0; i < numPecas; i++) {
                IloNumExpr sumX = cplex.constant(0);
                for (int j = 0; j < numFornecedores; j++) {
                    sumX = cplex.sum(sumX, x[i][j]);
                    System.out.println(sumX);
                }
                cplex.addEq(sumX, restricaoArray[i][0]);
            }			
			
			// Solve the model
            if (cplex.solve()) {
                System.out.println("Objective value: " + cplex.getObjValue());
                
                // Print the values of xij
                for (int i = 0; i < numPecas; i++) {
                    for (int j = 0; j < numFornecedores; j++) {
                        System.out.println("x_" + i + "_" + j + " = " + cplex.getValue(x[i][j]));
                    }
                }
            } else {
                System.out.println("No solution found");
            }
			
			
			
			return cplex;
		} catch (IloException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Reader reader = new Reader("data/sampleErrada.csv");
        reader.read();
        reader.printData();
        CplexSolver m = new CplexSolver();
        IloCplex n = m.solver(reader);
	}
	
    public static void function(String[] args) {
        try {
            IloCplex cplex = new IloCplex();
            
            // Variables
            int numPecas = 10;  // Number of pieces
            int numFornecedores = 5;  // Number of suppliers
            
            IloNumVar[][] x = new IloNumVar[numPecas][numFornecedores];
            
            for (int i = 0; i < numPecas; i++) {
                for (int j = 0; j < numFornecedores; j++) {
                    x[i][j] = cplex.boolVar("x_" + i + "_" + j);
                }
            }
            
            // Objective function
            IloNumExpr objective = cplex.constant(0);
            double[] Hi = {5, 3, 4, 2, 1};  // Array of Hi values
            double[] Pij = {10, 8, 6, 4, 2};  // Array of Pij values
            double[] Tij = {7, 6, 5, 4, 3};  // Array of Tij values
            double S = 0.5;  // S value
            
            for (int i = 0; i < numPecas; i++) {
                for (int j = 0; j < numFornecedores; j++) {
                    IloNumExpr term1 = cplex.prod(x[i][j], Hi[j] * Pij[j]);
                    IloNumExpr term2 = cplex.prod(x[i][j], Tij[j] * Hi[j]);
                    objective = cplex.sum(objective, cplex.sum(term1, term2));
                }
            }
            
            cplex.addMinimize(objective);
            
            // Constraints
            for (int i = 0; i < numPecas; i++) {
                IloNumExpr sumX = cplex.constant(0);
                for (int j = 0; j < numFornecedores; j++) {
                    sumX = cplex.sum(sumX, x[i][j]);
                }
                cplex.addEq(sumX, 1);
            }
            
            for (int i = 0; i < numPecas; i++) {
                for (int j = 0; j < numFornecedores; j++) {
                    cplex.addEq(x[i][j], Hi[j]);
                }
            }
            
            // Solve the model
            if (cplex.solve()) {
                System.out.println("Objective value: " + cplex.getObjValue());
                
                // Print the values of xij
                for (int i = 0; i < numPecas; i++) {
                    for (int j = 0; j < numFornecedores; j++) {
                        System.out.println("x_" + i + "_" + j + " = " + cplex.getValue(x[i][j]));
                    }
                }
            } else {
                System.out.println("No solution found");
            }
            
            cplex.end();
            
        } catch (IloException e) {
            e.printStackTrace();
        }
    }
}





