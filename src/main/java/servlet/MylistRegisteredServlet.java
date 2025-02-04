package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ListDao;


/**
 * Servlet implementation class MylistRegisteredServlet
 */
@WebServlet("/MylistRegisteredServlet")
public class MylistRegisteredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*HttpSession session = request.getSession();
		
        ArrayList<String> shoppingList = (ArrayList<String>) session.getAttribute("shoppingList");
        
        if (shoppingList == null) {
        	
            shoppingList = new ArrayList<>();
            
            session.setAttribute("shoppingList", shoppingList);
            
        }

        // パラメータから新しいアイテムを取得し、リストに追加
        String newItem = request.getParameter("item");
        
        if (newItem != null && !newItem.trim().isEmpty()) {
        	
            shoppingList.add(newItem);
            
        }*/
		request.setCharacterEncoding("UTF-8");
		
		ListDao listdao = new ListDao();
		
	
		String List_No = request.getReader().readLine();
		System.out.println("サーブレット２にきたよ");
		
		
		
		//本来はセッションから値を取得するだけ
		HttpSession session = request.getSession();
		String list_session = "0000000001";
		session.setAttribute("Sales_Namber", list_session);
		
		String Sales_Namber = (String)session.getAttribute("Sales_Namber");
		
		//String Sales_Namber = request.getParameter("Sales_Namber");
		
		System.out.println(List_No);
		
		/*String Sales_Namber = request.getParameter("Sales_No);*/
		
		System.out.println("受け取ったリスト番号: " + List_No);
	    System.out.println("受け取った商品番号: " + Sales_Namber);

	    if (List_No == null || List_No.isEmpty()) {
	        System.err.println("エラー: listNoがnullまたは空です。");
	        request.setAttribute("message", "リスト番号が指定されていません。");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
		
		boolean islist =listdao.ListRegistered(List_No ,Sales_Namber);
		
		
		if(islist) {
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/NewFile.jsp");
		        
		        dispatcher.forward(request, response);
			
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
	        
	        dispatcher.forward(request, response);
			
		}
		

        // JSPページにフォワード
    
	}

}
