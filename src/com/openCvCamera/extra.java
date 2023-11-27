package com.openCvCamera;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import static org.bytedeco.opencv.global.opencv_objdetect.CASCADE_SCALE_IMAGE;


public class extra {
	
	    public static void main(String[] args) throws FrameGrabber.Exception {
	        // Load the classifier file
	        CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_default.xml");

	        // Open the camera
	        FrameGrabber grabber = FrameGrabber.createDefault(0);
	        grabber.start();

	        // Create a window to display the camera feed
	        CanvasFrame canvas = new CanvasFrame("Camera Feed");
	        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

	        // Process each frame from the camera
	        while (true) {
	            // Read the frame from the camera
	            Mat frame = grabber.grab();

	            // Convert the frame to grayscale
	            Mat grayFrame = new Mat();
	            cvtColor(frame, grayFrame, CV_8UC(1));

	            // Detect faces in the frame
	            RectVector faceDetections = new RectVector();
	            faceDetector.detectMultiScale(grayFrame, faceDetections, 1.1, 3, CASCADE_SCALE_IMAGE, new org.bytedeco.opencv.opencv_core.Size(30, 30), new org.bytedeco.opencv.opencv_core.Size(0, 0));

	            // Draw a bounding box around each face
	            for (int i = 0; i < faceDetections.size(); i++) {
	                Rect rect = faceDetections.get(i);
	                rectangle(frame, rect, new org.bytedeco.opencv.opencv_core.Scalar(0, 255, 0, 0));
	            }

	            // Resize the frame to fit the window
	            resize(frame, frame, new org.bytedeco.opencv.opencv_core.Size(canvas.getWidth(), canvas.getHeight()));

	            // Display the frame in the window
	            canvas.showImage(frame);
	        }
	    }
	}


