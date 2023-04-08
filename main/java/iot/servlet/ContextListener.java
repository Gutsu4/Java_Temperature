package iot.servlet;

import java.sql.DriverManager;
import java.util.Collections;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextListener.super.contextInitialized(sce);

		// WAR ファイルの lib に配置された DBライブラリは
		// ClassLoader の都合上 自動クラス読み込みの対象とならないため
		// Class.forName による 手動読み込みが必要となる
		// 手動読み込みのタイミングとして、最適なタイミングは
		// コンテキスト(Webアプリケーション)の生成時と考えるので
		// コンテキスト初期化直後に読み込まれるこのリスナーを使う

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 登録したDBドライバの手動解除
		Collections.list(DriverManager.getDrivers()).forEach(driver -> {
			try {
				DriverManager.deregisterDriver(driver);
			} catch (final Exception e) {
			}
		});
	}
}
