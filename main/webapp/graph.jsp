<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8" >
    <title>グラフ表示</title>
    <link href="<%=request.getContextPath()%>/css/graph.css" rel="stylesheet" >
    <script>
    function saveImage(){
        var image = document.querySelector("img");
        var link = document.createElement("a");
        link.download = "graph.png";
        link.href = image.src;
        link.click();
    }
    </script>
</head>
<body>
    <h1>グラフ表示</h1>
    <div>
        <figure>
            <img src="<%=request.getContextPath()%>/generateGraph?startDate=<%=request.getParameter("startDate")%>&endDate=<%=request.getParameter("endDate")%>" class="center-block">
        </figure>
    </div>
	<button class="gradient-button" onclick="saveImage()">グラフを保存</button>
	<button class="gradient-button" onclick="location.href='<%=request.getContextPath()%>/index.jsp'">トップページへ</button>
</body>
</html>