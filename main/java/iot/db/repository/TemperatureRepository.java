package iot.db.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import iot.db.dto.TemperatureData;

public class TemperatureRepository {

	//接続文字列
	private final String url = "jdbc:postgresql://localhost:5432/iot2022";
	private final String user = "iotuser2022";
	private final String password = "iotuser2022";

	public List<TemperatureData> searchByLocaldate(LocalDateTime begin, LocalDateTime end) {
		List<TemperatureData> dataList = new ArrayList<>();

		// 検索文字列の準備
		// 日付を使った BETWEEN 検索を行う
		// BETWEEN は A 以上 B以下 の検索をわかりやすく表現できるSQL句の一つ
		// 次の文は等価となる
		// WHERE A <= COL AND COL <= B
		// WHERE COL BETWEEN A AND B
		String qry = "SELECT * FROM temperature WHERE regist_datetime BETWEEN ? AND ? ";

		// DB処理 ----
		// try-with-resource を用いて自動クローズ(後始末)をする

		try (
				//PostgreSQLへ接続
				Connection conn = DriverManager.getConnection(url, user, password);
				// 置換文字列を伴った SQL である  PrepareStatement に パラメータ(引数) を渡して SQL を生成
				PreparedStatement ps = conn.prepareStatement(qry);
			) {

			//自動コミットOFF
			conn.setAutoCommit(false);

			ps.setTimestamp(1, Timestamp.valueOf(begin));
			ps.setTimestamp(2, Timestamp.valueOf(end));

			// デバッグ用出力
			System.out.println(ps.toString());

			// SQL文を実行して結果をResultSetで受け取る
			ResultSet rset = ps.executeQuery();

			//SELECT結果の受け取り
			while (rset.next()) {
				dataList.add(new TemperatureData(
						rset.getInt("id"),
						rset.getTimestamp("regist_datetime").toLocalDateTime(),
						rset.getDouble("temperature_c"),
						rset.getDouble("humidity")));

			}
		} catch (Exception e) {
			// 何かしらのエラーが発生したら エラーメッセージを出力
			e.printStackTrace();
		}
		
		return dataList;

	}
}
