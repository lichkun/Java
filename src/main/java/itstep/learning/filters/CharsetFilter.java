package itstep.learning.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Регистрация фильтра происходит через web.xml или через аннотацию @WebFilter
Но в этом случае преимущество у web.xml, поскольку аннотации не гарантируют
порядок выполнения фильтров, а в web.xml порядок отвечает последовательности
декларации фильтров
 */
@Singleton
public class CharsetFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig= filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //прямой ход - от сервера к JSP(представления)
        //обращаем внимание, что request/response идут с базовыми типами (не-HTTP)
        //по надобности работы с HTTP функциями необходимо сделать преобразование
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //устанавливаем кодирование, которое будет работать при чтении/записи данных
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        System.out.println("CharsetFilter worked for: "+ request.getRequestURI());

        //если не указать вызов следующего фильтра, то обработка запроса приостанавливается
        //потребитель увидет пустую страницу браузера
        filterChain.doFilter(servletRequest, servletResponse); //await Next();
        // обратный ход - от представления к серверу
    }

    @Override
    public void destroy() {
       this.filterConfig = null;
    }
}
/*
    Фильтры(сервлетные фильтры) - концепция middleware - код, который
    - перед сервлетами (контролерами)
    - создает цепочку обработку запроса
    - проходится дважды: "прямо" при обработке запроса, и "обратно" - ответ(response)
    - позволяет добавлять другие фильтры в любую часть цепочки

Кодирование символов:
особенностью работы с запросов, в том что кодирование можно изменить только до,
того момента, когда начнетяс чтение еге. После считывания хотя б одного символа
изменение кодирования создает исключение.
Кодирование надо установить максимально рано - в самых первых
кодах работы с запросом. Это следствует на пользу использования фильтров.
 */