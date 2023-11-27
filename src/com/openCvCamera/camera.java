package com.openCvCamera;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class camera extends JFrame{
	
	private JLabel cameraScreen;
	
	private JButton btnCapture;
	
	private VideoCapture capture;			// it is used to start camera
	private Mat image;									// mat class use to store image as  a matrix.

	private boolean clicked = false;
	
	public camera() {
		
		setLayout(null);
		
		cameraScreen = new JLabel();
		cameraScreen.setBounds(0,0,640,480);
		add(cameraScreen);
		
		btnCapture = new JButton("capture");
		btnCapture.setBounds(300,480,80,40);
		add(btnCapture);
		
		btnCapture.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				clicked = true;
				
				
			}
		});
		
//		when program closes camera must close
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				
				super.windowClosing(e);
				capture.release();
				image.release();
				System.exit(0);
			}
			
		});
		
		
		setSize(new Dimension(640,560));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
//	create Camera
	
	public void startCamera() {
		
		capture = new VideoCapture(0);
		image = new Mat();
		
		byte[] imageData;
		
		ImageIcon icon;
		
		while(true) {
			
//			read image to matrix form.
			capture.read(image);
			
//			convert matrix to byte
			
			final MatOfByte buf = new MatOfByte();
			Imgcodecs.imencode(".jpg", image, buf);
			
			imageData = buf.toArray();
			
			// add to JLabel
			icon = new ImageIcon(imageData);
			cameraScreen.setIcon(icon);
			
//			capture and save to file
			if(clicked ) {
				
//				prompt for enter image name
				String name = JOptionPane.showInputDialog(this,"Enter image name");
				
				if(name==null) {
					name= new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
				}
				
//				write to file
				Imgcodecs.imwrite("images/" + name +".jpg",image );
				
				clicked= false;
			}
		}
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				camera camera = new camera();
				
//				start camera in thread
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						camera.startCamera();
					}
				}).start();
				
			}
	
			
		});
		

	}

}
