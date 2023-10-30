package com.bookstore.controller.admin.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.service.UserServices;

@WebServlet("/admin/update_user")
public class UpdateUserSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UpdateUserSevlet() {
        super();
    }

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserServices userServices = new UserServices(request, response);
		userServices.updateUser();
		
	}

}
