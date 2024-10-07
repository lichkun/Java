<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Файл конфигураций <code>web.xml</code></h1>
<p>
    Файл <code>web.xml</code> позволяет настроить веб-сервер (Tomcat или другие)
    под данный проект.
    <%=request.getAttribute("hash")%>
</p>
<h2>Сервлеты и маршрутизация</h2>
<p>
    До появления аннотаций и кшталт <code>@WebServlet</code> сервлеты регистрировались
    в файле <code>web.xml</code> с обозначением их маршрутов (роутинг).
</p>
<pre>
&lt;!--Регистрация сервлетов--&gt;
  &lt;servlet&gt;
    &lt;servlet-name&gt;webXmlServlet&lt;/servlet-name&gt;
    &lt;servlet-class&gt;itstep.learning.servlets.WebXmlServlet&lt;/servlet-class&gt;
      &lt;!--webXmlServlet = new itstep.learning.servlets.WebXmlServlet()--&gt;
  &lt;/servlet&gt;
    &lt;!--И Их маршрутизация(mapping)--&gt;
  &lt;servlet-mapping&gt;
    &lt;servlet-name&gt;webXmlServlet&lt;/servlet-name&gt;
    &lt;url-pattern&gt;/web-xml&lt;/url-pattern&gt;
  &lt;/servlet-mapping&gt;
</pre>
<h2>Страница ошибок</h2>
<p>
    в <code>web.xml</code> можно проложить адреса для всех типов ошибок
    также как с кодом ошибок, так и по исключению, которое случается при обработке
</p>
<pre>
  &lt;error-page&gt;
    &lt;error-code&gt;404&lt;/error-code&gt;
    &lt;location&gt;/WEB-INF/views/_layout.jsp&lt;/location&gt;
  &lt;/error-page&gt;
</pre>
<h2>Фильтры и область их действия</h2>
<p>
    Для фильтров <code>web.xml</code> особенно важный, поскольку гарантирует порядок
    выполнения фильтров(если их несколько). В области действия фильтров распространёной практирой являтся 
    шаблонных адресов на кшталт <code>/*</code> или <code>/api/*</code>
</p>
<pre>
    &lt;!--Регистрация фильтров--&gt;
  &lt;filter&gt;
    &lt;filter-name&gt;charsetFilter&lt;/filter-name&gt;
    &lt;filter-class&gt;itstep.learning.filters.CharsetFilter&lt;/filter-class&gt;
  &lt;/filter&gt;
  &lt;filter-mapping&gt;
    &lt;filter-name&gt;charsetFilter&lt;/filter-name&gt;
    &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
      &lt;!--/* для всех запросов--&gt;
  &lt;/filter-mapping&gt;
</pre>