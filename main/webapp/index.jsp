<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>統計情報表示</title>
    <link href="<%=request.getContextPath()%>/css/index.css" rel="stylesheet">
    <script>
    function saveDates() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        localStorage.setItem("startDate", startDate);
        localStorage.setItem("endDate", endDate);
    }
    function sendDates() {
        var startDate = localStorage.getItem("startDate");
        var endDate = localStorage.getItem("endDate");
        location.href = "<%=request.getContextPath()%>/graph.jsp?startDate=" + startDate + "&endDate=" + endDate;
    }
    </script>
</head>
<body>
    <h1>記録情報表示</h1>
    <p>Raspberry Piに記録された情報を表示するサイトです。</p>
		<form>
			開始日 : <input type="date" id="startDate" name="startDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)) %>">
			<br>
			<br>
			終了日 : <input type="date" id="endDate" name="endDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
		    <br>
		    <br>
		    <button type="button" class="gradient-button" onclick="saveDates(); alert('範囲を確定しました');">範囲確定</button>
		    <br>
		    <br>
		</form>
    <button class="gradient-button" onclick="sendDates()">グラフ表示用ページへ</button>
    
</body>
</html>
    