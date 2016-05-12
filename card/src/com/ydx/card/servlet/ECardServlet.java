package com.ydx.card.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swetake.util.Qrcode;

public class ECardServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ECardServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		// 获取前端内容
		String content = request.getParameter("content");
		// 创建qrcode对象
		Qrcode qrcode = new Qrcode();
		// 设置纠错级别
		qrcode.setQrcodeErrorCorrect('M');
		// 设置编码级别 模式二级制
		qrcode.setQrcodeEncodeMode('B');
		// 设置版本 1-40 版本越大，容纳的字符数越多
		qrcode.setQrcodeVersion(15);

		byte[] contentBytes = content.getBytes("UTF-8");
		int width = 255;
		int height = 255;

		// 获取一个画板
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取一根画笔
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.WHITE);

		// 清空内容
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);

		// 字节数组转换
		boolean[][] codeOut = qrcode.calQrcode(contentBytes);
		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; j++) {
				if (codeOut[j][i]) {
					g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
				}
			}
		}
		g.dispose();
		image.flush();
		String imageName = UUID.randomUUID() + ".png";
		File file = new File(request.getRealPath("/img") + "/" + imageName);
		if (!file.exists()) {
			file.mkdirs();
		}
		ImageIO.write(image, "png", file);
		PrintWriter out = response.getWriter();
		out.print("img/" + imageName);
//		System.out.print(file);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
