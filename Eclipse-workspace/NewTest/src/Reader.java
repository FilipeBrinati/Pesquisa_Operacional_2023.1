package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
	private String filePath;
    private List<String> headers;
    private List<String> firstItems;
    private List<List<String>> matrix;
    private List<List<String>> preco;
    private List<List<String>> tempo;
	private List<List<String>> restricao;

    public String getFilePath() {return filePath;}
    public List<String> getHeaders() {return headers;}
    public List<String> getFirstItems() {return firstItems;}
    public List<List<String>> getMatrix() {return matrix;}
    public List<List<String>> getPreco() {return preco;}
    public List<List<String>> getTempo() {return tempo;}

    public void setFilePath(String filePath) {this.filePath = filePath;}
    public void setHeaders(List<String> headers) {this.headers = headers;}
    public void setFirstItems(List<String> firstItems) {this.firstItems = firstItems;}
    public void setMatrix(List<List<String>> matrix) {this.matrix = matrix;}
    public void setPreco(List<List<String>> preco) {this.preco = preco;}
    public void setTempo(List<List<String>> tempo) {this.tempo = tempo;}


    public Reader() {
        setFilePath("data/sample.csv");
        this.headers = new ArrayList<>();
        this.firstItems = new ArrayList<>();
        this.matrix = new ArrayList<>();
        this.preco = new ArrayList<>();
        this.tempo = new ArrayList<>();
        this.restricao = new ArrayList<>();
    }
    
    public Reader(String name) {
        setFilePath(name);
        this.headers = new ArrayList<>();
        this.firstItems = new ArrayList<>();
        this.matrix = new ArrayList<>();
        this.preco = new ArrayList<>();
        this.tempo = new ArrayList<>();
        this.restricao = new ArrayList<>();
    }


    public void read() {
    	try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");

                if (isFirstLine) {
                    headers.addAll(Arrays.asList(items));
                    isFirstLine = false;
                } else {
                    firstItems.add(items[0]);
                    matrix.add(Arrays.asList(items).subList(1, items.length));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not open the file");
            e.printStackTrace();
        }
    	for (int i = 0; i < matrix.size(); i++) {
            List<String> precoRow = new ArrayList<>();
            List<String> tempoRow = new ArrayList<>();
            List<String> restricaoRow = new ArrayList<>();

            for (int j = 0; j < matrix.get(i).size(); j++) {
                String item = matrix.get(i).get(j);

                if (j % 2 == 0 && j+1 < matrix.get(i).size()) {
                    precoRow.add(item);
                }else if(j % 2 != 0 && j+1 < matrix.get(i).size()) {
                	tempoRow.add(item);
                } else {
                	restricaoRow.add(item);
                }
                
            }

            tempo.add(tempoRow);
            preco.add(precoRow);
            restricao.add(restricaoRow);
        }
    }

    public void printData() {
        System.out.println("Headers: " + headers);
        System.out.println("First Items: " + firstItems);
        System.out.println("Matrix: ");
        for (List<String> row : matrix) {
            System.out.println(row);
        }
        System.out.println("Preco Matrix: ");
        for (List<String> row : preco) {
            System.out.println(row);
        }
        System.out.println("Tempo Matrix: ");
        for (List<String> row : tempo) {
            System.out.println(row);
        }
        System.out.println("Restricao: ");
        for (List<String> row : restricao) {
            System.out.println(row);
        }
    }

    public static void main(String[] args) {
        Reader reader = new Reader("data/sample1.csv");
        reader.read();
        reader.printData();
    }
}
