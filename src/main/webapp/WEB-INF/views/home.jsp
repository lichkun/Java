<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="itstep.learning.services.file.RandomFileNameService" %>
<%
    RandomFileNameService randomFileNameService = new RandomFileNameService();
%>
<i>Home JSP</i>
<h1>Java wep. JSP</h1>
<img src="img/Java_Logo.png" alt="logo" width="100"/>
<i>Контроль инжекции хешу: <%=request.getAttribute("hash") %></i>
<p>
    JSP - Java Server Page - технология веб разработки с динамическим
    формированием HTML страниц. Аналогично к PHP, ранее ASP то
    надстройка над HTML, которое разширяет его добавляя
</p>
<ul>
    <li>Выражения</li>
    <li>Переменные</li>
    <li>Алгоритмические конструкции(условия, циклы)</li>
    <li>Взаимодействия с другими файлами-страницами</li>
</ul>
<p>
    Основу JSP составляет специализированные теги &lt;% %&gt; и &lt;% = %&gt;<br/>
    Тег &lt;% %&gt; включает в себя Java-код тег &lt;% = %&gt; выводит результат(короткая форма оператора print())
</p>
<h2>Выражения</h2>
<p>
    Выражение чаще всего задаются тегом, который выводит, в котором будет
    произвольное выражение (корректное для Java). Вывод результата происходит в месте,
    где находиться тег <br/>
    &lt;%=2+3%&gt; = <%=2 + 3%>
</p>
<h2>Переменные</h2>
<p>
    Переменные, их объявление и назначение (без вывода)
    оформляется в блоке &lt;% %&gt;
        <%
      String str= "Hello, World!";
      double[] prices = {10.0,20.0,30.0,40.0};
    %>
<pre>
      &lt;
        String str= "Hello, World!";
        double[] prices = {10.0,20.0,30.0,40.0};
      &gt;
    </pre>
</p>
<p>
    Вывод значения переменных - тег <br/>
    &lt;%= str % &gt; &rarr; <%= str %>
</p>
<h2>Алгоритмические конструкции</h2>
<pre>
    &lt; for (int i = 0; i < prices.length; i++) { %&gt;
      &lt;i&gt;&lt;%= prices[i] %&gt;&lt;/i&gt;&amp;emsp;
  &lt;% } &gt;
  </pre>
&rarr;
<table border="1">
    <tr>
        <th>#</th>
        <th>Price</th>
    </tr>
    <% for (int i = 0; i < prices.length; i++) { %>
    <tr>
        <td><%= (i + 1) %></td>
        <td><%= prices[i] %></td>
    </tr>
    <% } %>
</table>

<h2>Случайные имена файлов</h2>
<p>Сгенерированные случайные имена файлов:</p>
<ul>
    <li>Длина 5: <%= randomFileNameService.generate(5) %></li>
    <li>Длина 10: <%= randomFileNameService.generate(10) %></li>
    <li>Длина 12: <%= randomFileNameService.generate(12) %></li>
    <li>Длина по умолчанию: <%= randomFileNameService.generateDefault() %></li>
</ul>

<h2>Взаимодействие с файлами</h2>
&lt;jsp:include page="WEB-INF/fregment.jsp"/&gt;
<br/>&rarr;<br/>
<jsp:include page="fregment.jsp"/>
