import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TesteFilipe2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== MENU ===");
            System.out.println("1. Ler arquivo XML");
            System.out.println("2. Ler arquivo JSON");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    lerArquivoXML("C:\\Users\\filipe\\Downloads\\dados (2).xml");
                    break;
                case 2:
                    lerArquivoJSON("C:\\Users\\filipe\\Downloads\\dados.json");
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void lerArquivoXML(String caminhoArquivo) {
        try {
            File arquivo = new File(caminhoArquivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(arquivo);
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("row");
            System.out.println("Leitura do arquivo XML:");
            System.out.printf("%-10s %-15s%n", "Dia", "Valor");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String dia = element.getElementsByTagName("dia").item(0).getTextContent();
                    String valor = element.getElementsByTagName("valor").item(0).getTextContent();

                    System.out.printf("%-10s %-15s%n", dia, valor);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo XML: " + e.getMessage());
        }
    }

    public static void lerArquivoJSON(String caminhoArquivo) {
        try (FileInputStream fis = new FileInputStream(caminhoArquivo)) {
            byte[] data = new byte[(int) new File(caminhoArquivo).length()];
            fis.read(data);
            String jsonString = new String(data, "UTF-8");

            // O arquivo JSON é um array
            JSONArray jsonArray = new JSONArray(jsonString);
            System.out.println("Leitura do arquivo JSON:");
            System.out.printf("%-10s %-15s%n", "Dia", "Valor");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int dia = jsonObject.getInt("dia");
                double valor = jsonObject.getDouble("valor");
                System.out.printf("%-10d %-15.4f%n", dia, valor);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo JSON: " + e.getMessage());
        }
    }
}
