package iot.servlet;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.category.DefaultCategoryDataset;

import iot.db.dto.TemperatureData;
import iot.db.repository.TemperatureRepository;

@WebServlet(urlPatterns = "/generateGraph")
public class CreateGraph extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 検索用データの設定 ----

		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime localDateTimeBegin;
		LocalDateTime localDateTimeEnd;
		
		try {
			
			localDateTimeBegin = LocalDateTime.parse(startDate + "T00:00:00", formatter);
			localDateTimeEnd = LocalDateTime.parse(endDate + "T00:00:00", formatter);	
			
		}catch(Exception e){
			localDateTimeEnd = LocalDateTime.now();
			localDateTimeBegin = localDateTimeEnd.plusDays(-1);
		}
		
		

		// DB処理 接続から検索まで TemperatureRepository クラスに任せている
		// ここでは TemperatureRepository の生成(new)と検索を行うメソッドの呼び出しを行い
		// 検索結果を受け取ることを行う
		TemperatureRepository tempertureRepository = new TemperatureRepository();
		List<TemperatureData> dataList = tempertureRepository.searchByLocaldate(localDateTimeBegin, localDateTimeEnd);

		// 検索結果からグラフデータを作成するメソッドを呼び出す
		BufferedImage bufImage = createGraph(dataList);

		// このURLにアクセスされたときには画像情報を返却するよう
		// レスポンス情報を設定
		resp.setContentType("image/png");

		// ブラウザに直接画像データを送る
		try {
			ServletOutputStream os = resp.getOutputStream();
			ImageIO.write(bufImage, "png", os);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static BufferedImage createGraph(List<TemperatureData> dataList) {

		// プロットするデータ格納先のクラスを作成する
		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		// プロットデータとしては 1 データ, 2 系列, 3 項目 の情報が必要
		// 今回は 1 に 温度、2に"温度", 3に時間 を指定する
		for (TemperatureData td : dataList) {
			dcd.addValue(td.getTemperature(), "温度", td.getRegistDatetime());
		}

		JFreeChart chart = ChartFactory.createLineChart("温度グラフ", "時間", "温度", dcd);

		// フォント設定
		applyChartTheme(chart);

		// メモリ上に画像情報を作成する
		BufferedImage bufImage = chart.createBufferedImage(640, 480);
		return bufImage;
	}

	/**
	 * グラフ生成のフォント情報を設定する
	 * @see <a href="https://jfree.org/forum/viewtopic.php?t=30181">Change the font family for entire JFreeChart</a>
	 * @param chart フォント情報設定対象
	 */
	public static void applyChartTheme(JFreeChart chart) {
		final StandardChartTheme chartTheme = (StandardChartTheme) StandardChartTheme.createJFreeTheme();

		if (Locale.getDefault().getLanguage().equals(Locale.JAPANESE.getLanguage())) {
			final Font oldExtraLargeFont = chartTheme.getExtraLargeFont();
			final Font oldLargeFont = chartTheme.getLargeFont();
			final Font oldRegulerFont = chartTheme.getRegularFont();
			final Font oldSmallFont = chartTheme.getSmallFont();

			Font extraLargeFont = new Font("メイリオ", oldExtraLargeFont.getStyle(), oldExtraLargeFont.getSize());
			Font largeFont = new Font("メイリオ", oldLargeFont.getStyle(), oldLargeFont.getSize());
			Font regulerFont = new Font("メイリオ", oldRegulerFont.getStyle(), oldRegulerFont.getSize());
			Font smallFont = new Font("メイリオ", oldSmallFont.getStyle(), oldSmallFont.getSize());

			// chart.getTitle().setFont(font);
			chartTheme.setExtraLargeFont(extraLargeFont);
			chartTheme.setLargeFont(largeFont);
			chartTheme.setRegularFont(regulerFont);
			chartTheme.setSmallFont(smallFont);
		}

		chartTheme.apply(chart);
	}

}
