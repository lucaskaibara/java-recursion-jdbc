package Principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;

public class App {

	public static List<Item> lstHierarquia;

	public static void main(String[] args) {

		try {

			//Inserir();
			Carregar();
			ImprimirRecursao(0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void Inserir() throws Exception {

		FileReader fr = new FileReader("src//Dados//teste 01.csv");
		BufferedReader br = new BufferedReader(fr);

		br.readLine();
		String linha = br.readLine();

		String[] dados;

		Connection conexao = Conexao.getConexao();

		while (linha != null) {
			dados = linha.split(";");

			String sql = "INSERT INTO itens (Id, Descricao, Pai) VALUES (?, ?, ?)";
			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, Integer.parseInt(dados[0]));
			stmt.setString(2, dados[1]);
			stmt.setInt(3, Integer.parseInt(dados[2]));

			stmt.execute();

			stmt.close();

			linha = br.readLine();
		}

		conexao.close();

		br.close();

	}

	public static void Carregar() throws Exception {

		lstHierarquia = new ArrayList<Item>();

		Connection conexao = Conexao.getConexao();

		String sql = "SELECT * FROM itens";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery(sql);

		while (resultado.next()) {
			lstHierarquia.add(new Item(resultado.getInt(1), resultado.getString(2), resultado.getInt(3)));
		}

		stmt.close();
		conexao.close();

	}

	public static void ImprimirRecursao(int pai, int nivel) {

		List<Item> lstFilhos = BuscarFilhos(pai);

		for (Item item : lstFilhos) {

			for (int i = 0; i < nivel; i++) {
				System.out.print(" ");
			}

			System.out.print(item.Descricao + "\n");

			ImprimirRecursao(item.Id, nivel + 3);

		}

	}

	public static List<Item> BuscarFilhos(int pai) {

		List<Item> lstRetorno = new ArrayList<Item>();

		for (Item item : lstHierarquia) {
			if (item.Pai == pai) {
				lstRetorno.add(item);
			}
		}

		return lstRetorno;

	}

}
