package src;

import ilog.concert.*;
import ilog.cplex.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ResultArq {
	
	public ResultArq(Reader read, IloCplex cplex, IloNumVar[][] x) throws IloException {
		double[][] precoArray = read.convertToDoubleArray(read.getPreco());
		double[][] tempoArray = read.convertToDoubleArray(read.getTempo());
		double[][] restricaoArray = read.convertToDoubleArray(read.getRestricao());
		List<String> headers = read.getHeaders();
	    List<String> firstItems = read.getFirstItems();
		
		int numPecas = precoArray.length;
		int numFornecedores = precoArray[0].length;
		
		String texto = read.getFilePath();
        String substringRemover = "data/";
        String resultado = texto.replace(substringRemover, "");
        String nomeArquivo = "arquivo_" + resultado + ".txt";
        String path = "F:/Documents/GitHub/PO-this/Pesquisa_Operacional_2023.1/Eclipse-workspace/NewTest/result/";
        
        
        String conteudo = "Objective value: " + Double.toString(cplex.getObjValue()) + "\n";
        for (int i = 0; i < numPecas; i++) {
            for (int j = 0; j < numFornecedores; j++) {
                conteudo +="x_" + i + "_" + j + " = " + cplex.getValue(x[i][j])+ "\n";
            }
        }
        
        conteudo += "\n";
        conteudo += "------------------------------------------------------------------------------------------";
        conteudo += "\n";
        
        for (int i = 0; i < numPecas; i++) {
            for (int j = 0; j < numFornecedores; j++) {
            	if(cplex.getValue(x[i][j]) > 0) {
            		conteudo += firstItems.get(i) + " -> Fornecedor: ";
            		String aux = headers.get(2*j + 1);
            		substringRemover = "Preco";
            		aux = aux.replace(substringRemover, "");
            		conteudo += aux + "; Preco: " + precoArray[i][j] + "; Tempo de Entrega: " + tempoArray[i][j] + " dias \n";	
            	}
            }
        }
        
        

        try {
            FileWriter escritor = new FileWriter(path + nomeArquivo);
            escritor.write(conteudo);
            escritor.close();
            System.out.println("Arquivo criado e escrito com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
        }
	}
	
}
