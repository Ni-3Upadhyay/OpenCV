package com.openCvCamera;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class OpenCVshapes extends JFrame{
	
	Mat image;
	Mat tempImage;
	JLabel imageView;
	
//	menu for save images
	
	private JMenuBar mb;
	private JMenu menu;
	
	private JMenuItem saveMenuItem;
	
	
	public org.opencv.core.Point originPoint;
	
	
	public OpenCVshapes() {
		
//		load Image
		
		image =Imgcodecs.imread("images/nitin.jpg");
		
//		view setup
		
		setUpView();
		
//		Load Image to JLabel
		
		loadImage(image);
		
//		set JFrame property
		
		setSize(image.width(), image.height());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void loadImage(Mat img) {
		
		final MatOfByte mof = new MatOfByte();
		
		Imgcodecs.imencode(".jpg", img, mof);
		
		final byte[] imageData = mof.toArray();
		
//		change image byte to image icon
		
		final ImageIcon  icon = new ImageIcon(imageData);
		
//		add icon to JLabel
		imageView.setIcon(icon);
		
		
	}
	
	private void setUpView() {
		
		setLayout(null);
		
		imageView = new JLabel();
		imageView.setBounds(0, 20,image.width(),image.height());
		
//		add mouse listener
		
		imageView.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
//				store location of mouse pressed
				
				originPoint = new org.opencv.core.Point(e.getX(), e.getY());
				
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseReleased(e);
				
				image = tempImage.clone();
				
			}
		});
		
//		add motion listener
		
		imageView.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
//				create temp image for drawing.
				
				tempImage = image.clone();
				
				final org.opencv.core.Point point = new org.opencv.core.Point(e.getX(), e.getY());
				
//				Here we will draw shapes
				
//				line draw
				
//				Imgproc.line(tempImage, originPoint, point, new Scalar(0,0,255), 5);
				
//				rectangle draw
				
//				Imgproc.rectangle(tempImage, originPoint, point, new Scalar(255,0,0),5);
				
				
//				circle draw
				
//				double ab2 = Math.pow(originPoint.x-point.x, 2) + Math.pow(originPoint.y-point.y,  2);
//				int distance = (int)Math.sqrt(ab2);	
//				Imgproc.circle(tempImage, originPoint, distance, new Scalar(0,255,0), 5);
				
//				draw ellipse
				
				double x = Math.abs(point.x - originPoint.x);
				double y = Math.abs(point.y - originPoint.y);
				
				Size size = new Size(x*2, y*2);
				
				Imgproc.ellipse(tempImage, new RotatedRect(originPoint, size, 5), new Scalar(255,255,0), 5);
				
				
				loadImage(tempImage);
			}
		});
		add(imageView);
		
//		add menu
		
		mb = new JMenuBar();
		menu = new JMenu("file");
		
		saveMenuItem = new JMenuItem("save");
		menu.add(saveMenuItem);
		mb.add(menu);
		
		mb.setBounds(0,0,image.width(), 20);
		add(mb);
		
		saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
//				save image to file
				Imgcodecs.imwrite("images/out.jpg",image);
				
			}
		});
		
	}

	
	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new OpenCVshapes();
			}
		});
		

	}
}
